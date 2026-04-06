import javax.swing.*;
import java.awt.*;

public class HasilAntrian extends JFrame {

    public HasilAntrian(String nama,int nomor){

        setTitle("Hasil Antrian");

        setSize(400,300);

        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        400,300,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        JPanel box =
                new JPanel();

        box.setLayout(null);

        box.setBounds(70,60,260,140);

        box.setBackground(Color.WHITE);

        box.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,105,180),2));

        background.add(box);

        JLabel title =
                new JLabel("HASIL ANTRIAN");

        title.setBounds(50,10,200,30);

        title.setFont(new Font(
                "Arial",Font.BOLD,16));

        title.setForeground(
                new Color(255,105,180));

        box.add(title);

        JLabel l1 =
                new JLabel("Nama : "+nama);

        l1.setBounds(30,50,200,25);

        l1.setFont(new Font(
                "Arial",Font.BOLD,14));

        box.add(l1);

        JLabel l2 =
                new JLabel("Nomor : A"+nomor);

        l2.setBounds(30,80,200,25);

        l2.setFont(new Font(
                "Arial",Font.BOLD,14));

        box.add(l2);

        JButton kembali =
                new JButton("Kembali");

        kembali.setBounds(90,105,90,25);

        box.add(kembali);

        kembali.addActionListener(e -> {

            new User().setVisible(true);

            dispose();

        });

        add(background);

    }
}