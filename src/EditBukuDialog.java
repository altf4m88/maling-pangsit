import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// DOKUMENTASI: Dialog form untuk mengedit data buku — setara edit.php untuk tabel buku
public class EditBukuDialog extends JDialog {

    public EditBukuDialog(JFrame parent, BukuDAO dao, Buku buku) {
        super(parent, "Edit Data Buku", true);
        setSize(420, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        formPanel.add(new JLabel("Judul Buku:"));
        // DOKUMENTASI: Mengisi nilai awal dari objek buku — setara value="<?php echo $row['judul']; ?>"
        JTextField txtJudul = new JTextField(buku.getJudul());
        formPanel.add(txtJudul);

        formPanel.add(new JLabel("Pengarang:"));
        JTextField txtPengarang = new JTextField(buku.getPengarang());
        formPanel.add(txtPengarang);

        formPanel.add(new JLabel("Penerbit:"));
        JTextField txtPenerbit = new JTextField(buku.getPenerbit());
        formPanel.add(txtPenerbit);

        formPanel.add(new JLabel("Tahun Terbit:"));
        JSpinner spnTahun = new JSpinner(new SpinnerNumberModel(buku.getTahunTerbit(), 1000, 9999, 1));
        formPanel.add(spnTahun);

        formPanel.add(new JLabel("Stok:"));
        JSpinner spnStok = new JSpinner(new SpinnerNumberModel(buku.getStok(), 0, 9999, 1));
        formPanel.add(spnStok);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnUpdate = new JButton("Perbarui Buku");
        JButton btnBatal  = new JButton("Batal");
        btnUpdate.setBackground(new Color(255, 193, 7));
        btnPanel.add(btnUpdate);
        btnPanel.add(btnBatal);

        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // DOKUMENTASI: Aksi tombol Perbarui — memanggil DAO untuk UPDATE ke database
        btnUpdate.addActionListener(e -> {
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
                dao.updateBuku(buku.getId(), judul, pengarang, penerbit, tahun, stok);
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saat memperbarui: " + ex.getMessage());
            }
        });

        btnBatal.addActionListener(e -> dispose());
    }
}
