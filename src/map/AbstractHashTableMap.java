package map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

abstract public class AbstractHashTableMap<K, V> implements Map<K, V> {
    // Tarjeta que contiene el resultado de la búsqueda
    protected static class HashEntryIndex {

        int index;
        boolean found;

        public HashEntryIndex(int index, boolean f) {
            this.index = index;
            this.found = f;
        }

        // Easy visualization
        @Override
        public String toString() {
            return "(" + this.index + ", " + this.found + ")";
        }
    }

    protected int size; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected HashEntry<K, V>[] bucket; // bucket array
    protected final Entry<K, V> AVAILABLE = new HashEntry<>(null, null);

    protected AbstractHashTableMap(int prime, int capacity) {
        this.prime = prime;
        this.capacity = capacity;
        this.size = 0;
        this.bucket = new HashEntry[capacity];
    }

    public AbstractHashTableMap(int size) {
    }

    public AbstractHashTableMap() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Este método abstracto es el que hace que la clase sea abstracta
    abstract protected int offset(K key, int i);

    @Override
    public V put(K key, V value) throws IllegalStateException {
        HashEntryIndex i = findEntry(key); // Aqui hace el "checkPosition()" (index, found)
        if(i.found){
            bucket[i.index].setValue(value);
            return bucket[i.index].getValue();
        }else if(size >= capacity / 2){ // Si la ocupacion es mayor o igual al 50%
            rehash();
            i = findEntry(key); // index y boolean
        }
        bucket[i.index] = new HashEntry<>(key, value);
        size ++;
        return null;
    }

    @Override
    public V get(K key) throws IllegalStateException {
        HashEntryIndex i = findEntry(key);
        if (i.found){
            return bucket[i.index].getValue();
        }
        return null;
    }

    @Override
    public V remove(K key) throws IllegalStateException {
        HashEntryIndex i = findEntry(key);
        if (i.found){
            V toReturn = bucket[i.index].getValue();
            bucket[i.index] = (HashEntry<K, V>) AVAILABLE;
            size --;
            return toReturn;
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        ArrayList<K> keys = new ArrayList<>();
        for(Entry<K,V> entry : bucket){
            keys.add(entry.getKey());
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for(Entry<K,V> value : bucket){
            values.add(value.getValue());
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for( Entry<K, V> entry : bucket){
            entries.add(entry);
        }
        return  entries;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<K, V>(size, bucket, AVAILABLE);
    }

    // Buscador de entries ( los elementos que conforman el bucket)
    // devuelve donde está la k que le pasan en nuestra tabla
    protected HashEntryIndex findEntry(K key) throws IllegalStateException{
        int avail = -1; // primer hueco disponible que vemos
        checkKey(key);
        int i = hashValue(key); // aplicamos la funcion hash = key mod size
        final int j = i;
        do{
            Entry<K, V> e = bucket[i];
            // si esa entry no dispone de nada vemos si es porque no se ha rellenado o porque se ha hecho un borrado
            if(e == null){
                if (avail < 0){
                    avail = i; // Si no se ha hecho borrado entonces paramos de buscar
                }
                break;
            }else if(key.equals(e.getKey())){ // si es el elemento que buscamos retornamos la solución
                return new HashEntryIndex(i, true); // i = posicion del bucket en el que está
            }else if (e == AVAILABLE){
                if (avail < 0){
                    avail = i;
                }
            }
            i = ( i + offset(key, i)) % capacity; // offset = desplazamiento (1 siempre porque no esta implementado)
        }while( i != j);
        return new HashEntryIndex(avail, false); // no se ha encontrado
    }

    protected void checkKey(K k) {

        // We cannot check the second test (i.e., k instanceof K) since we do not know the class K
        if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }

    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Doubles the size of the hash table and rehashes all the entries.
     */
    protected void rehash() {
        capacity = 2 * capacity;
        HashEntry<K, V>[] old = bucket;
        // new bucket is twice as big
        bucket = (HashEntry<K, V>[]) new HashEntry[capacity];
        Random rand = new Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (HashEntry<K, V> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = findEntry(e.getKey()).index;
                bucket[j] = e;
            }
        }
    }

    protected void rehash(int newcap) {
        capacity = newcap;
        HashEntry<K, V>[] old = bucket;
        // new bucket is twice as big
        bucket = (HashEntry<K, V>[]) new HashEntry[capacity];
        Random rand = new Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (HashEntry<K, V> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = findEntry(e.getKey()).index;
                bucket[j] = e;
            }
        }
    }
}
