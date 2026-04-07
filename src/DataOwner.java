import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class DataOwner extends JFrame {

    private JTable table;
    private Connection conn;

    public DataOwner() {

        conn = Koneksi.getKoneksi();

        setTitle("Data Laundry");
        setSize(600,400);
        setLocationRelativeTo(null);

        ImageIcon icon =
                new ImageIcon("src/background.jpg");

        Image img =
                icon.getImage().getScaledInstance(
                        600,400,
                        Image.SCALE_SMOOTH);

        JLabel background =
                new JLabel(new ImageIcon(img));

        background.setLayout(new BorderLayout());

        String[] kolom = {

                "ID",
                "Nama",
                "No HP",
                "Antrian",
                "Status",
                "Total"

        };

        DefaultTableModel model =
                new DefaultTableModel(kolom,0);

        table =
                new JTable(model);

        table.setBackground(Color.WHITE);

        JScrollPane scroll =
                new JScrollPane(table);

        background.add(scroll,
                BorderLayout.CENTER);

        tampilData(model);

        JButton kembali =
                new JButton("Kembali");

        kembali.setBackground(
                new Color(255,105,180));

        kembali.setForeground(Color.WHITE);

        background.add(kembali,
                BorderLayout.SOUTH);

        kembali.addActionListener(e -> {

            new Owner().setVisible(true);

            dispose();

        });

        add(background);
    }

    private void tampilData(DefaultTableModel model){

        try{

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM view_laporan");

            while(rs.next()){

                model.addRow(new Object[]{

                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        rs.getInt("nomor_antrian"),
                        rs.getString("status"),
                        rs.getDouble("total")

                });

            }

        }

        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }

    }
}