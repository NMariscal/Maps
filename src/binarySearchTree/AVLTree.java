package binarySearchTree;

import material.Position;

import java.util.Comparator;
import java.util.Iterator;

public class AVLTree<E> implements BinarySearchTree<E>{

    // We need this class to store the height of each BTNode
    private static class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T> {

        private int height;
        private T element;
        private Position<AVLInfo<T>> pos;

        AVLInfo(T element) {
            this.element = element;
            this.pos = null;
            this.height = 1;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<AVLInfo<T>> getTreePosition() {
            return this.pos;
        }


        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable<T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }

        @Override
        public String toString() {
            return this.getElement().toString();
        }
    }


    private static class AVLTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<AVLInfo<T>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<AVLInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<AVLInfo<E>> bst;
    private ReestructurableBinaryTree<AVLInfo<E>> resBT;

    public AVLTree() {
        this(new DefaultComparator<>());
    }

    /**
     * Creates a BinarySearchTree with the given comparator.
     *
     * @param c the comparator used to sort the nodes in the tree
     */
    public AVLTree(Comparator<E> c) {
        Comparator<AVLInfo<E>> avlComparator = (o1, o2) -> c.compare(o1.getElement(), o2.getElement());
        bst = new LinkedBinarySearchTree<>(avlComparator);
        resBT = new ReestructurableBinaryTree<>();
        bst.binTree = resBT;
    }


    @Override
    public Position<E> find(E value) {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) {
        return null;
    }

    @Override
    public Position<E> insert(E value) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public E remove(Position<E> pos) throws RuntimeException {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> first() throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> last() throws RuntimeException {
        return null;
    }

    @Override
    public Iterable<Position<E>> successors(Position<E> pos) {
        return null;
    }

    @Override
    public Iterable<Position<E>> predecessors(Position<E> pos) {
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }
}
