import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Admin extends JFrame {

    private Connection conn;
    private JTable table;
    private DefaultTableModel model;

    public Admin() {

        conn = Koneksi.getKoneksi();

        setTitle("Best Laundry - Admin");

        // PERBESAR WINDOW
        setSize(600, 650);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        600, 650,
                        Image.SCALE_SMOOTH);

        JLabel panel =
                new JLabel(new ImageIcon(img));

        panel.setLayout(null);

        // TITLE
        JLabel title =
                new JLabel("Admin Laundry");

        title.setBounds(180, 30, 300, 40);

        title.setFont(
                new Font(
                        "Comic Sans MS",
                        Font.BOLD,
                        24));

        title.setForeground(Color.BLACK);

        panel.add(title);

        // ===== BUTTON =====

        JButton btnData =
                createButton("Lihat Data", 100);

        JButton btnUpdate =
                createButton("Update Status", 160);

        JButton btnHitung =
                createButton("Hitung Total", 220);

        JButton btnHapus =
                createButton("Hapus Data", 280);
        
          JButton btnRata =
                createButton("Diatas Rata-Rata", 340);


        JButton btnKeluar =
                createButton("Keluar", 400);

      
        panel.add(btnData);
        panel.add(btnUpdate);
        panel.add(btnHitung);
        panel.add(btnHapus);
        panel.add(btnRata);
        panel.add(btnKeluar);
        

        // ===== TABLE =====

        model =
                new DefaultTableModel();

        table =
                new JTable(model);

        table.getTableHeader().setBackground(
                new Color(255, 105, 180));

        table.getTableHeader().setForeground(
                Color.WHITE);

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12));

        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("No HP");
        model.addColumn("Antrian");
        model.addColumn("Layanan");
        model.addColumn("Status");
        model.addColumn("Total");

        JScrollPane sp =
                new JScrollPane(table);

        // DIPINDAH KE BAWAH
        sp.setBounds(50, 460, 500, 130);

        table.setBackground(Color.WHITE);
        sp.getViewport().setBackground(Color.WHITE);

        panel.add(sp);

        add(panel);

        // ===== ACTION =====

        btnData.addActionListener(
                e -> tampilData());

        btnUpdate.addActionListener(
                e -> updateStatus());

        btnHitung.addActionListener(
                e -> hitungTotal());

        btnHapus.addActionListener(
                e -> hapusData());
        
         btnRata.addActionListener(
                e -> tampilDiatasRata());

        btnKeluar.addActionListener(
                e -> System.exit(0));

       
    }

    // STYLE BUTTON

    private JButton createButton(
            String text,
            int y) {

        JButton btn =
                new JButton(text);

        btn.setBounds(150, y, 250, 40);

        btn.setBackground(
                new Color(255, 105, 180));

        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);

        return btn;
    }

    // READ VIEW

    private void tampilData() {

        try {

            model.setRowCount(0);

            String sql =
                    "SELECT * FROM view_laporan";

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

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

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    // UPDATE + TRANSACTION

    private void updateStatus() {

        try {

            int row =
                    table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pilih data dulu!");

                return;

            }

            int id =
                    Integer.parseInt(
                            model.getValueAt(
                                    row,
                                    0).toString());

            String[] pilihan = {
                    "Menunggu",
                    "Diproses",
                    "Selesai"};

            String status =
                    (String)
                            JOptionPane.showInputDialog(
                                    this,
                                    "Pilih Status:",
                                    "Update Status",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    pilihan,
                                    pilihan[0]);

            conn.setAutoCommit(false);

            PreparedStatement ps =
                    conn.prepareStatement(
                            "UPDATE antrian SET status=? WHERE id=?");

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

            conn.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Status berhasil diupdate!");

            tampilData();

        }

        catch (Exception e) {

            try {
                conn.rollback();
            }

            catch(Exception ex){}

            JOptionPane.showMessageDialog(
                    this,
                    "Gagal update!");

        }
    }

    // STORED PROCEDURE

    private void hitungTotal() {

        try {

            int id =
                    Integer.parseInt(
                            JOptionPane.showInputDialog("ID"));

            double berat =
                    Double.parseDouble(
                            JOptionPane.showInputDialog("Berat (kg)"));

            CallableStatement cs =
                    conn.prepareCall(
                            "CALL hitung_total(?, ?)");

            cs.setInt(1, id);
            cs.setDouble(2, berat);

            cs.execute();

            JOptionPane.showMessageDialog(
                    this,
                    "Total berhasil dihitung!");

            tampilData();

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    // DELETE

    private void hapusData() {

        try {

            int row =
                    table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pilih data dulu!");

                return;

            }

            int id =
                    Integer.parseInt(
                            model.getValueAt(
                                    row,
                                    0).toString());

            int konfirmasi =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Yakin mau hapus data?",
                            "Konfirmasi",
                            JOptionPane.YES_NO_OPTION);

            if (konfirmasi ==
                    JOptionPane.YES_OPTION) {

                PreparedStatement ps =
                        conn.prepareStatement(
                                "DELETE FROM antrian WHERE id=?");

                ps.setInt(1, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Data berhasil dihapus!");

                tampilData();

            }

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    // SUBQUERY

    private void tampilDiatasRata() {

        try {

            model.setRowCount(0);

            String sql =
                    "SELECT * FROM antrian " +
                    "WHERE total > " +
                    "(SELECT AVG(total) FROM antrian)";

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        "A" + rs.getInt("nomor_antrian"),
                        "-",
                        "Diatas Rata",
                        rs.getDouble("total")

                });

            }

        }

        catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }

    public static void main(String[] args) {

        new Admin().setVisible(true);

    }

}