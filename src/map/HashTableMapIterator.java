package map;

import java.util.Iterator;

public class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
    private int position;
    private HashEntry<T, U>[] bucket;
    private Entry<T, U> AVAILABLE;


    public HashTableMapIterator(int numberElements, HashEntry<T, U>[] bucket, Entry<T, U> AVAILABLE) {
        this.bucket = bucket;
        if (numberElements == 0) { // si no hay elementos en el mapa nos colocamos en la última posicion
            this.position = bucket.length;
        }else{ // Si hay elementos en el mapa vamos a recorrerlos desde el inicio.
            this.position = 0;
            goToNextElement(0);
            this.AVAILABLE = AVAILABLE;
        }
    }

    private void goToNextElement(int start){
        final int n = bucket.length;;
        this.position = start;
        while((this.position< n) && ((this.bucket[this.position] == null || (this.bucket[this.position] == AVAILABLE)))){
            this.position ++;
        }
    }

    @Override
    public boolean hasNext() {
        return this.position < this.bucket.length;
    }

    @Override
    public Entry<T, U> next() {
        if (hasNext()) {
            int currentPosition = this.position;
            goToNextElement(this.position + 1);
            return this.bucket[currentPosition];
        }else{
            throw new IllegalStateException("The map has not more elements.");
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
