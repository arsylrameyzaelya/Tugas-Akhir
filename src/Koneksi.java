import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection conn;

    public static Connection getKoneksi() {
        try {
            if (conn == null) {
                String url = "jdbc:mysql://localhost:3306/tugas_akhir";
                String user = "root";
                String pass = "";

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);

                System.out.println("Koneksi berhasil");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver tidak ditemukan!");
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}
