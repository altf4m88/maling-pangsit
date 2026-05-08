import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DOKUMENTASI: DAO untuk semua operasi SQL ke tabel anggota
public class AnggotaDAO {

    // DOKUMENTASI: Mengambil semua anggota dari database — SELECT * FROM anggota
    public List<Anggota> getAllAnggota() throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String query = "SELECT * FROM anggota";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)) {

            // DOKUMENTASI: Iterasi hasil — setara while($row = mysqli_fetch_assoc($result))
            while (rs.next()) {
                list.add(new Anggota(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nomor_anggota"),
                    rs.getString("alamat"),
                    rs.getString("telepon")
                ));
            }
        }
        return list;
    }

    // DOKUMENTASI: Menyimpan anggota baru — INSERT INTO anggota
    public void createAnggota(String nama, String nomorAnggota, String alamat, String telepon) throws SQLException {
        String query = "INSERT INTO anggota (nama, nomor_anggota, alamat, telepon) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nama);
            ps.setString(2, nomorAnggota);
            ps.setString(3, alamat);
            ps.setString(4, telepon);
            ps.executeUpdate();
        }
    }

    // DOKUMENTASI: Memperbarui data anggota — UPDATE anggota SET ... WHERE id=?
    public void updateAnggota(int id, String nama, String nomorAnggota, String alamat, String telepon) throws SQLException {
        String query = "UPDATE anggota SET nama=?, nomor_anggota=?, alamat=?, telepon=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nama);
            ps.setString(2, nomorAnggota);
            ps.setString(3, alamat);
            ps.setString(4, telepon);
            ps.setInt(5, id);
            ps.executeUpdate();
        }
    }

    // DOKUMENTASI: Menghapus anggota berdasarkan ID — DELETE FROM anggota WHERE id=?
    public void deleteAnggota(int id) throws SQLException {
        String query = "DELETE FROM anggota WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
