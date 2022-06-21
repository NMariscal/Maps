package map;

// Se corresponde con un nodo
public class HashEntry<K, V> implements Entry<K, V>{
    protected K key;
    protected V value;

    public HashEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }
    @Override
    public V getValue() {
        return value;
    }


}