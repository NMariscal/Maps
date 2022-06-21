package material.iterators;

import material.BinaryTree.BinaryTree;
import material.Position;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class InorderBinaryTreeIterator<E> implements Iterator<Position<E>> {
    // profundidad = pila
    Deque<Position<E>> stack = new LinkedList<>();
    BinaryTree<E> binaryTree ;

    public InorderBinaryTreeIterator( BinaryTree<E> binaryTree) {
        this.binaryTree = binaryTree;
        if (!binaryTree.isEmpty()){
            goLastInLeft(binaryTree.root());
        }
    }

    public InorderBinaryTreeIterator(Position<E> position, BinaryTree<E> binaryTree) {
        this.binaryTree = binaryTree;
        goLastInLeft(position);
    }

    private void goLastInLeft(Position<E> position){
        this.stack.addFirst(position);
        while(binaryTree.hasLeft(position)){
            position = binaryTree.left(position);
            stack.addFirst(position);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Position<E> next() {
        Position<E> position = stack.removeFirst();
        while (binaryTree.hasRight(position)){
            goLastInLeft(binaryTree.right(position));
        }
        return position;
    }
}
