
Aplikasi desktop CRUD untuk mengelola data **Anggota** dan **Buku** perpustakaan. Dibangun dengan **Java Swing** (UI) dan **JDBC** (koneksi database), tanpa framework tambahan.

---

## Prasyarat

| Kebutuhan | Keterangan |
|-----------|------------|
| JDK 11+ | Sudah terinstall |
| XAMPP | MySQL harus berjalan (Apache tidak perlu) |
| VS Code | Dengan **Extension Pack for Java** (publisher: Microsoft) <img width="1090" height="290" alt="Screenshot 2026-05-08 at 16 56 03" src="https://github.com/user-attachments/assets/4bd1c8c1-a9e8-481d-905c-206654fa3ea8" />
 |
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

<img width="1470" height="728" alt="Screenshot 2026-05-08 at 16 52 58" src="https://github.com/user-attachments/assets/26b13b5d-c5c9-4888-897c-d75dcb9615e6" />



## Langkah 2 — Download MySQL Connector/J (JAR)

JDBC membutuhkan file `.jar` untuk bisa terhubung ke MySQL.

1. Buka: https://github.com/altf4m88/maling-pangsit/blob/main/lib/mysql-connector-j.jar
2. Download file jar tersebut
   <img width="1152" height="283" alt="Screenshot 2026-05-08 at 16 54 28" src="https://github.com/user-attachments/assets/54ddc509-4948-4a69-8e44-516276319937" />

3. **Salin file `.jar` tersebut ke folder `lib/`** di dalam project ini.

Struktur setelah selesai:
```
inventory-java/
└── lib/
    └── mysql-connector-j-9.x.x.jar   ← file yang baru disalin
```

---
<img width="292" height="120" alt="Screenshot 2026-05-08 at 16 55 25" src="https://github.com/user-attachments/assets/3f9d7fd8-d73f-4391-b25c-fecf731718ed" />


## Langkah 3 — Buka Project di VS Code

1. Buka VS Code.
2. **File → Open Folder** → pilih folder `inventory-java/`.
3. VS Code akan otomatis mendeteksi source (`src/`) dan library (`lib/*.jar`) dari file `.vscode/settings.json`.

Jika muncul notifikasi "Java projects detected", klik **Yes**.
<img width="273" height="338" alt="Screenshot 2026-05-08 at 16 56 56" src="https://github.com/user-attachments/assets/63ff1a40-4809-4776-96d1-c7063c1b33e1" />


---

## Langkah 4 — Jalankan Aplikasi

Buka file [src/MainApp.java](src/MainApp.java), lalu klik tombol **▷ Run** yang muncul di atas method `main()`.
<img width="838" height="589" alt="Screenshot 2026-05-08 at 16 59 07" src="https://github.com/user-attachments/assets/c38fb91b-35a6-45d6-8824-fd51435c3e1d" />

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

<img width="876" height="522" alt="Screenshot 2026-05-08 at 16 59 44" src="https://github.com/user-attachments/assets/7b69846e-e162-415c-ad33-16f9f0f67ea6" />

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

## Cara Membaca Kode (untuk Pemula)

Jika kamu baru pertama kali melihat proyek ini, baca file-file berikut **secara berurutan**. Setiap file lebih mudah dipahami setelah membaca file sebelumnya.

### 1. `DatabaseConnection.java` — Mulai di sini
File terpendek dan tersederhana. Hanya berisi satu method `getConnection()` yang menghubungkan aplikasi ke MySQL. Ini adalah padanan langsung dari `config.php`.

### 2. `Anggota.java` dan `Buku.java` — Pahami bentuk datanya
Di PHP, satu baris hasil query disimpan sebagai `$row['nama']`. Di Java, kita butuh kelas khusus untuk menampungnya. Kedua file ini hanya berisi field dan getter — tidak ada logika sama sekali.

### 3. `BukuDAO.java` — Lihat cara SQL dijalankan
Baca ini sebelum `AnggotaDAO.java` karena kolom tabel `buku` lebih familiar (judul, pengarang, dll). Di sini kamu akan melihat bagaimana `PreparedStatement` menggantikan `mysqli_query()`. Perhatikan pola yang sama diulang di keempat method: buka koneksi → siapkan query → isi nilai → eksekusi → tutup.

### 4. `AddBukuDialog.java` — Lihat cara form bekerja
Setara dengan `create.php`. Di PHP, form dikirim via `POST` lalu PHP menangkapnya. Di Java, user mengisi `JTextField` lalu menekan tombol — tombol itu punya `ActionListener` yang menjalankan logika yang sama. Bandingkan blok `btnSimpan.addActionListener(...)` di sini dengan blok `if(isset($_POST['submit']))` di `create.php`.

### 5. `EditBukuDialog.java` — Lihat cara form pre-filled bekerja
Setara dengan `edit.php`. Perhatikan bagaimana constructor menerima objek `Buku` dan langsung menggunakannya untuk mengisi nilai awal setiap field (`new JTextField(buku.getJudul())`). Bandingkan dengan `value="<?php echo $row['judul']; ?>"` di HTML.

### 6. `MainFrame.java` — Lihat semuanya tersambung
File terbesar. Di sini semua komponen digabungkan: `JTabbedPane` menampung dua panel, setiap panel punya tabel dan tombol, setiap tombol membuka dialog yang sudah kamu pelajari di langkah sebelumnya. Baca method `createBukuPanel()` terlebih dahulu, lalu `createAnggotaPanel()` karena polanya identik.

### 7. `MainApp.java` — Titik masuk
File terakhir dan tersederhana kedua. Hanya memanggil `new MainFrame()`. Baca ini terakhir agar kamu sudah paham apa yang dibuat oleh `MainFrame`.

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
