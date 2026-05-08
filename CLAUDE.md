# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Java Swing desktop application for managing a library (perpustakaan) — CRUD operations for two entities: **Anggota** (members) and **Buku** (books). Uses raw JDBC against a MySQL database; no build system (Maven/Gradle), no test framework.

## Prerequisites

- JDK 11+
- MySQL running via XAMPP (port 3306, user `root`, empty password)
- `lib/mysql-connector-j.jar` present (MySQL Connector/J)
- Database `perpustakaan` created from `database.sql`

## Build & Run

```bash
# Compile all sources (macOS/Linux)
javac -cp "lib/*" -d out src/*.java

# Run
java -cp "out:lib/*" MainApp

# Windows
javac -cp "lib/*" -d out src/*.java
java -cp "out;lib/*" MainApp
```

In VS Code: open [src/MainApp.java](src/MainApp.java) and click the **▷ Run** button above `main()`. The `.vscode/settings.json` already configures `src/` as the source path, `out/` as output, and `lib/**/*.jar` as referenced libraries.

There are no automated tests.

## Architecture

The app follows a simple three-layer pattern, all in the default package:

**Models** — plain Java objects mirroring DB rows:
- [src/Anggota.java](src/Anggota.java) — maps `anggota` table (id, nama, nomor_anggota, alamat, telepon)
- [src/Buku.java](src/Buku.java) — maps `buku` table (id, judul, pengarang, penerbit, tahun_terbit, stok)

**DAOs** — all SQL lives here; each method opens and closes its own `Connection` via `DatabaseConnection.getConnection()`:
- [src/AnggotaDAO.java](src/AnggotaDAO.java) — SELECT/INSERT/UPDATE/DELETE for `anggota`
- [src/BukuDAO.java](src/BukuDAO.java) — SELECT/INSERT/UPDATE/DELETE for `buku`

**UI** — Java Swing, no layout managers beyond `BorderLayout` and `FlowLayout`:
- [src/MainApp.java](src/MainApp.java) — entry point; launches `MainFrame` on the EDT via `SwingUtilities.invokeLater`
- [src/MainFrame.java](src/MainFrame.java) — `JFrame` with a `JTabbedPane` (two tabs). Holds `DefaultTableModel` instances that are refreshed after every write operation by calling `refreshAnggota()` / `refreshBuku()`
- [src/AddAnggotaDialog.java](src/AddAnggotaDialog.java), [src/EditAnggotaDialog.java](src/EditAnggotaDialog.java) — modal dialogs for member create/update
- [src/AddBukuDialog.java](src/AddBukuDialog.java), [src/EditBukuDialog.java](src/EditBukuDialog.java) — modal dialogs for book create/update

**DB config** — [src/DatabaseConnection.java](src/DatabaseConnection.java) — hardcoded `localhost:3306/perpustakaan`, `root`, empty password. Change constants here to point to a different MySQL instance.

## Data Flow

1. `MainFrame` instantiates one `AnggotaDAO` and one `BukuDAO`.
2. Button clicks open the appropriate `Add*Dialog` or `Edit*Dialog`, passing the DAO.
3. The dialog executes the SQL (INSERT or UPDATE) and closes.
4. `MainFrame` calls `refresh*()` which re-queries the DB and repopulates the `DefaultTableModel`.
5. Delete is handled inline in `MainFrame` without a dialog.

## Common Errors

| Error | Fix |
|-------|-----|
| `Communications link failure` | Start MySQL in XAMPP |
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Add the Connector/J JAR to `lib/` |
| `Unknown database 'perpustakaan'` | Run `database.sql` in phpMyAdmin |
