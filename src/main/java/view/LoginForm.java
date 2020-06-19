package view;

import model.Client;
import service.MainService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginForm extends JFrame {
    public static Client clientActual = new Client();
    //    private static int nrAutentificariGresite = 0;
    private JPanel panel;
    private JTextField textField1;
    private JButton loginButton;
    private JPasswordField passwordField1;

    public LoginForm() {
        add(panel);
        loginButton.addActionListener(ev -> login());
        populareDB();

        setLocationRelativeTo(null);
        setSize(500, 400);
        setVisible(true);
        // pentru a pozitiona fereastra mea exact in centru ecranului
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        // pentru a inchide procesul cand inchid frame-ul
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void populareDB() {
        MainService.getInstance().populareDB();
    }

    public void login() {
        String codClient = textField1.getText();
        Optional<Client> client = MainService.getInstance().login(codClient);
        try {
            int pin = Integer.parseInt(new String(passwordField1.getPassword()));
            if (client.isPresent() && client.get().getPin() == pin && client.get().getNrAutentificariGresite() < 3) {
                clientActual = client.get();
                new MainFrame();
                dispose();
            } else {
                MainService.getInstance().updateClientByAutentificariGresite(codClient);
                JOptionPane.showMessageDialog(null, "Cod client sau pin gresite sau contul este blocat!", "", JOptionPane.WARNING_MESSAGE);
                if (client.isPresent() && client.get().getNrAutentificariGresite() >= 2) {
                    JOptionPane.showMessageDialog(null, "Contul dvs. este blocat!", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            MainService.getInstance().updateClientByAutentificariGresite(codClient);
            if (client.isPresent() && client.get().getNrAutentificariGresite() >= 2) {
                JOptionPane.showMessageDialog(null, "Contul dvs. este blocat!", "", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cod client sau pin gresite!", "", JOptionPane.ERROR_MESSAGE);
            }
        }
        cleanFields(textField1, passwordField1);
    }

    public void cleanFields(JTextField textField, JPasswordField passField) {
        textField.setText("");
        passField.setText("");
    }
}
