import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class User extends JFrame {

    private Connection conn;
    private JTextField txtNama, txtHP;
    private JComboBox<String> cbLayanan;
    private JLabel lblAntrian;

    public User() {
        conn = Koneksi.getKoneksi();

        setTitle("Best Laundry - User");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(500, 650, Image.SCALE_SMOOTH);
        JLabel panel = new JLabel(new ImageIcon(img));
        panel.setLayout(null);

        // TITLE
        JLabel title = new JLabel("Ambil Antrian");
        title.setBounds(150, 40, 300, 40);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // INPUT
        panel.add(label("Nama:", 120));
        txtNama = input(200,120);
        panel.add(txtNama);
        
        panel.add(label("No HP:", 170));
        txtHP = input(200,170);
        panel.add(txtHP);

        panel.add(label("Layanan:", 220));
        cbLayanan = new JComboBox<>(new String[]{
            "Cuci",
            "Cuci + Setrika"
        });
        cbLayanan.setBounds(200,220,180,30);
        panel.add(cbLayanan);

        // BUTTON AMBIL
        JButton btnAmbil = button("Ambil Antrian",280);
        panel.add(btnAmbil);

        // OUTPUT ANTRIAN
        lblAntrian = new JLabel("Nomor Antrian: -");
        lblAntrian.setBounds(140, 340, 250, 40);
        lblAntrian.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAntrian.setForeground(Color.YELLOW);
        panel.add(lblAntrian);

        // BUTTON CEK STATUS
        JButton btnCek = button("Cek Status & Total",400);
        panel.add(btnCek);

        add(panel);

        // ACTION
        btnAmbil.addActionListener(e -> ambilAntrian());
        btnCek.addActionListener(e -> cekStatus());
    }

    // COMPONENT
    private JLabel label(String text, int y){
        JLabel l = new JLabel(text);
        l.setBounds(100,y,100,30);
        l.setForeground(Color.WHITE);
        return l;
    }

    private JTextField input(int x, int y){
        JTextField t = new JTextField();
        t.setBounds(x,y,180,30);
        return t;
    }

    private JButton button(String text, int y){
        JButton btn = new JButton(text);
        btn.setBounds(140,y,220,40);
        btn.setBackground(new Color(255,105,180));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    // VALIDASI
    private boolean validasiInput(String nama, String hp){

        // Nama hanya huruf
        if(!nama.matches("[a-zA-Z ]+")){
            JOptionPane.showMessageDialog(this, "Nama hanya boleh huruf!");
            return false;
        }

        // HP harus 12 angka
        if(!hp.matches("\\d{12}")){
            JOptionPane.showMessageDialog(this, "No HP harus 12 angka dan tidak boleh huruf!");
            return false;
        }

        return true;
    }

    // AMBIL ANTRIAN
    private void ambilAntrian() {
        try {
            String nama = txtNama.getText();
            String hp = txtHP.getText();
            int idLayanan = cbLayanan.getSelectedIndex() + 1;

            if(!validasiInput(nama, hp)) return;

            // ambil nomor terakhir
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(nomor_antrian) FROM antrian");

            int nomor = 1;
            if (rs.next() && rs.getInt(1) != 0) {
                nomor = rs.getInt(1) + 1;
            }

            // insert
            PreparedStatement ps = conn.prepareStatement("INSERT INTO antrian(nama, no_hp, nomor_antrian, id_layanan, status, total) VALUES(?,?,?,?,?,?)");

            ps.setString(1, nama);
            ps.setString(2, hp); 
            ps.setInt(3, nomor);
            ps.setInt(4, idLayanan);
            ps.setString(5, "Menunggu");
            ps.setDouble(6, 0);

            ps.executeUpdate();

            lblAntrian.setText("Nomor Antrian: A" + nomor);

            JOptionPane.showMessageDialog(this, "Terima kasih atas kepercayaan anda");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // CEK STATUS + TOTAL
    private void cekStatus(){
        try{
            String nama = txtNama.getText();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT status, total FROM antrian WHERE nama=? ORDER BY id DESC LIMIT 1"
            );
            ps.setString(1, nama);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String status = rs.getString("status");
                double total = rs.getDouble("total");

                JOptionPane.showMessageDialog(this,
                    "Status: " + status + "\nTotal: Rp " + total
                );
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new User().setVisible(true);
    }
}