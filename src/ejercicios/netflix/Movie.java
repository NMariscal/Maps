package ejercicios.netflix;

import java.util.List;

public class Movie {
    String name;
    int year;
    List<String> types;

    public Movie(String name, int year, List<String> types) {
        this.name = name;
        this.year = year;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
