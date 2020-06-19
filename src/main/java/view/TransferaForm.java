package view;

import service.MainService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TransferaForm extends JFrame {
    private JPanel panel1;
    private JButton OKButton;
    private JButton inapoiButton;
    private JTextField textFieldValoare;
    private JTextField textFieldCodClient;

    public TransferaForm() {
        add(panel1);

        OKButton.addActionListener(ev -> transferaBani());
        inapoiButton.addActionListener(ev -> returnAction());

        setLocationRelativeTo(null);
        setSize(500, 400);
        setVisible(true);
        // pentru a pozitiona fereastra mea exact in centru ecranului
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    public void transferaBani() {
        try {
            int suma = Integer.parseInt(textFieldValoare.getText());
            String codClientDestinatar = textFieldCodClient.getText();

            if (suma >= 50 && suma <= 1000) {
                MainService.getInstance().transferaBani(suma, codClientDestinatar);
//                JOptionPane.showMessageDialog(null, "Transfer reusit!");
            } else {
                JOptionPane.showMessageDialog(null, "Suma trebuie sa fie in intervalul [50, 1000] lei! ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Nu ai ales o suma corecta!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            returnAction();
        }
    }

    public void returnAction() {
        dispose();
        new MainFrame();
    }
}
