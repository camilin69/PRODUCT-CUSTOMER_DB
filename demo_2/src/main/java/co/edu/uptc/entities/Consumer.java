package co.edu.uptc.entities;

public class Consumer {
    private int id;
    private String name;
    private int age;
    private String municipio;
    private short status;
    private String email;


    public Consumer(int id, String name, int age, String municipio, short status, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.municipio = municipio;
        this.status = status;
        this.email = email;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}