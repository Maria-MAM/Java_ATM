package service;

import dao.ClientDao;
import dao.ContDao;
import model.Client;
import model.Cont;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static view.LoginForm.clientActual;

public class MainService {
    private final String url = "jdbc:mysql://localhost/ATM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String user = "root";
    private final String pass = "";
    private Connection con;

    private MainService() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MainService getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public Optional<Client> login(String codClient) {
        ClientDao clientDao = new ClientDao(con);
        try {
            Optional<Client> optionalClient = clientDao.findClient(codClient);
            if (optionalClient.isPresent()) {
                return optionalClient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateClientByAutentificariGresite(String codClient) {
        ClientDao clientDao = new ClientDao(con);
        try {
            Optional<Client> optionalClient = clientDao.findClient(codClient);
            if (optionalClient.isPresent()) {
                clientDao.updateClientByAutentificariGresite(codClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populareDB() {
        ClientDao clientDao = new ClientDao(con);
        Optional<Integer> idClientCreat = clientDao.populareDB();
        ContDao contDao = new ContDao(con);
        Integer idClientNou = idClientCreat.get();
        contDao.populareDB(idClientNou);
    }

    public Cont getContClient(Client client) {
        ContDao contDao = new ContDao(con);
        return contDao.getContClient(client);
    }

    public void retrageBani(int valoare) throws SQLException {
        ContDao contDao = new ContDao(con);
        if (getContClient(clientActual).getValoare() >= valoare) {
            contDao.retrageBani(clientActual, valoare);
//            JOptionPane.showMessageDialog(null, "Retragere reusita!");
        } else
            JOptionPane.showMessageDialog(null, "Nu aveti aceasta suma disponibila in cont!", "", JOptionPane.ERROR_MESSAGE);
    }

    public void transferaBani(int valoare, String codClientDestinatar) throws SQLException {
        try {
            ContDao contDao = new ContDao(con);
            ClientDao clientDao = new ClientDao(con);
            Optional<Client> optionalClient = clientDao.findClient(codClientDestinatar);
            Client clientDestinatar = optionalClient.get();
            if (clientDestinatar.getId() != clientActual.getId()) {
                retrageBani(valoare);
                contDao.transferaBani(valoare, clientDestinatar);
                JOptionPane.showMessageDialog(null, "Transfer reusit!", "", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Codul client al destinatarului nu al tau!", "", JOptionPane.WARNING_MESSAGE);
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Cod client inexistent!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final class SingletonHolder {
        private static final MainService INSTANCE = new MainService();
    }
}













