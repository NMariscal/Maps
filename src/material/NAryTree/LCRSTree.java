package material.NAryTree;

import material.Position;
import material.iterators.BFSIterator;

import java.util.Iterator;

public class LCRSTree<E> implements NAryTree<E>{
    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    @Override
    public E replace(Position<E> p, E e) {
        LCRSNode<E> node = checkPosition(p);
        E pElement = node.getElement();
        node.setElement(e);
        return pElement;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        LCRSNode<E> node1 = checkPosition(p1);
        LCRSNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.element);
        node2.setElement(aux);

    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        LCRSNode<E> parent = checkPosition(p);
        LCRSNode<E> newNode = new LCRSNode<>(element,parent, null, null,this);
        LCRSNode<E> child = parent.getLeftChild();
        if (child == null){ // si no tiene hijo
            parent.setLeftChild(newNode);
        }else{ // si tiene otros hios, buscamos el último
            while (child.getRightSibling() != null){
                child = child.getRightSibling();
            }
            child.setRightSibling(newNode);
        }
        size++;
        return newNode;
    }

    @Override
    public void remove(Position<E> p) {
        LCRSNode<E> node = checkPosition(p);
        if(node == root){ // si el nodo dado es la raiz : eliminamos el árbol entero
            root = null;
            size = 0;
        }else{
            // recorremos el arbol desde la posicion y eliminamos los hijos
            Iterator<Position<E>> it = new BFSIterator<>(this, p);
            while (it.hasNext()){
                LCRSNode<E> next = checkPosition(it.next());
                next.setMyTree(null);
                size--;
            }
            LCRSNode<E> parent = node.getParent();
            LCRSNode<E> leftChild = parent.getLeftChild();
            if (leftChild == node) { // si es hijo izquierdo , le asignas la parte derecha
                parent.setLeftChild(leftChild.getRightSibling());
            } else { // seguimos buscando
                LCRSNode<E> rightSibling = leftChild;
                while (rightSibling.getRightSibling() != node) {
                    rightSibling = rightSibling.getRightSibling(); // avanzas por los hermanos hasta encntrarlo
                }
                // Una vez encontrado el nodo le asigno el más a la zquierda ??
                rightSibling.setRightSibling(rightSibling.getRightSibling().getRightSibling());
            }
        }
        node.setMyTree(null);
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {

    }

    class LCRSNode<E> implements Position<E>{
        private E element;
        private LCRSNode<E> parent;
        private LCRSNode<E> leftChild;
        private LCRSNode<E> rightSibling;
        private LCRSTree<E> myTree;

        public LCRSNode(E element, LCRSNode<E> parent, LCRSNode<E> leftChild, LCRSNode<E> rightSibling, LCRSTree<E> myTree) {
            this.element = element;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightSibling = rightSibling;
            this.myTree = myTree;
        }

        @Override
        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public LCRSNode<E> getParent() {
            return parent;
        }

        public void setParent(LCRSNode<E> parent) {
            this.parent = parent;
        }

        public LCRSNode<E> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(LCRSNode<E> leftChild) {
            this.leftChild = leftChild;
        }

        public LCRSNode<E> getRightSibling() {
            return rightSibling;
        }

        public void setRightSibling(LCRSNode<E> rightSibling) {
            this.rightSibling = rightSibling;
        }

        public LCRSTree<E> getMyTree() {
            return myTree;
        }

        public void setMyTree(LCRSTree<E> myTree) {
            this.myTree = myTree;
        }
    }
    int size;
    LCRSNode<E> root;

    public LCRSTree(int size, LCRSNode<E> root) {
        this.size = 0;
        this.root = null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LCRSNode<E> getRoot() {
        return root;
    }

    public void setRoot(LCRSNode<E> root) {
        this.root = root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if (root == null){
            throw new RuntimeException("This tree is empty.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        LCRSNode<E> parent = node.getParent();
        if (parent == null){
            throw new RuntimeException("This node not has parent.");
        }
        return parent;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return (Iterable<? extends Position<E>>) node.getLeftChild();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return (!isLeaf(v) && !(isRoot(v)));
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        return ((node.getLeftChild() == null) && (node.getRightSibling() == null));
    }

    @Override
    public boolean isRoot(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return (node == this.root);
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if (size == 0){
        LCRSNode<E> root = new LCRSNode<>(e, null,null,null, this);
        this.root = root;
        this.size = 1;
        }else{
            throw new RuntimeException("This tree has already a root");
        }
        return root;
    }

    LCRSNode<E> checkPosition(Position<E> p){
        if (!(p instanceof LCRSNode)){
            throw new RuntimeException("The position is invalid.");
        }
        LCRSNode<E> aux = (LCRSNode<E>) p; //casting
        //Comprobamos que el nodo esta en el arbol
        if (aux.myTree != this) {
            throw new RuntimeException("This node does not belong to this tree.");
        }
        return aux;
    }




}
