package map;

import java.util.Iterator;

public class HashTableMapKeyIterator<T, U> implements Iterator<T> {
    public HashTableMapIterator<T, U> it;

    public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public T next() {
        return it.next().getKey();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
