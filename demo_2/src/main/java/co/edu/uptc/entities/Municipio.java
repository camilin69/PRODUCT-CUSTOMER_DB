package co.edu.uptc.entities;

public class Municipio {
    private int id;
    private String name;

    public Municipio() {}

    public Municipio(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
