package material.iterators;

import material.BinaryTree.BinaryTree;
import material.Position;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class PreorderBinaryTreeIterator<E> implements Iterator<Position<E>> {
    Deque<Position<E>> stack = new LinkedList<>();
    BinaryTree<E> tree;

    public PreorderBinaryTreeIterator(BinaryTree<E> tree, Position<E> position) {
        this.tree = tree;
        stack.addFirst(position);
    }

    public PreorderBinaryTreeIterator(BinaryTree<E> tree) {
        this.tree = tree;
        if (!tree.isEmpty()){
            stack.addFirst(tree.root());
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Position<E> next() {
        Position<E> aux = stack.pollFirst();
        Deque<Position<E>> reverseList = new LinkedList<>();
        for (Position<E> node : tree.children(aux)) {
            reverseList.addFirst(node);
        }
        for (Position<E> node : reverseList) {
            stack.addFirst(node);
        }
        return aux;
    }
}
