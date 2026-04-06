import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class User extends JFrame {

    private JTextField txtNama, txtHP;
    private JComboBox<String> cbLayanan;
    private Connection conn;

    public User() {

        conn = Koneksi.getKoneksi();

        setTitle("Best Laundry - User");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        JLabel title = new JLabel("USER LAUNDRY");
        title.setBounds(170, 40, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // LABEL
        panel.add(label("Nama", 120));
        panel.add(label("No HP", 170));
        panel.add(label("Layanan", 220));

        // INPUT
        txtNama = new JTextField();
        txtNama.setBounds(200, 120, 180, 30);
        panel.add(txtNama);

        txtHP = new JTextField();
        txtHP.setBounds(200, 170, 180, 30);
        panel.add(txtHP);

        cbLayanan = new JComboBox<>(new String[]{
                "Cuci",
                "Cuci + Setrika"
        });

        cbLayanan.setBounds(200, 220, 180, 30);
        panel.add(cbLayanan);

        // BUTTON
        JButton btnAmbil = tombol("Ambil Antrian", 290);
        JButton btnCek = tombol("Cek Status", 340);
        JButton btnKeluar = tombol("Keluar", 390);

        panel.add(btnAmbil);
        panel.add(btnCek);
        panel.add(btnKeluar);

        add(panel);

        // ACTION
        btnAmbil.addActionListener(e -> simpan());
        btnCek.addActionListener(e -> cekStatus());
        btnKeluar.addActionListener(e -> System.exit(0));
    }

    private JLabel label(String text, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(100, y, 100, 30);
        l.setForeground(Color.BLACK);
        return l;
    }

    private JButton tombol(String text, int y) {
        JButton b = new JButton(text);
        b.setBounds(150, y, 200, 40);
        b.setBackground(new Color(255,105,180));
        b.setForeground(Color.WHITE);
        return b;
    }

    // SIMPAN DATA → PINDAH HALAMAN
    private void simpan() {

        try {

            String nama = txtNama.getText();
            String hp = txtHP.getText();

            if (!nama.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this,"Nama hanya huruf!");
                return;
            }

            if (!hp.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(this,"No HP harus 12 digit!");
                return;
            }

            int idLayanan = cbLayanan.getSelectedIndex()+1;

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT MAX(nomor_antrian) FROM antrian"
            );

            int nomor = 1;

            if(rs.next() && rs.getInt(1)!=0){
                nomor = rs.getInt(1)+1;
            }

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO antrian(nama,no_hp,nomor_antrian,id_layanan,status,total) VALUES(?,?,?,?,?,?)"
            );

            ps.setString(1,nama);
            ps.setString(2,hp);
            ps.setInt(3,nomor);
            ps.setInt(4,idLayanan);
            ps.setString(5,"Menunggu");
            ps.setDouble(6,0);

            ps.executeUpdate();

            new HasilAntrian(nama, nomor).setVisible(true);
            dispose();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(this,e.getMessage());

        }
    }

    // CEK STATUS → PINDAH HALAMAN
    private void cekStatus() {

        try {

            String hp = txtHP.getText();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT nomor_antrian,status,total FROM antrian WHERE no_hp=? ORDER BY id DESC LIMIT 1"
            );

            ps.setString(1,hp);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                new HasilStatus(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                ).setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,"Data tidak ditemukan!");

            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(this,e.getMessage());

        }
    }

    public static void main(String[] args) {
        new User().setVisible(true);
    }
}