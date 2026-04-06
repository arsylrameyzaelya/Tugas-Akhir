import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LaporanOwner extends JFrame {

    private Connection conn;

    public LaporanOwner() {

        conn = Koneksi.getKoneksi();

        setTitle("Laporan Owner");
        setSize(500,400);
        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        500,400,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        double total = 0;
        double rata = 0;

        try{

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT SUM(total), AVG(total) FROM antrian");

            if(rs.next()){

                total = rs.getDouble(1);
                rata = rs.getDouble(2);

            }

        }

        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

        // ===== KOTAK TOTAL =====

        JPanel boxTotal =
                new JPanel();

        boxTotal.setLayout(null);

        boxTotal.setBounds(100,80,300,90);

        boxTotal.setBackground(Color.WHITE);

        boxTotal.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,105,180),2));

        background.add(boxTotal);

        JLabel lblTotalTitle =
                new JLabel("TOTAL PENDAPATAN");

        lblTotalTitle.setBounds(60,10,200,25);

        lblTotalTitle.setFont(
                new Font("Arial",
                        Font.BOLD,14));

        lblTotalTitle.setForeground(
                new Color(255,105,180));

        boxTotal.add(lblTotalTitle);

        JLabel lblTotal =
                new JLabel("Rp " + total);

        lblTotal.setBounds(90,40,200,30);

        lblTotal.setFont(
                new Font("Arial",
                        Font.BOLD,18));

        boxTotal.add(lblTotal);

        // ===== KOTAK RATA =====

        JPanel boxRata =
                new JPanel();

        boxRata.setLayout(null);

        boxRata.setBounds(100,200,300,90);

        boxRata.setBackground(Color.WHITE);

        boxRata.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,105,180),2));

        background.add(boxRata);

        JLabel lblRataTitle =
                new JLabel("RATA-RATA TRANSAKSI");

        lblRataTitle.setBounds(50,10,220,25);

        lblRataTitle.setFont(
                new Font("Arial",
                        Font.BOLD,14));

        lblRataTitle.setForeground(
                new Color(255,105,180));

        boxRata.add(lblRataTitle);

        JLabel lblRata =
                new JLabel("Rp " + rata);

        lblRata.setBounds(90,40,200,30);

        lblRata.setFont(
                new Font("Arial",
                        Font.BOLD,18));

        boxRata.add(lblRata);

        JButton kembali =
                new JButton("Kembali");

        kembali.setBounds(200,310,100,35);

        kembali.setBackground(
                new Color(255,105,180));

        kembali.setForeground(Color.WHITE);

        background.add(kembali);

        kembali.addActionListener(e -> {

            new Owner().setVisible(true);

            dispose();

        });

        add(background);
    }
}