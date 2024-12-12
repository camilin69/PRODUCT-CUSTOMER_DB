package co.edu.uptc.entities;

public class Consumer {
    private int id;
    private String name;
    private int age;
    private String municipio;
    private int estrato;
    private String email;
    private String password;

    public Consumer() {}

    public Consumer(int id, String name, int age, String municipio, int estrato, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.municipio = municipio;
        this.estrato = estrato;
        this.email = email;
        this.password = password;
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
    public int getEstrato() {
        return estrato;
    }
    public void setEstrato(int estrato) {
        this.estrato = estrato;
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
}
