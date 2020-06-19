package view;

import model.Client;
import service.MainService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginForm extends JFrame {
    public static Client clientActual = new Client();
    private static int nrAutentificariGresite = 0;
    private JPanel panel;
    private JTextField textField1;
    private JButton loginButton;
    private JPasswordField passwordField1;

    public LoginForm() {
        add(panel);
        loginButton.addActionListener(ev -> login());
        populareDB();

        setLocationRelativeTo(null);
        setSize(400, 400);
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
        try {
            int pin = Integer.parseInt(new String(passwordField1.getPassword()));
            Optional<Client> client = MainService.getInstance().login(codClient, pin);
            if (client.isPresent()) {
                clientActual = client.get();
                new MainFrame();
                dispose();
            } else {
                nrAutentificariGresite++;
                JOptionPane.showMessageDialog(null, "Cod client sau pin gresite sau contul este blocat!", "", JOptionPane.WARNING_MESSAGE);
                if (nrAutentificariGresite == 3) {
                    JOptionPane.showMessageDialog(null, "Contul dvs. a fost blocat!", "", JOptionPane.ERROR_MESSAGE);
                    MainService.getInstance().blocareCont(codClient);
                }
            }
        } catch (NumberFormatException e) {
            nrAutentificariGresite++;
            JOptionPane.showMessageDialog(null, "Cod client sau pin gresite!", "", JOptionPane.WARNING_MESSAGE);
            if (nrAutentificariGresite == 3) {
                JOptionPane.showMessageDialog(null, "Contul dvs. a fost blocat!", "", JOptionPane.ERROR_MESSAGE);
                MainService.getInstance().blocareCont(codClient);
            }
        }
        cleanFields(textField1, passwordField1);
    }

    public void cleanFields(JTextField textField, JPasswordField passField) {
        textField.setText("");
        passField.setText("");
    }
}
