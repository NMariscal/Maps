package material.BinaryTree;

import material.Position;
import material.iterators.BFSIterator;
import material.iterators.InorderBinaryTreeIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
    class BinaryTreeNode<E> implements Position<E> {
        BinaryTreeNode<E> parent;
        BinaryTreeNode<E> LeftChild;
        BinaryTreeNode<E> RightChild;
        BinaryTree<E> myTree;
        E element;

        public BinaryTreeNode(BinaryTreeNode<E> parent, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild, BinaryTree<E> myTree, E element) {
            this.parent = parent;
            LeftChild = leftChild;
            RightChild = rightChild;
            this.myTree = myTree;
            this.element = element;
        }

        public BinaryTreeNode<E> getParent() {
            return parent;
        }

        public void setParent(BinaryTreeNode<E> parent) {
            this.parent = parent;
        }

        public BinaryTreeNode<E> getLeftChild() {
            return LeftChild;
        }

        public void setLeftChild(BinaryTreeNode<E> leftChild) {
            LeftChild = leftChild;
        }

        public BinaryTreeNode<E> getRightChild() {
            return RightChild;
        }

        public void setRightChild(BinaryTreeNode<E> rightChild) {
            RightChild = rightChild;
        }

        public BinaryTree<E> getMyTree() {
            return myTree;
        }

        public void setMyTree(BinaryTree<E> myTree) {
            this.myTree = myTree;
        }

        public void setElement(E element) {
            this.element = element;
        }

        @Override
        public E getElement() {
            return element;
        }
    }
    int size;
    BinaryTreeNode<E> root;

    public LinkedBinaryTree() {
        this.size = 0;
        this.root = null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BinaryTreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode<E> root) {
        this.root = root;
    }

    @Override
    public Position<E> left(Position<E> p) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        BinaryTreeNode<E> left = node.getLeftChild();
        if (left == null){
            throw new RuntimeException("This node has no left child.");
        }
        return left;
    }

    @Override
    public Position<E> right(Position<E> p) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        BinaryTreeNode<E> right = node.getLeftChild();
        if (right == null){
            throw new RuntimeException("This node has no right child.");
        }
        return right;
    }

    @Override
    public boolean hasLeft(Position<E> p) {
        BinaryTreeNode<E> node = checkPosition(p);
        return (node.getLeftChild() != null);
    }

    @Override
    public boolean hasRight(Position<E> p) {
        BinaryTreeNode<E> node = checkPosition(p);
        return (node.getRightChild() != null);
    }

    @Override
    public E replace(Position<E> p, E e) {
        BinaryTreeNode<E> node = checkPosition(p);
        E aux = node.getElement();
        node.setElement(e);
        return aux;
    }

    // Returns the sibling of a node.
    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        BinaryTreeNode<E> parent = node.getParent();
        if(parent != null) {
            BinaryTreeNode<E> nodeAux;
            if (parent.getLeftChild() == node) {
                nodeAux = parent.getLeftChild();
            } else {
                nodeAux = parent.getRightChild();
            }
            if(nodeAux != null){
                return nodeAux;
            }
        }
        throw new RuntimeException("This node has no sibling.");
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(node,null,null,this,e);
        if(!hasLeft(node)){
            node.setLeftChild(newNode);
            size++;
        }else{
            throw new RuntimeException("This node already has a left child.");
        }
        return newNode;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        Position<E> rightPos = node.getRightChild();
        if (rightPos != null) {
            throw new RuntimeException("Node already has a right child");
        }
        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(node,null,null,this,e);
        node.setRightChild(newNode);
        size++;
        return newNode;
    }

    // No podemos eliminar nodos con dos hijos, solo se puede eliminar cuando tiene 1 o 0
    @Override
    public E remove(Position<E> p) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(p);
        BinaryTreeNode<E> left = node.getLeftChild();
        BinaryTreeNode<E>  right = node.getRightChild();
        if (hasLeft(node) && hasRight(node)){
            throw new RuntimeException("Cannot remove a node with two children.");
        }else{
            if (root == node){
                if (left != null) {
                    left.setParent(null);
                    root = left;
                }else if (right != null){
                    right.setParent(null);
                    root = right;
                }
            }else{
                BinaryTreeNode<E> parent = node.getParent();
                if (left != null) { // si tiene hijo izquierdo
                    if(node == parent.getLeftChild()){
                        left.setParent(parent);
                        parent.setLeftChild(left);
                        size --;
                    }else{ // si tiene hijo derecho
                        left.setParent(parent);
                        parent.setRightChild(left);
                        size --;
                    }
                }else if (right != null){
                    if(node == parent.getLeftChild()){
                        left.setParent(parent);
                        parent.setLeftChild(right);
                        size --;
                    }else{ // si tiene hijo derecho
                        left.setParent(parent);
                        parent.setRightChild(right);
                        size --;
                    }
                }
            }
        }
        return node.getElement();
    }

    //  Swap the locations of two positions in the tree.
    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BinaryTreeNode<E> node1 = checkPosition(p1);
        BinaryTreeNode<E> node2 = checkPosition(p2);
        BinaryTreeNode<E> aux = new BinaryTreeNode<>(node1.getParent(),node1.getLeftChild(), node1.getRightChild(), this, node1.getElement());
        node1.parent = node2.parent == node1 ? node2 : node2.parent;
        node1.LeftChild = node2.LeftChild == node1 ? node2 : node2.LeftChild;
        node1.RightChild = node2.RightChild == node1 ? node2 : node2.RightChild;

        node2.parent = aux.parent == node2 ? node1 : aux.parent;
        node2.LeftChild = aux.LeftChild == node2 ? node1 : aux.LeftChild;
        node2.RightChild = aux.RightChild == node2 ? node1 : aux.RightChild;

        if (node1.parent != null) {
            if (node1.parent.LeftChild == node2) {
                node1.parent.LeftChild = node1;
            } else {
                node1.parent.RightChild = node1;
            }
        } else {
            this.root = node1;
        }

        if (node2.parent != null) {
            if (node2.parent.LeftChild == node1) {
                node2.parent.LeftChild = node2;
            } else {
                node2.parent.RightChild = node2;
            }
        } else {
            root = node2;
        }

        if (this.hasLeft(node1)) {
            node1.LeftChild.parent = node1;
        }
        if (this.hasRight(node1)) {
            node1.RightChild.parent = node1;
        }
        if (this.hasLeft(node2)) {
            node2.LeftChild.parent = node2;
        }
        if (this.hasRight(node2)) {
            node2.RightChild.parent = node2;
        }
    }

    // Removes the subtree of v from this tree a creates a new tree with it.
    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        BinaryTreeNode<E> node  = checkPosition(v);
        if (node == root) {
            root = null; // eliminamos el árbol entero
        } else { // vemos si es el hijo derecho o izquierdo
            if (node.parent.LeftChild == node)
                node.parent.LeftChild = null; // Lo eliminamos
            else
                node.parent.RightChild = null;
        }
        node.parent = null;
        LinkedBinaryTree<E> tree = new LinkedBinaryTree<>();
        this.root = node;
        for (Position<E> position : tree) { // contamos los nodos del subarbol que vamos a eliminar
            tree.size++;
        }
        this.size -= tree.size; // eliminamos del arbol anterior los nodos que hemos contado
        return tree;
    }

    // Poner el arbol que nos pasan como hio derecho del position que nos pasan y vaciar el arbol que nos dan.
    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BinaryTreeNode<E> parent = checkPosition(p);
        if(tree == this ){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if(hasLeft(parent)){
            throw new RuntimeException("This node already has left child.");
        }
       if (tree != null && !tree.isEmpty()){ // si el arbol que vamos a insertar no es vacio
           BinaryTreeNode<E> root = checkPosition(tree.root());
           parent.setLeftChild(root);
           root.setParent(parent);
           this.size += tree.size();
       }
       // Eliminamos el arbol que nos dan
       LinkedBinaryTree<E> removedLeftTree = (LinkedBinaryTree<E>) tree;
       removedLeftTree.size = 0;
       removedLeftTree.root = null;
    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BinaryTreeNode<E> parent = checkPosition(p);
        if (tree == this){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (parent.getRightChild() != null){
            throw  new RuntimeException("This no already has right child.");
        }
        if (!tree.isEmpty()){
            BinaryTreeNode<E> root = checkPosition(tree.root());
            parent.setLeftChild(root);
            root.setParent(parent);
            this.size += tree.size();
        }
        LinkedBinaryTree<E> toRemove = (LinkedBinaryTree<E>) tree;
        toRemove.size = 0;
        toRemove.root = null;
    }

    @Override
    public boolean isComplete() {
        InorderBinaryTreeIterator<E> iterator = new InorderBinaryTreeIterator<>(this);
        while (iterator.hasNext()) {
            Position<E> p = iterator.next();
            if (this.isInternal(p)) {
                if (!this.hasLeft(p) || !this.hasRight(p)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if(isEmpty()){
            throw new RuntimeException("This tree is empty.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        List<Position<E>> children = new ArrayList<>();
        BinaryTreeNode<E> node = checkPosition(v);
        if(hasLeft(node)){
            children.add(node.LeftChild);
        }
        if(hasRight(node)){
            children.add(node.RightChild);
        }
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public boolean isInternal(Position<E> v) {
        BinaryTreeNode<E> node = checkPosition(v);
        return hasLeft(node) || hasRight(node);
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        BinaryTreeNode<E> node = checkPosition(v);
        if (isEmpty()){
            throw new RuntimeException("This tree is empty");
        }
        return (!isInternal(node) );
    }

    @Override
    public boolean isRoot(Position<E> v) {
        BinaryTreeNode<E> node = checkPosition(v);
        return (node == root);
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if(!isEmpty()){
            throw new RuntimeException("This tree already has a root.");
        }
        BinaryTreeNode<E> newRoot = new BinaryTreeNode<>(null,null,null, this,e);
        size = 1;
        return newRoot;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    BinaryTreeNode<E> checkPosition(Position<E> position){
        if (!(position instanceof BinaryTreeNode)){
            throw new RuntimeException("The position is invalid.");
        }
        BinaryTreeNode<E> aux = (BinaryTreeNode<E>) position; //casting
        //Comprobamos que el nodo esta en el arbol
        if (aux.myTree != this) {
            throw new RuntimeException("This node does not belong to this tree.");
        }
        return aux;
    }
}
