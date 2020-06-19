package view;

import service.MainService;

import javax.swing.*;
import java.awt.*;

import static view.LoginForm.clientActual;

public class MainFrame extends JFrame {
    private JPanel panel;
    private JButton retrageButton;
    private JButton transferaButton;
    private JButton exitButton;
    private JLabel numeField;
    private JLabel codClientField;
    private JLabel nrContField;
    private JLabel valoareField;

    public MainFrame() {
        add(panel);
        afisareInfo();

        retrageButton.addActionListener(e -> new RetrageForm());
        retrageButton.addActionListener(e -> dispose());

        transferaButton.addActionListener(e -> new TransferaForm());
        transferaButton.addActionListener(e -> dispose());

        exitButton.addActionListener(e -> exit());

        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        // pentru a pozitiona fereastra mea exact in centru ecranului
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        // pentru a inchide procesul cand inchid frame-ul
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void afisareInfo() {
        numeField.setText(clientActual.getNume());
        codClientField.setText(clientActual.getCodClient());
        nrContField.setText(MainService.getInstance().getContClient(clientActual).getNrCont());
        valoareField.setText(String.valueOf(MainService.getInstance().getContClient(clientActual).getValoare()));
    }

    public void exit() {
        new LoginForm();
        dispose();
    }

}
