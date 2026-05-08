// DOKUMENTASI: Kelas model untuk merepresentasikan satu baris dari tabel buku
public class Buku {

    private int    id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private int    tahunTerbit;
    private int    stok;

    // DOKUMENTASI: Constructor untuk membuat objek Buku dari data database
    public Buku(int id, String judul, String pengarang, String penerbit, int tahunTerbit, int stok) {
        this.id          = id;
        this.judul       = judul;
        this.pengarang   = pengarang;
        this.penerbit    = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.stok        = stok;
    }

    // DOKUMENTASI: Getter methods — cara mengambil nilai field
    public int    getId()          { return id; }
    public String getJudul()       { return judul; }
    public String getPengarang()   { return pengarang; }
    public String getPenerbit()    { return penerbit; }
    public int    getTahunTerbit() { return tahunTerbit; }
    public int    getStok()        { return stok; }
}
