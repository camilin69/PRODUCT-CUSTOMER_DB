package co.edu.uptc.demo_1;

public class Consumer {
    private String id_consumer;
    private int id;
    private String name;
    private int age;
    private String municipio;
    private short status;
    private String email;


    public Consumer(String id_consumer, int id, String name, int age, String municipio, short status, String email) {
        this.id_consumer = id_consumer;
        this.id = id;
        this.name = name;
        this.age = age;
        this.municipio = municipio;
        this.status = status;
        this.email = email;
    }

    public String getId_consumer() {
        return id_consumer;
    }

    public void setId_consumer(String id_consumer) {
        this.id_consumer = id_consumer;
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