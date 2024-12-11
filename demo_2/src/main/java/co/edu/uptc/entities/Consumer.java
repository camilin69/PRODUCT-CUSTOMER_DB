package co.edu.uptc.entities;

public class Consumer {
    private int id;
    private String name;
    private int age;
    private short status;
    private String email;
    private String password;
    private int id_municipio;
    public Consumer(int id, String name, int age, short status, String email, String password, int id_municipio) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = status;
        this.email = email;
        this.password = password;
        this.id_municipio = id_municipio;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId_municipio() {
        return id_municipio;
    }
    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    


}