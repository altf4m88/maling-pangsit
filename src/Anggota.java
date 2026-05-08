// DOKUMENTASI: Kelas model untuk merepresentasikan satu baris dari tabel anggota
public class Anggota {

    private int    id;
    private String nama;
    private String nomorAnggota;
    private String alamat;
    private String telepon;

    // DOKUMENTASI: Constructor untuk membuat objek Anggota dari data database
    public Anggota(int id, String nama, String nomorAnggota, String alamat, String telepon) {
        this.id           = id;
        this.nama         = nama;
        this.nomorAnggota = nomorAnggota;
        this.alamat       = alamat;
        this.telepon      = telepon;
    }

    // DOKUMENTASI: Getter methods — cara mengambil nilai field
    public int    getId()           { return id; }
    public String getNama()         { return nama; }
    public String getNomorAnggota() { return nomorAnggota; }
    public String getAlamat()       { return alamat; }
    public String getTelepon()      { return telepon; }
}
