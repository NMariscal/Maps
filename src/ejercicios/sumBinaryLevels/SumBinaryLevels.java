package ejercicios.sumBinaryLevels;

import map.HashTableMapLP;
import material.NAryTree.NAryTree;
import material.Position;
import material.iterators.BFSIterator;

import java.util.ArrayList;

public class SumBinaryLevels {
    /*
    Tiene la capacidad de sumar los valores de todos los nodos de unos niveles determinados

    ---> Arbol Nario
     */

     NAryTree<Integer> nAryTree;

    public SumBinaryLevels(NAryTree<Integer> nAryTree) {
        this.nAryTree = nAryTree;
    }

    public int sumLevels(HashTableMapLP<Integer, Integer> levelsHashTableMapLP , NAryTree<Integer> nAryTree, ArrayList<Integer> levels){
        // vamos viendo el nivel de cada elemento y lo metemos en nuestro mapa
        int result = 0;
        for (Position<Integer> position : nAryTree){
            int level = 0;
            while (!nAryTree.isRoot(position)){
                level++;
            }
            Integer cost = levelsHashTableMapLP.get(level);
            if (cost == null){
                cost = position.getElement();
            }else{
                int newCost = cost + position.getElement();
                levelsHashTableMapLP.put(level, newCost);
            }
        }
        for(int n : levels){
            Integer value = levelsHashTableMapLP.get(n);
            if (value != null){
                result += value;
            }
        }
        return result;
    }
}
