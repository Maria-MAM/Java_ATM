package model;

public class Client {
    private int id;
    private String nume;
    private String codClient;
    private int pin;
    private int nrAutentificariGresite;

    public Client() {
    }

    public Client(String nume, String codClient, int pin) {
        this.nume = nume;
        this.codClient = codClient;
        this.pin = pin;
    }

    public Client(int id, String nume, String codClient, int pin) {
        this.id = id;
        this.nume = nume;
        this.codClient = codClient;
        this.pin = pin;
    }

    public Client(String nume, String codClient, int pin, int nrAutentificariGresite) {
        this.nume = nume;
        this.codClient = codClient;
        this.pin = pin;
        this.nrAutentificariGresite = nrAutentificariGresite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCodClient() {
        return codClient;
    }

    public void setCodClient(String codClient) {
        this.codClient = codClient;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getNrAutentificariGresite() {
        return nrAutentificariGresite;
    }

    public void setNrAutentificariGresite(int nrAutentificariGresite) {
        this.nrAutentificariGresite = nrAutentificariGresite;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", codClient='" + codClient + '\'' +
                ", pin=" + pin +
                ", nrAutentificariGresite=" + nrAutentificariGresite +
                '}';
    }
}
