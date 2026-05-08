import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// DOKUMENTASI: Dialog form untuk menambah anggota baru — setara create.php untuk tabel anggota
public class AddAnggotaDialog extends JDialog {

    public AddAnggotaDialog(JFrame parent, AnggotaDAO dao) {
        super(parent, "Tambah Anggota Baru", true);
        setSize(420, 280);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // DOKUMENTASI: Panel input form menggunakan GridLayout
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        formPanel.add(new JLabel("Nama:"));
        JTextField txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Nomor Anggota:"));
        JTextField txtNomor = new JTextField();
        formPanel.add(txtNomor);

        formPanel.add(new JLabel("Alamat:"));
        JTextField txtAlamat = new JTextField();
        formPanel.add(txtAlamat);

        formPanel.add(new JLabel("Telepon:"));
        JTextField txtTelepon = new JTextField();
        formPanel.add(txtTelepon);

        // DOKUMENTASI: Panel tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSimpan = new JButton("Simpan Anggota");
        JButton btnBatal  = new JButton("Batal");
        btnSimpan.setBackground(new Color(25, 135, 84));
        btnSimpan.setForeground(Color.BLACK);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBatal);

        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // DOKUMENTASI: Aksi tombol Simpan — memanggil DAO untuk INSERT ke database
        btnSimpan.addActionListener(e -> {
            String nama    = txtNama.getText().trim();
            String nomor   = txtNomor.getText().trim();
            String alamat  = txtAlamat.getText().trim();
            String telepon = txtTelepon.getText().trim();

            if (nama.isEmpty() || nomor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Nomor Anggota wajib diisi.");
                return;
            }
            try {
                dao.createAnggota(nama, nomor, alamat, telepon);
                dispose(); // DOKUMENTASI: Tutup dialog — setara header("Location: index.php")
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saat menyimpan: " + ex.getMessage());
            }
        });

        btnBatal.addActionListener(e -> dispose());
    }
}
