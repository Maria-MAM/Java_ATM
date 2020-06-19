package model;

public class Cont {
    private int id;
    private String nrCont;
    private double valoare;
    private int idClient;

    public Cont() {
    }

    public Cont(String nrCont, double valoare, int idClient) {
        this.nrCont = nrCont;
        this.valoare = valoare;
        this.idClient = idClient;
    }

    public Cont(int id, String nrCont, double valoare, int idClient) {
        this.id = id;
        this.nrCont = nrCont;
        this.valoare = valoare;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNrCont() {
        return nrCont;
    }

    public void setNrCont(String nrCont) {
        this.nrCont = nrCont;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "id=" + id +
                ", nrCont='" + nrCont + '\'' +
                ", valoare=" + valoare +
                ", idClient=" + idClient +
                '}';
    }
}
