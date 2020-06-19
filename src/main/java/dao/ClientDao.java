package dao;

import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientDao {
    private final Connection con;

    public ClientDao(Connection con) {
        this.con = con;
    }

    public Optional<Client> findClient(String codClient) throws SQLException {
        String sql = "SELECT * FROM Clienti WHERE codClient=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNume(rs.getString("nume"));
                client.setCodClient(rs.getString("codClient"));
                client.setPin(rs.getInt("pin"));
                client.setNrAutentificariGresite(rs.getInt("nrAutentificariGresite"));
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

//    public Optional<Client> findClient(String codClient, int pin) throws SQLException {
//        String sql = "SELECT * FROM Clienti WHERE codClient=? AND pin=?";
//        try (PreparedStatement stmt = con.prepareStatement(sql)) {
//            stmt.setString(1, codClient);
//            stmt.setInt(2, pin);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Client client = new Client();
//                client.setId(rs.getInt("id"));
//                client.setNume(rs.getString("nume"));
//                client.setCodClient(rs.getString("codClient"));
//                client.setPin(rs.getInt("pin"));
//                return Optional.of(client);
//            }
//        }
//        return Optional.empty();
//    }

    public Optional<Integer> populareDB() {
        String sql = "INSERT INTO Clienti (nume, codClient, pin) VALUES (?, ?, ?);";

        String nume = new Random().ints(7, 97, 122)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());

        String randomUUID = String.valueOf(UUID.randomUUID()).replaceAll("-", "").toUpperCase();
        StringBuffer sb = new StringBuffer(randomUUID);
        String codClient = String.valueOf(sb.subSequence(1, 7));

        int max = 9999, min = 1000, range = max - min + 1;
        int pin = (int) (Math.random() * range) + min;

        try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nume);
            stmt.setString(2, codClient);
            stmt.setInt(3, pin);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                Integer id = Integer.parseInt(rs.getString(1));
                return Optional.of(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateClientByAutentificariGresite(String codClient) throws SQLException {
        String sql = "UPDATE Clienti SET nrAutentificariGresite = nrAutentificariGresite + 1 WHERE codClient=?;";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codClient);
            stmt.executeUpdate();
        }
    }

}
