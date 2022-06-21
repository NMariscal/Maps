package material.NAryTree;

import javafx.geometry.Pos;
import material.Position;
import material.iterators.BFSIterator;
import sun.net.www.http.PosterOutputStream;

import java.util.*;

public class LinkedTree<E> implements NAryTree<E> {
    // Nodos del arbol
    static class TreeNode<E> implements Position<E> {
        // Iterador
        static class LinkedTreeIterator<E> implements Iterator<Position<E>> {
            // recorrido en anchura = cola
            ArrayDeque<TreeNode<E>> queue = new ArrayDeque<>();
            LinkedTree<E> tree;

            // Contructor del iterador: metemos la raiz en la cola
            LinkedTreeIterator(TreeNode<E> root) {
                this.queue.push(root);
            }

            @Override
            public boolean hasNext() {
                return queue.size() != 0;
            }

            @Override
            public Position<E> next() {
                TreeNode<E> aux = queue.pop(); // pop = desencolar
                for (TreeNode<E> node : aux.getChildren()) {
                    this.queue.push(node); // meto los hijos en la cola
                }
                return aux;
            }
        } // end class iterator

        LinkedTree<E> myTree;
        E element;
        List<TreeNode<E>> children;
        TreeNode<E> parent;

        public TreeNode(LinkedTree<E> tree, E element, List<TreeNode<E>> children, TreeNode<E> parent) {
            this.myTree = tree;
            this.element = element;
            this.children = children;
            this.parent = parent;
        }

        public LinkedTree<E> getMyTree() {
            return myTree;
        }

        public void setMyTree(LinkedTree<E> myTree) {
            this.myTree = myTree;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public List<TreeNode<E>> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode<E>> children) {
            this.children = children;
        }

        public TreeNode<E> getParent() {
            return parent;
        }

        public void setParent(TreeNode<E> parent) {
            this.parent = parent;
        }
        @Override
        public E getElement() {
            return element;
        }
    } // end class treeNode
    int size;
    TreeNode<E> root;

    public LinkedTree() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Position<E> root() throws RuntimeException {
        // NO PODEMOS DEVOLVER POSITION NULL !!!
        if (root == null){
            throw new RuntimeException("The tree is empty.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        TreeNode<E> node = checkPosition(v); // para comprobar que es válido
        Position<E> parent = node.getParent();
        if (parent == null){
            throw new RuntimeException("This node has not parent.");
        }
        return parent;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal( Position<E> v) {
        return !(isLeaf(v));
    }

    @Override
    public boolean isLeaf( Position<E> v) throws RuntimeException {
        TreeNode<E> node = checkPosition(v);
        return (node.getChildren() == null || node.getChildren().isEmpty());
    }

    @Override
    public boolean isRoot( Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node == root;
    }

    @Override
    public  Position<E> addRoot(E e) throws RuntimeException {
        if (this.root != null) {
            throw new RuntimeException("This tree already has a root.");
        }
        TreeNode<E> newNode = new TreeNode<>(this, e, null, null);
        this.root = newNode;
        this.size = 1;
        return  newNode; // Si tenemos que devolver un Position vale devolver un TreeNode , es compatible
    }
    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkPosition(p);
        E element = node.getElement();
        node.setElement(e);
        return element;
    }


    // Cambiar los elementos (E) de dos position dados
    @Override
    public void swapElements( Position<E> p1,  Position<E> p2) {
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E e2 = node2.getElement(); // guardamos en una variable
        node2.setElement(node1.getElement());
        node1.setElement(e2);
    }

    // Añade un nodo hijo en el position que se pasa cuyo elemento es element
    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode = new TreeNode<>(this, element, new ArrayList<>(), parent);
        List<TreeNode<E>> children = parent.getChildren();
        children.add(newNode);
        this.size++;
        return newNode;
    }

    @Override
    public void remove( Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        TreeNode<E> parent = node.getParent();
        if (parent != null) {
            Iterator<Position<E>> iterator = new BFSIterator<>(this, p);
            int counter = 0;
            while (iterator.hasNext()) {
                TreeNode<E> next = checkPosition(iterator.next());
                next.setMyTree(null); // borramos todos los nodos desde el position que nos dan
                counter++; // contador de los nodos desde esa posicion
            }
            size = size - counter; // actualizamos el tamaño
            parent.getChildren().remove(node);
        }else{ // es el nodo raiz = borramos el árbol
            this.root = null;
            this.size = 0;
        }
        node.setMyTree(null);

    }

    // Mueve el nodo POrigen para hacerlo hijo de PDestino
    @Override
    public void moveSubtree( Position<E> pOrig,  Position<E> pDest) throws RuntimeException {
        TreeNode<E> origen = checkPosition(pOrig);
        TreeNode<E> destino = checkPosition(pDest);
        origen.setParent(destino);
        TreeNode<E> parent1 = origen.getParent();
        TreeNode<E> parent2 = destino.getParent();
        parent2.getChildren().add(origen);
        parent1.getChildren().remove(origen);
    }


    TreeNode<E> checkPosition(Position<E> position){
        //instanceof verifica que p es distinto de null.
        if (!(position instanceof TreeNode)) { //(p == null)
            throw new RuntimeException("The position is invalid.");
        }
        TreeNode<E> aux = (TreeNode<E>) position; //casting
        //Comprobamos que el nodo esta en el arbol
        if (aux.myTree != this) {
            throw new RuntimeException("This node does not belong to this tree.");
        }
        return aux;
    }

    /*


    private void posOrderAux(Position<E> p, List<Position<E>> pos) throws RuntimeException {
        for (Position<E> w : children(p)) {
            posOrderAux(w, pos);
        }
        pos.add(p);
    }

    public Iterable<Position<E>> posOrder() {
        List<Position<E>> positions = new ArrayList<Position<E>>();
        if (!this.isEmpty()) {
            posOrderAux(this.root, positions);
        }
        return positions;
    }*/

}
