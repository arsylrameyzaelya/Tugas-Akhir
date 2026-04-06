import javax.swing.*;
import java.awt.*;

public class HasilStatus extends JFrame {

    public HasilStatus(
            int nomor,
            String status,
            double total){

        setTitle("Status Laundry");

        setSize(400,320);

        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        400,320,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        JPanel box =
                new JPanel();

        box.setLayout(null);

        box.setBounds(60,60,280,170);

        box.setBackground(Color.WHITE);

        box.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,105,180),2));

        background.add(box);

        JLabel title =
                new JLabel("STATUS LAUNDRY");

        title.setBounds(60,10,200,30);

        title.setFont(new Font(
                "Arial",Font.BOLD,16));

        title.setForeground(
                new Color(255,105,180));

        box.add(title);

        JLabel l1 =
                new JLabel("Nomor : A"+nomor);

        l1.setBounds(40,50,200,25);

        l1.setFont(new Font(
                "Arial",Font.BOLD,14));

        box.add(l1);

        JLabel l2 =
                new JLabel("Status : "+status);

        l2.setBounds(40,80,200,25);

        l2.setFont(new Font(
                "Arial",Font.BOLD,14));

        box.add(l2);

        JLabel l3 =
                new JLabel("Total : Rp "+total);

        l3.setBounds(40,110,200,25);

        l3.setFont(new Font(
                "Arial",Font.BOLD,14));

        box.add(l3);

        JButton kembali =
                new JButton("Kembali");

        kembali.setBounds(90,135,90,25);

        box.add(kembali);

        kembali.addActionListener(e -> {

            new User().setVisible(true);

            dispose();

        });

        add(background);

    }
}