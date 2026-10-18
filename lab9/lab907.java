/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author kitti
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class lab907 extends JFrame {
    public lab907() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("ACCOUNT MONEY", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 12f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(null);
        form.setBorder(new TitledBorder("Enter Data Account Money"));
        form.setPreferredSize(new Dimension(390, 340));

        addLabel(form, "ID :", 16, 38, 30);
        addField(form, 54, 34, 90);
        addLabel(form, "MONEY :", 160, 38, 60);
        addField(form, 222, 34, 90);
        addLabel(form, "BATH :", 332, 38, 50);

        addLabel(form, "ANNUALINTERATE RATE :", 16, 78, 170);
        addField(form, 178, 74, 113);

        addLabel(form, "DAY OPEN ACCOUNT :", 16, 120, 150);
        addCombo(form, "day", 160, 116, 46);
        addCombo(form, "month", 222, 116, 90);

        addCombo(form, "year", 16, 165, 56);
        addLabel(form, "FIRST NAME  :", 86, 169, 100);
        addField(form, 190, 165, 168);

        addLabel(form, "LAST NAME   :", 16, 208, 100);
        addField(form, 120, 204, 168);

        addLabel(form, "BIRTH DAY   :", 16, 251, 100);
        addCombo(form, "day", 114, 247, 46);
        addCombo(form, "month", 178, 247, 90);
        addCombo(form, "year", 284, 247, 56);

        addLabel(form, "AGE  :", 16, 294, 50);
        addField(form, 68, 290, 58);
        addLabel(form, "YEAR", 142, 294, 50);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        buttons.add(new JButton("SAVE"));
        buttons.add(new JButton("SHOW"));
        add(buttons, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel p, String text, int x, int y, int w) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 11f));
        l.setBounds(x, y, w, 20);
        p.add(l);
    }

    private void addField(JPanel p, int x, int y, int w) {
        JTextField t = new JTextField();
        t.setBounds(x, y, w, 22);
        p.add(t);
    }

    private void addCombo(JPanel p, String text, int x, int y, int w) {
        JComboBox<String> c = new JComboBox<>(new String[]{text});
        c.setBounds(x, y, w, 24);
        p.add(c);
    }

    public static void main(String[] args) {
        lab907 frame = new lab907();
        frame.setTitle("Show Detail of Account ...");
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}