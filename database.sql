-- SQL Script to set up the Library Database (Sistem Perpustakaan)
CREATE DATABASE IF NOT EXISTS perpustakaan;
USE perpustakaan;

-- Tabel anggota: menyimpan data anggota perpustakaan
CREATE TABLE IF NOT EXISTS anggota (
    id             INT(11)      AUTO_INCREMENT PRIMARY KEY,
    nama           VARCHAR(100) NOT NULL,
    nomor_anggota  VARCHAR(20)  NOT NULL,
    alamat         VARCHAR(200),
    telepon        VARCHAR(20)
);

-- Tabel buku: menyimpan data koleksi buku perpustakaan
CREATE TABLE IF NOT EXISTS buku (
    id           INT(11)      AUTO_INCREMENT PRIMARY KEY,
    judul        VARCHAR(200) NOT NULL,
    pengarang    VARCHAR(100) NOT NULL,
    penerbit     VARCHAR(100),
    tahun_terbit INT(4),
    stok         INT(11)      NOT NULL DEFAULT 0
);

-- Data sampel untuk pengujian langsung
INSERT INTO anggota (nama, nomor_anggota, alamat, telepon) VALUES
('Budi Santoso', 'A001', 'Jl. Merdeka No. 10, Jakarta', '081234567890');

INSERT INTO buku (judul, pengarang, penerbit, tahun_terbit, stok) VALUES
('Laskar Pelangi', 'Andrea Hirata', 'Bentang Pustaka', 2005, 3);
