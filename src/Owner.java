import javax.swing.*;
import java.awt.*;

public class Owner extends JFrame {

    public Owner() {

        setTitle("Menu Owner");
        setSize(400,350);
        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        400,350,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        JLabel title =
                new JLabel("MENU OWNER");

        title.setBounds(130,40,200,30);

        title.setFont(new Font(
                "Arial",Font.BOLD,18));

        title.setForeground(Color.WHITE);

        background.add(title);

        JButton btnData =
                tombol("Lihat Data",100);

        JButton btnLaporan =
                tombol("Total & Rata-rata",160);

        JButton btnKeluar =
                tombol("Keluar",220);

        background.add(btnData);
        background.add(btnLaporan);
        background.add(btnKeluar);

        add(background);

        btnData.addActionListener(e -> {

            new DataOwner().setVisible(true);

            dispose();

        });

        btnLaporan.addActionListener(e -> {

            new LaporanOwner().setVisible(true);

            dispose();

        });

        btnKeluar.addActionListener(e -> {

            System.exit(0);

        });
    }

    private JButton tombol(String text,int y){

        JButton b =
                new JButton(text);

        b.setBounds(120,y,160,40);

        b.setBackground(
                new Color(255,105,180));

        b.setForeground(Color.WHITE);

        return b;
    }
}