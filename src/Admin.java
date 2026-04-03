import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

// INTERFACE
interface Operasi {
    void tampilData();
}

// ABSTRACT CLASS
abstract class BaseApp extends JFrame {
    abstract void setJudul();
}

// CLASS ADMIN
public class Admin extends BaseApp implements Operasi {

    private Connection conn;
    private JTable table;
    private DefaultTableModel model;

    public Admin() {

        conn = Koneksi.getKoneksi();

        setJudul();
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND GAMBAR
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(700, 600, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        // ===== TITLE =====
        JLabel title = new JLabel("Admin Laundry");
        title.setBounds(260, 20, 300, 40);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // ===== BUTTON =====
        JButton btnData = btn("Lihat Data", 80);
        JButton btnUpdate = btn("Update Status", 140);
        JButton btnHitung = btn("Hitung Total", 200);

        panel.add(btnData);
        panel.add(btnUpdate);
        panel.add(btnHitung);

        // ===== TABLE MODEL =====
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Antrian");
        model.addColumn("Layanan");
        model.addColumn("Status");
        model.addColumn("Total");
        model.addColumn("No. Hp");

        table = new JTable(model);

        
        // TABLE PUTIH (BERSIH & JELAS)
        table.setOpaque(true);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);

        // HEADER
        table.getTableHeader().setBackground(new Color(255,105,180));
        table.getTableHeader().setForeground(Color.WHITE);

        // SCROLL NORMAL (PUTIH)
        JScrollPane sp = new JScrollPane(table);
        sp.setOpaque(true);
        sp.getViewport().setOpaque(true);
        sp.setBounds(50, 270, 600, 250);

        panel.add(sp);

        add(panel);

        // ===== ACTION =====
        btnData.addActionListener(e -> tampilData());
        btnUpdate.addActionListener(e -> updateStatus());
        btnHitung.addActionListener(e -> hitungTotal());
    }

    // POLYMORPHISM
    @Override
    void setJudul() {
        setTitle("Best Laundry - Admin");
    }

    private JButton btn(String text, int y) {
        JButton b = new JButton(text);
        b.setBounds(220, y, 250, 40);
        b.setBackground(new Color(255,105,180));
        b.setForeground(Color.WHITE);
        return b;
    }

    // JOIN + SELECT
    @Override
    public void tampilData() {
        try {
            model.setRowCount(0);

            String sql = "SELECT a.id, a.nama, a.nomor_antrian, l.nama_layanan, a.status, a.total, a.no_hp " +
                         "FROM antrian a JOIN layanan l ON a.id_layanan = l.id_layanan";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama"),
                        "A" + rs.getInt("nomor_antrian"),
                        rs.getString("nama_layanan"),
                        rs.getString("status"),
                        rs.getDouble("total"),
                        rs.getString("no_hp")
     
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // UPDATE STATUS (TRANSACTION + DROPDOWN)
    private void updateStatus() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data dulu!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            String[] pilihan = {"Menunggu", "Diproses", "Selesai"};
            String status = (String) JOptionPane.showInputDialog(
                    this,
                    "Pilih Status:",
                    "Update Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    pilihan,
                    pilihan[0]
            );

            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE antrian SET status=? WHERE id=?"
            );
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

            conn.commit();

            JOptionPane.showMessageDialog(this, "Berhasil!");
            tampilData();

        } catch (Exception e) {
            try { conn.rollback(); } catch(Exception ex){}
            JOptionPane.showMessageDialog(this, "Gagal!");
        }
    }

    // STORED PROCEDURE
    private void hitungTotal() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data dulu!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            double berat = Double.parseDouble(
                    JOptionPane.showInputDialog("Masukkan berat (kg):")
            );

            CallableStatement cs = conn.prepareCall("CALL hitung_total(?, ?)");
            cs.setInt(1, id);
            cs.setDouble(2, berat);
            cs.execute();

            JOptionPane.showMessageDialog(this, "Total dihitung!");
            tampilData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {
        new Admin().setVisible(true);
    }
}