package map;

import java.util.Iterator;

public class HashTableMapValueIterator<T, U> implements Iterator<U> {
    HashTableMapIterator<T, U> it;

    public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public U next() {
        return it.next().getValue();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
