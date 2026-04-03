import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class User extends JFrame {

    private JTextField txtNama, txtHP;
    private JComboBox<String> cbLayanan;
    private Connection conn;

    public User() {

        conn = Koneksi.getKoneksi();

        setTitle("User Laundry");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        // ===== TITLE =====
        JLabel title = new JLabel("User Laundry");
        title.setBounds(180, 20, 200, 30);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // ===== LABEL =====
        JLabel lNama = label("Nama:", 100);
        JLabel lHP = label("No HP:", 150);
        JLabel lLayanan = label("Layanan:", 200);

        panel.add(lNama);
        panel.add(lHP);
        panel.add(lLayanan);

        // ===== INPUT =====
        txtNama = new JTextField();
        txtNama.setBounds(200, 100, 200, 30);
        panel.add(txtNama);

        txtHP = new JTextField();
        txtHP.setBounds(200, 150, 200, 30);
        panel.add(txtHP);

        cbLayanan = new JComboBox<>(new String[]{"Cuci", "Cuci + Setrika"});
        cbLayanan.setBounds(200, 200, 200, 30);
        panel.add(cbLayanan);

        // ===== BUTTON =====
        JButton btnSimpan = btn("Simpan", 260);
        JButton btnCek = btn("Cek Status", 310);
        JButton btnKeluar = btn("Keluar", 360);

        panel.add(btnSimpan);
        panel.add(btnCek);
        panel.add(btnKeluar);

        add(panel);

        // ===== ACTION =====
        btnSimpan.addActionListener(e -> simpan());
        btnCek.addActionListener(e -> cekStatus());
        btnKeluar.addActionListener(e -> keluar());
    }

    private JLabel label(String text, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(100, y, 100, 30);
        l.setForeground(Color.WHITE);
        return l;
    }

    private JButton btn(String text, int y) {
        JButton b = new JButton(text);
        b.setBounds(150, y, 200, 40);
        b.setBackground(new Color(0,150,255));
        b.setForeground(Color.WHITE);
        return b;
    }

    // VALIDASI + INSERT
    private void simpan() {
        try {
            String nama = txtNama.getText();
            String hp = txtHP.getText();
            String layanan = cbLayanan.getSelectedItem().toString();

            // VALIDASI NAMA
            if (!nama.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Nama hanya huruf!");
                return;
            }

            // VALIDASI HP (12 ANGKA)
            if (!hp.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(this, "No HP harus 12 digit angka!");
                return;
            }

            // AMBIL ID LAYANAN
            int idLayanan = layanan.equals("Cuci") ? 1 : 2;

            // NOMOR ANTRIAN OTOMATIS
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(nomor_antrian) FROM antrian");

            int nomor = 1;
            if (rs.next() && rs.getInt(1) != 0) {
                nomor = rs.getInt(1) + 1;
            }

            // INSERT
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO antrian(nama, no_hp, nomor_antrian, id_layanan, status, total) VALUES(?,?,?,?,?,?)"
            );

            ps.setString(1, nama);
            ps.setString(2, hp);
            ps.setInt(3, nomor);
            ps.setInt(4, idLayanan);
            ps.setString(5, "Menunggu");
            ps.setDouble(6, 0);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Berhasil!\nNomor Antrian: A" + nomor);

            txtNama.setText("");
            txtHP.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // CEK STATUS + TOTAL
    private void cekStatus() {
        try {
            String hp = txtHP.getText();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT nomor_antrian, status, total FROM antrian WHERE no_hp=? ORDER BY id DESC LIMIT 1"
            );

            ps.setString(1, hp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                        "No Antrian: A" + rs.getInt(1) +
                        "\nStatus: " + rs.getString(2) +
                        "\nTotal: Rp " + rs.getDouble(3)
                );
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
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

    public static void main(String[] args) {
        new User().setVisible(true);
    }
}