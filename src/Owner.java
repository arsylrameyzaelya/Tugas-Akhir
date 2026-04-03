import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Owner extends JFrame {

    private Connection conn;
    private JTable table;
    private DefaultTableModel model;
    private JLabel hasil;

    public Owner() {

        conn = Koneksi.getKoneksi();

        setTitle("Best Laundry - Owner");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(750, 650, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        // TITLE
        JLabel title = new JLabel("Owner Laundry");
        title.setBounds(280, 20, 300, 40);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // BUTTON
        JButton btnData = btn("Lihat Data", 80);
        JButton btnTotal = btn("Total Pendapatan", 140);
        JButton btnRata = btn("Rata-rata", 200);
        JButton btnKeluar = btn("Keluar", 260);

        panel.add(btnData);
        panel.add(btnTotal);
        panel.add(btnRata);
        panel.add(btnKeluar);

        // TABLE
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("No HP");
        model.addColumn("Antrian");
        model.addColumn("Layanan");
        model.addColumn("Status");
        model.addColumn("Total");

        table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(0,150,255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 320, 650, 250);
        panel.add(sp);

        // OUTPUT
        hasil = new JLabel("Output: -");
        hasil.setBounds(50, 580, 650, 30);
        hasil.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hasil.setForeground(Color.YELLOW);
        panel.add(hasil);

        add(panel);

        // ACTION
        btnData.addActionListener(e -> tampilData());
        btnTotal.addActionListener(e -> totalPendapatan());
        btnRata.addActionListener(e -> rataRata());
        btnKeluar.addActionListener(e -> keluar());
    }

    private JButton btn(String text, int y) {
        JButton b = new JButton(text);
        b.setBounds(250, y, 250, 40);
        b.setBackground(new Color(0,150,255));
        b.setForeground(Color.WHITE);
        return b;
    }

    // LIHAT DATA (JOIN)
    private void tampilData() {
        try {
            model.setRowCount(0);

            String sql = "SELECT a.id, a.nama, a.no_hp, a.nomor_antrian, l.nama_layanan, a.status, a.total " +
                         "FROM antrian a JOIN layanan l ON a.id_layanan = l.id_layanan";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        "A" + rs.getInt("nomor_antrian"),
                        rs.getString("nama_layanan"),
                        rs.getString("status"),
                        rs.getDouble("total")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // TOTAL (SUM)
    private void totalPendapatan() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(total) FROM antrian");

            if (rs.next()) {
                hasil.setText("Total Pendapatan: Rp " + rs.getDouble(1));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // RATA-RATA (AVG)
    private void rataRata() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT AVG(total) FROM antrian");

            if (rs.next()) {
                hasil.setText("Rata-rata Transaksi: Rp " + rs.getDouble(1));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // KELUAR
    private void keluar() {
        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Yakin mau keluar?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
          System.exit(0); 
        }
    }
}