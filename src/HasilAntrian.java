import javax.swing.*;
import java.awt.*;

public class HasilAntrian extends JFrame {

    public HasilAntrian(String nama, int nomor) {

        setTitle("Hasil Antrian");
        setSize(400,300);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        JLabel l1 = new JLabel("Nama : " + nama);
        l1.setBounds(120,80,250,30);
        l1.setForeground(Color.GREEN);
        l1.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(l1);

        JLabel l2 = new JLabel("Nomor Antrian : A" + nomor);
        l2.setBounds(120,120,250,30);
        l2.setForeground(Color.GREEN);
        l2.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(l2);

        JButton kembali = new JButton("Kembali");
        kembali.setBounds(140,170,100,35);
        panel.add(kembali);

        kembali.addActionListener(e -> {
            new User().setVisible(true);
            dispose();
        });

        add(panel);
    }
}