import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DOKUMENTASI: Kelas ini adalah "Reusable Subroutine" — setara dengan config.php
// Menyediakan koneksi ke database MySQL lokal menggunakan JDBC
public class DatabaseConnection {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB   = "perpustakaan";
    private static final String USER = "root";
    private static final String PASS = "";

    // DOKUMENTASI: Mengembalikan objek Connection — setara dengan variabel $conn di PHP
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB
                   + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, USER, PASS);
    }
}
