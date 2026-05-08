# Perpustakaan Java — Aplikasi Desktop Manajemen Perpustakaan

Aplikasi desktop CRUD untuk mengelola data **Anggota** dan **Buku** perpustakaan. Dibangun dengan **Java Swing** (UI) dan **JDBC** (koneksi database), tanpa framework tambahan.

---

## Prasyarat

| Kebutuhan | Keterangan |
|-----------|------------|
| JDK 11+ | Sudah terinstall |
| XAMPP | MySQL harus berjalan (Apache tidak perlu) |
| VS Code | Dengan **Extension Pack for Java** (publisher: Microsoft) |
| MySQL Connector/J | Lihat langkah di bawah |

---

## Langkah 1 — Setup Database

1. Buka XAMPP Control Panel → klik **Start** pada MySQL.
2. Buka `http://localhost/phpmyadmin` di browser.
3. Klik tab **SQL**, tempel isi file `../database.sql`, lalu klik **Go**.

Ini akan membuat database `perpustakaan` dengan dua tabel:

| Tabel | Kolom |
|-------|-------|
| `anggota` | id, nama, nomor_anggota, alamat, telepon |
| `buku` | id, judul, pengarang, penerbit, tahun_terbit, stok |

---

## Langkah 2 — Download MySQL Connector/J (JAR)

JDBC membutuhkan file `.jar` untuk bisa terhubung ke MySQL.

1. Buka: https://dev.mysql.com/downloads/connector/j/
2. Pilih **Platform Independent** → klik **Download** (pilih ZIP).
3. Ekstrak ZIP tersebut.
4. Cari file bernama `mysql-connector-j-x.x.x.jar`.
5. **Salin file `.jar` tersebut ke folder `lib/`** di dalam project ini.

Struktur setelah selesai:
```
inventory-java/
└── lib/
    └── mysql-connector-j-9.x.x.jar   ← file yang baru disalin
```

---

## Langkah 3 — Buka Project di VS Code

1. Buka VS Code.
2. **File → Open Folder** → pilih folder `inventory-java/`.
3. VS Code akan otomatis mendeteksi source (`src/`) dan library (`lib/*.jar`) dari file `.vscode/settings.json`.

Jika muncul notifikasi "Java projects detected", klik **Yes**.

---

## Langkah 4 — Jalankan Aplikasi

Buka file [src/MainApp.java](src/MainApp.java), lalu klik tombol **▷ Run** yang muncul di atas method `main()`.

**Atau lewat terminal** (dari folder `inventory-java/`):

```bash
# macOS / Linux
javac -cp "lib/*" -d out src/*.java
java -cp "out:lib/*" MainApp

# Windows
javac -cp "lib/*" -d out src/*.java
java -cp "out;lib/*" MainApp
```

Jendela aplikasi akan terbuka dengan **dua tab**: Data Anggota dan Data Buku.

---

## Struktur File & Padanannya di PHP

| Java | PHP | Fungsi |
|------|-----|--------|
| `DatabaseConnection.java` | `config.php` | Koneksi ke MySQL (`perpustakaan`) |
| `Anggota.java` | *(tidak ada)* | Model data satu baris tabel anggota |
| `Buku.java` | *(tidak ada)* | Model data satu baris tabel buku |
| `AnggotaDAO.java` | Semua `mysqli_query()` di file anggota | SELECT, INSERT, UPDATE, DELETE untuk anggota |
| `BukuDAO.java` | `index/create/edit/delete.php` | SELECT, INSERT, UPDATE, DELETE untuk buku |
| `MainFrame.java` | `index.php` | Dashboard dengan JTabbedPane (2 tab) |
| `AddAnggotaDialog.java` | `create.php` (versi anggota) | Form tambah anggota |
| `EditAnggotaDialog.java` | `edit.php` (versi anggota) | Form edit anggota (pre-filled) |
| `AddBukuDialog.java` | `create.php` | Form tambah buku |
| `EditBukuDialog.java` | `edit.php` | Form edit buku (pre-filled) |
| `MainApp.java` | *(titik masuk)* | Menjalankan aplikasi |

---

## Troubleshooting

**Error: `Communications link failure`**
→ MySQL di XAMPP belum berjalan. Buka XAMPP dan klik Start pada MySQL.

**Error: `ClassNotFoundException: com.mysql.cj.jdbc.Driver`**
→ File `.jar` belum ada di folder `lib/`. Ulangi Langkah 2.

**Error: `Unknown database 'perpustakaan'`**
→ Database belum dibuat. Ulangi Langkah 1.

**Tombol ▷ Run tidak muncul di VS Code**
→ Extension Pack for Java belum terinstall. Buka Extensions (`Ctrl+Shift+X`), cari "Extension Pack for Java" oleh Microsoft, lalu install.
