package dao;

import model.Client;
import model.Cont;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.Collectors;

public class ContDao {
    private final Connection con;

    public ContDao(Connection con) {
        this.con = con;
    }

    public void populareDB(Integer idClient) {
        String sql = "INSERT INTO Conturi (nrCont, valoare, blocat, idClient) VALUES (?, ?, ?, ?);";

        String nrCont = new Random()
                .ints(48, 90)
                .limit(13)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());

        int max = 9999, min = 1, range = max - min + 1;
        double valoare = (int) (Math.random() * range) + min;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nrCont);
            stmt.setDouble(2, valoare);
            stmt.setBoolean(3, false);
            stmt.setInt(4, idClient);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cont getContClient(Client client) {
        String sql = "SELECT * FROM Conturi WHERE idClient=?";
        Cont cont = new Cont();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nrCont = rs.getString("nrCont");
                double valoare = rs.getDouble("valoare");
                boolean blocat = rs.getBoolean("blocat");
                int idClient = rs.getInt("idClient");
                if (idClient == client.getId()) {
                    cont = new Cont(id, nrCont, valoare, blocat, idClient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cont;
    }

    public void blocareCont(Client client) throws SQLException {
        String sql = "UPDATE Conturi SET blocat=1 WHERE nrCont=?;";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, getContClient(client).getNrCont());
            stmt.executeUpdate();
        }
    }

    public void retrageBani(Client client, double valoareDeRetras) throws SQLException {
        String sql = "UPDATE Conturi SET valoare=? WHERE nrCont=?;";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(2, getContClient(client).getNrCont());
            stmt.setDouble(1, getContClient(client).getValoare() - valoareDeRetras);
            stmt.executeUpdate();
        }
    }

    public void transferaBani(int valoare, Client clientDestinatar) throws SQLException {
        String sql = "UPDATE Conturi SET valoare=? WHERE nrCont=?;";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(2, getContClient(clientDestinatar).getNrCont());
            stmt.setDouble(1, getContClient(clientDestinatar).getValoare() + valoare);
            stmt.executeUpdate();
        }
    }
}










