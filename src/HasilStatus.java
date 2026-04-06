import javax.swing.*;
import java.awt.*;

public class HasilStatus extends JFrame {

    public HasilStatus(int nomor, String status, double total) {

        setTitle("Status Laundry");
        setSize(400,300);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        JLabel l1 = new JLabel("Nomor : A" + nomor);
        l1.setBounds(120,70,250,30);
        l1.setForeground(Color.GREEN);
        l1.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(l1);

        JLabel l2 = new JLabel("Status : " + status);
        l2.setBounds(120,110,250,30);
        l2.setForeground(Color.GREEN);
        l2.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(l2);

        JLabel l3 = new JLabel("Total : Rp " + total);
        l3.setBounds(120,150,250,30);
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(l3);

        JButton kembali = new JButton("Kembali");
        kembali.setBounds(140,190,100,35);
        panel.add(kembali);

        kembali.addActionListener(e -> {
            new User().setVisible(true);
            dispose();
        });

        add(panel);
    }
}