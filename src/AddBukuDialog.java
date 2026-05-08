import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// DOKUMENTASI: Dialog form untuk menambah buku baru — setara create.php untuk tabel buku
public class AddBukuDialog extends JDialog {

    public AddBukuDialog(JFrame parent, BukuDAO dao) {
        super(parent, "Tambah Buku Baru", true);
        setSize(420, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // DOKUMENTASI: Panel input form menggunakan GridLayout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        formPanel.add(new JLabel("Judul Buku:"));
        JTextField txtJudul = new JTextField();
        formPanel.add(txtJudul);

        formPanel.add(new JLabel("Pengarang:"));
        JTextField txtPengarang = new JTextField();
        formPanel.add(txtPengarang);

        formPanel.add(new JLabel("Penerbit:"));
        JTextField txtPenerbit = new JTextField();
        formPanel.add(txtPenerbit);

        formPanel.add(new JLabel("Tahun Terbit:"));
        // DOKUMENTASI: JSpinner untuk input angka — setara <input type="number"> di HTML
        JSpinner spnTahun = new JSpinner(new SpinnerNumberModel(2024, 1000, 9999, 1));
        formPanel.add(spnTahun);

        formPanel.add(new JLabel("Stok:"));
        JSpinner spnStok = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        formPanel.add(spnStok);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSimpan = new JButton("Simpan Buku");
        JButton btnBatal  = new JButton("Batal");
        btnSimpan.setBackground(new Color(25, 135, 84));
        btnSimpan.setForeground(Color.BLACK);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBatal);

        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // DOKUMENTASI: Aksi tombol Simpan — memanggil DAO untuk INSERT ke database
        btnSimpan.addActionListener(e -> {
            String judul     = txtJudul.getText().trim();
            String pengarang = txtPengarang.getText().trim();
            String penerbit  = txtPenerbit.getText().trim();
            int    tahun     = (int) spnTahun.getValue();
            int    stok      = (int) spnStok.getValue();

            if (judul.isEmpty() || pengarang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul dan Pengarang wajib diisi.");
                return;
            }
            try {
                dao.createBuku(judul, pengarang, penerbit, tahun, stok);
                dispose(); // DOKUMENTASI: Tutup dialog — setara header("Location: index.php")
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saat menyimpan: " + ex.getMessage());
            }
        });

        btnBatal.addActionListener(e -> dispose());
    }
}
