import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// DOKUMENTASI: Jendela utama aplikasi dengan dua tab — Anggota dan Buku
// Menggunakan JTabbedPane untuk mengelola dua tabel sekaligus dalam satu jendela
public class MainFrame extends JFrame {

    // DOKUMENTASI: Komponen dan DAO untuk tab Anggota
    private final DefaultTableModel anggotaModel;
    private final JTable            anggotaTable;
    private final AnggotaDAO        anggotaDAO = new AnggotaDAO();

    // DOKUMENTASI: Komponen dan DAO untuk tab Buku
    private final DefaultTableModel bukuModel;
    private final JTable            bukuTable;
    private final BukuDAO           bukuDAO = new BukuDAO();

    public MainFrame() {
        setTitle("Sistem Manajemen Perpustakaan");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // DOKUMENTASI: Mendefinisikan kolom tabel Anggota
        anggotaModel = new DefaultTableModel(
            new String[]{"ID", "Nama", "Nomor Anggota", "Alamat", "Telepon"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        anggotaTable = new JTable(anggotaModel);
        anggotaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // DOKUMENTASI: Mendefinisikan kolom tabel Buku
        bukuModel = new DefaultTableModel(
            new String[]{"ID", "Judul", "Pengarang", "Penerbit", "Tahun Terbit", "Stok"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        bukuTable = new JTable(bukuModel);
        bukuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // DOKUMENTASI: JTabbedPane menampung dua panel — satu untuk setiap tabel database
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Data Anggota", createAnggotaPanel());
        tabs.addTab("Data Buku",    createBukuPanel());

        add(tabs);

        // DOKUMENTASI: Memuat data awal saat aplikasi dibuka
        refreshAnggota();
        refreshBuku();
    }

    // DOKUMENTASI: Membangun panel tab Anggota beserta tombol CRUD-nya
    private JPanel createAnggotaPanel() {
        JButton btnTambah = new JButton("Tambah Anggota");
        JButton btnEdit   = new JButton("Edit");
        JButton btnHapus  = new JButton("Hapus");
        btnTambah.setBackground(new Color(13, 110, 253));  btnTambah.setForeground(Color.BLACK);
        btnEdit.setBackground(new Color(255, 193, 7));
        btnHapus.setBackground(new Color(220, 53, 69));    btnHapus.setForeground(Color.BLACK);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(anggotaTable), BorderLayout.CENTER);

        // DOKUMENTASI: Aksi tombol Tambah Anggota — membuka dialog AddAnggotaDialog
        btnTambah.addActionListener(e -> {
            new AddAnggotaDialog(this, anggotaDAO).setVisible(true);
            refreshAnggota();
        });

        // DOKUMENTASI: Aksi tombol Edit — membuka EditAnggotaDialog dengan data baris yang dipilih
        btnEdit.addActionListener(e -> {
            int row = anggotaTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih anggota yang ingin diedit."); return; }
            Anggota selected = new Anggota(
                (int)    anggotaModel.getValueAt(row, 0),
                (String) anggotaModel.getValueAt(row, 1),
                (String) anggotaModel.getValueAt(row, 2),
                (String) anggotaModel.getValueAt(row, 3),
                (String) anggotaModel.getValueAt(row, 4)
            );
            new EditAnggotaDialog(this, anggotaDAO, selected).setVisible(true);
            refreshAnggota();
        });

        // DOKUMENTASI: Aksi tombol Hapus — langsung eksekusi DELETE lalu refresh tabel
        btnHapus.addActionListener(e -> {
            int row = anggotaTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih anggota yang ingin dihapus."); return; }
            int id      = (int) anggotaModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus anggota ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try { anggotaDAO.deleteAnggota(id); refreshAnggota(); }
                catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
            }
        });

        return panel;
    }

    // DOKUMENTASI: Membangun panel tab Buku beserta tombol CRUD-nya
    private JPanel createBukuPanel() {
        JButton btnTambah = new JButton("Tambah Buku");
        JButton btnEdit   = new JButton("Edit");
        JButton btnHapus  = new JButton("Hapus");
        btnTambah.setBackground(new Color(13, 110, 253));  btnTambah.setForeground(Color.BLACK);
        btnEdit.setBackground(new Color(255, 193, 7));
        btnHapus.setBackground(new Color(220, 53, 69));    btnHapus.setForeground(Color.BLACK);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bukuTable), BorderLayout.CENTER);

        // DOKUMENTASI: Aksi tombol Tambah Buku — membuka dialog AddBukuDialog
        btnTambah.addActionListener(e -> {
            new AddBukuDialog(this, bukuDAO).setVisible(true);
            refreshBuku();
        });

        // DOKUMENTASI: Aksi tombol Edit — membuka EditBukuDialog dengan data baris yang dipilih
        btnEdit.addActionListener(e -> {
            int row = bukuTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih buku yang ingin diedit."); return; }
            Buku selected = new Buku(
                (int)    bukuModel.getValueAt(row, 0),
                (String) bukuModel.getValueAt(row, 1),
                (String) bukuModel.getValueAt(row, 2),
                (String) bukuModel.getValueAt(row, 3),
                (int)    bukuModel.getValueAt(row, 4),
                (int)    bukuModel.getValueAt(row, 5)
            );
            new EditBukuDialog(this, bukuDAO, selected).setVisible(true);
            refreshBuku();
        });

        // DOKUMENTASI: Aksi tombol Hapus — langsung eksekusi DELETE lalu refresh tabel
        btnHapus.addActionListener(e -> {
            int row = bukuTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus."); return; }
            int id      = (int) bukuModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus buku ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try { bukuDAO.deleteBuku(id); refreshBuku(); }
                catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
            }
        });

        return panel;
    }

    // DOKUMENTASI: Memuat ulang tabel Anggota dari database
    public void refreshAnggota() {
        anggotaModel.setRowCount(0);
        try {
            List<Anggota> list = anggotaDAO.getAllAnggota();
            for (Anggota a : list) {
                anggotaModel.addRow(new Object[]{a.getId(), a.getNama(), a.getNomorAnggota(), a.getAlamat(), a.getTelepon()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error memuat data anggota: " + ex.getMessage());
        }
    }

    // DOKUMENTASI: Memuat ulang tabel Buku dari database
    public void refreshBuku() {
        bukuModel.setRowCount(0);
        try {
            List<Buku> list = bukuDAO.getAllBuku();
            for (Buku b : list) {
                bukuModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPengarang(), b.getPenerbit(), b.getTahunTerbit(), b.getStok()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error memuat data buku: " + ex.getMessage());
        }
    }
}
