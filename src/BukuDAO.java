import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DOKUMENTASI: DAO untuk semua operasi SQL ke tabel buku
public class BukuDAO {

    // DOKUMENTASI: Mengambil semua buku dari database — SELECT * FROM buku
    public List<Buku> getAllBuku() throws SQLException {
        List<Buku> list = new ArrayList<>();
        String query = "SELECT * FROM buku";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)) {

            // DOKUMENTASI: Iterasi hasil — setara while($row = mysqli_fetch_assoc($result))
            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("penerbit"),
                    rs.getInt("tahun_terbit"),
                    rs.getInt("stok")
                ));
            }
        }
        return list;
    }

    // DOKUMENTASI: Menyimpan buku baru — INSERT INTO buku
    public void createBuku(String judul, String pengarang, String penerbit, int tahunTerbit, int stok) throws SQLException {
        String query = "INSERT INTO buku (judul, pengarang, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, judul);
            ps.setString(2, pengarang);
            ps.setString(3, penerbit);
            ps.setInt(4, tahunTerbit);
            ps.setInt(5, stok);
            ps.executeUpdate();
        }
    }

    // DOKUMENTASI: Memperbarui data buku — UPDATE buku SET ... WHERE id=?
    public void updateBuku(int id, String judul, String pengarang, String penerbit, int tahunTerbit, int stok) throws SQLException {
        String query = "UPDATE buku SET judul=?, pengarang=?, penerbit=?, tahun_terbit=?, stok=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, judul);
            ps.setString(2, pengarang);
            ps.setString(3, penerbit);
            ps.setInt(4, tahunTerbit);
            ps.setInt(5, stok);
            ps.setInt(6, id);
            ps.executeUpdate();
        }
    }

    // DOKUMENTASI: Menghapus buku berdasarkan ID — DELETE FROM buku WHERE id=?
    public void deleteBuku(int id) throws SQLException {
        String query = "DELETE FROM buku WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
