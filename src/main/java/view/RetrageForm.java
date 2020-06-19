package view;

import service.MainService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class RetrageForm extends JFrame {
    private JPanel panel1;
    private JButton OKButton;
    private JButton inapoiButton;
    private JTextField textField1;

    public RetrageForm() {
        add(panel1);


        OKButton.addActionListener(ev -> retrageBani());
        inapoiButton.addActionListener(ev -> new MainFrame());

        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        // pentru a pozitiona fereastra mea exact in centru ecranului
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }


    public void retrageBani() {
        try {
            int suma = Integer.parseInt(textField1.getText());
            if (suma >= 10 && suma <= 2000) {
                MainService.getInstance().retrageBani(suma);
                JOptionPane.showMessageDialog(null, "Retragere reusita!", "", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Suma trebuie sa fie in intervalul [10, 2000] lei! ", "", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Nu ai ales o suma corecta!", "", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dispose();
            new MainFrame();
        }

    }
}
