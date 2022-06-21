package ejercicios.netflix;

import map.HashTableMapLP;

import java.util.*;

public class Netflix {
    HashTableMapLP< Integer, ArrayList<Movie>> yearHashTableMapLP = new HashTableMapLP<>();
    HashTableMapLP<String, ArrayList<Movie>> typeHashTableMapLP = new HashTableMapLP<>();
    HashTableMapLP<String, ArrayList<Movie>> movieHashTableMapLP = new HashTableMapLP<>();

    // Añadir la pelicula al gestor
    // Al añadir una pelicula hay que añadirla a todos los mapas.
    public void addContent(String title, int year, ArrayList<String> types){
        Movie newMovie = new Movie(title, year, types);

        //Almacenar la pelicula segun su titulo
        ArrayList<Movie> list = movieHashTableMapLP.get(title);
        if (list == null){
            list = new ArrayList<>();
            list.add(newMovie);
            movieHashTableMapLP.put(title, list);
        }else{
            list.add(newMovie);
        }

        //Almacenar la pelicula segun su tipo
        for(String type : types){
            list = typeHashTableMapLP.get(type);
            if(list == null){
                list = new ArrayList<>();
                list.add(newMovie);
                typeHashTableMapLP.put(type, list);
            }else{
                list.add(newMovie);
            }
        }

        //Almacenar la pelicula segun el año
        ArrayList<Movie> yearList = yearHashTableMapLP.get(year);
        if(yearList == null){
            yearList = new ArrayList<>();
            yearList.add(newMovie);
            yearHashTableMapLP.put(year, yearList);
        }else {
            yearList.add(newMovie);
        }
    }

    // Consulta por nombre: dado un nombre de pelicula, se recuperaran todas las que cincidan con el titulo dado
    public Iterable<Movie> findTitle(String title){
        ArrayList<Movie> titles;
        titles = movieHashTableMapLP.get(title);
        return titles;
    }

    // Consulta por año
    public Iterable<Movie> findYear(int year){
        ArrayList<Movie> years;
        years = yearHashTableMapLP.get(year);
        return years;
    }

    // Consulta por tipo: Dado un tipo o conjunto de tipos
    public Iterable<Movie> findType(String type){
        ArrayList<Movie> typeList;
        typeList = typeHashTableMapLP.get(type);
        return typeList;
    }

    public Iterable<Movie> findType(Set<String> types){
        ArrayList<Movie> typesList = null;
        for(String type : types){
            ArrayList<Movie> movies = typeHashTableMapLP.get(type); // sacas todas las pelis de ese genero
            if(typesList == null){ // si la lista que vamos a devolver es vacia
                typesList = movies;
            }else{
                ArrayList<Movie> toRemove = new ArrayList<>(); // almacenas las peliculas no comunes para luego eliminarlas
                for (Movie movie : typesList){
                    if(!movies.contains(movie)){
                        toRemove.add(movie);
                    }
                }
                for (Movie m : toRemove){
                    typesList.remove(m);
                }
            }
        }
        return typesList;
    }
}
