import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class User extends JFrame {

    // ENKAPSULASI
private String namaUser;
private String noHpUser;

// GETTER
public String getNamaUser() {
    return namaUser;
}

public String getNoHpUser() {
    return noHpUser;
}

// SETTER
public void setNamaUser(String nama) {
    this.namaUser = nama;
}

public void setNoHpUser(String hp) {
    this.noHpUser = hp;
}
    private JTextField txtNama, txtHP;
    private JComboBox<String> cbLayanan;
    private Connection conn;

    public User() {

        conn = Koneksi.getKoneksi();

        setTitle("Best Laundry - User");
        setSize(500,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // BACKGROUND
        ImageIcon icon = new ImageIcon("src/background.jpg");
        Image img = icon.getImage().getScaledInstance(
                500,550,Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(null);

        // ===== JUDUL =====
        JLabel title =
                new JLabel("USER LAUNDRY");

        title.setBounds(170,20,200,30);
        title.setFont(new Font("Arial",
                Font.BOLD,18));

        title.setForeground(Color.BLACK);
        background.add(title);

        // ===== KOTAK HARGA =====
        JPanel boxHarga = new JPanel();
        boxHarga.setLayout(null);
        boxHarga.setBounds(120,60,260,100);

        boxHarga.setBackground(Color.WHITE);

        boxHarga.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,105,180),2));

        background.add(boxHarga);

        JLabel lblHarga =
                new JLabel("DAFTAR HARGA");

        lblHarga.setBounds(70,5,200,20);

        lblHarga.setFont(new Font(
                "Arial",Font.BOLD,14));

        lblHarga.setForeground(
                new Color(255,105,180));

        boxHarga.add(lblHarga);

        JLabel harga1 =
                new JLabel("Cuci : Rp 5000 / Kg");

        harga1.setBounds(20,35,200,20);

        harga1.setFont(new Font(
                "Arial",Font.BOLD,12));

        boxHarga.add(harga1);

        JLabel harga2 =
                new JLabel(
                        "Cuci + Setrika : Rp 7000 / Kg");

        harga2.setBounds(20,60,220,20);

        harga2.setFont(new Font(
                "Arial",Font.BOLD,12));

        boxHarga.add(harga2);

        // ===== INPUT =====
        background.add(label("Nama",190));
        background.add(label("No HP",240));
        background.add(label("Layanan",290));

        txtNama = new JTextField();
        txtNama.setBounds(200,190,180,30);
        background.add(txtNama);

        txtHP = new JTextField();
        txtHP.setBounds(200,240,180,30);
        background.add(txtHP);

        cbLayanan = new JComboBox<>(
                new String[]{
                        "Cuci",
                        "Cuci + Setrika"
                });

        cbLayanan.setBounds(200,290,180,30);
        background.add(cbLayanan);

        // ===== BUTTON =====
        JButton btnAmbil =
                tombol("Ambil Antrian",350);

        JButton btnCek =
                tombol("Cek Status",400);

        JButton btnKeluar =
                tombol("Keluar",450);

        background.add(btnAmbil);
        background.add(btnCek);
        background.add(btnKeluar);

        add(background);

        // ===== ACTION =====
        btnAmbil.addActionListener(
                e -> simpan());

        btnCek.addActionListener(
                e -> cekStatus());

        btnKeluar.addActionListener(
                e -> System.exit(0));
    }

    private JLabel label(String text,int y){

        JLabel l = new JLabel(text);

        l.setBounds(100,y,100,30);

        l.setForeground(Color.BLACK);

        l.setFont(new Font(
                "Arial",Font.BOLD,12));

        return l;
    }

    private JButton tombol(String text,int y){

        JButton b = new JButton(text);

        b.setBounds(150,y,200,40);

        b.setBackground(
                new Color(255,105,180));

        b.setForeground(Color.WHITE);

        return b;
    }

    // ===== SIMPAN =====
    private void simpan(){

        try{

            String nama =
                    txtNama.getText();

            String hp =
                    txtHP.getText();

            if(!nama.matches("[a-zA-Z ]+")){
                JOptionPane.showMessageDialog(
                        this,
                        "Nama hanya huruf!");
                return;
            }

            if(!hp.matches("\\d{12}")){
                JOptionPane.showMessageDialog(
                        this,
                        "No HP harus 12 digit!");
                return;
            }

            int idLayanan =
                    cbLayanan.getSelectedIndex()+1;

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT MAX(nomor_antrian) FROM antrian");

            int nomor = 1;

            if(rs.next() && rs.getInt(1)!=0){

                nomor = rs.getInt(1)+1;

            }

            PreparedStatement ps =
                    conn.prepareStatement(
                            "INSERT INTO antrian(nama,no_hp,nomor_antrian,id_layanan,status,total) VALUES(?,?,?,?,?,?)");

            ps.setString(1,nama);
            ps.setString(2,hp);
            ps.setInt(3,nomor);
            ps.setInt(4,idLayanan);
            ps.setString(5,"Menunggu");
            ps.setDouble(6,0);

            ps.executeUpdate();

            new HasilAntrian(
                    nama,nomor).setVisible(true);

            dispose();

        }

        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }

    // ===== CEK STATUS =====
    private void cekStatus(){

        try{

            String hp =
                    txtHP.getText();

            PreparedStatement ps =
                    conn.prepareStatement(
                            "SELECT nomor_antrian,status,total FROM antrian WHERE no_hp=? ORDER BY id DESC LIMIT 1");

            ps.setString(1,hp);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                new HasilStatus(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                ).setVisible(true);

                dispose();

            }

            else{

                JOptionPane.showMessageDialog(
                        this,
                        "Data tidak ditemukan!");

            }

        }

        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }

    public static void main(String[] args) {

        new User().setVisible(true);

    }
}