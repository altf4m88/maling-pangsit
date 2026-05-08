import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// DOKUMENTASI: Dialog form untuk mengedit data anggota — setara edit.php untuk tabel anggota
public class EditAnggotaDialog extends JDialog {

    public EditAnggotaDialog(JFrame parent, AnggotaDAO dao, Anggota anggota) {
        super(parent, "Edit Data Anggota", true);
        setSize(420, 280);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        formPanel.add(new JLabel("Nama:"));
        // DOKUMENTASI: Mengisi nilai awal dari objek anggota — setara value="<?php echo $row['nama']; ?>"
        JTextField txtNama = new JTextField(anggota.getNama());
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Nomor Anggota:"));
        JTextField txtNomor = new JTextField(anggota.getNomorAnggota());
        formPanel.add(txtNomor);

        formPanel.add(new JLabel("Alamat:"));
        JTextField txtAlamat = new JTextField(anggota.getAlamat());
        formPanel.add(txtAlamat);

        formPanel.add(new JLabel("Telepon:"));
        JTextField txtTelepon = new JTextField(anggota.getTelepon());
        formPanel.add(txtTelepon);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnUpdate = new JButton("Perbarui Anggota");
        JButton btnBatal  = new JButton("Batal");
        btnUpdate.setBackground(new Color(255, 193, 7));
        btnPanel.add(btnUpdate);
        btnPanel.add(btnBatal);

        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // DOKUMENTASI: Aksi tombol Perbarui — memanggil DAO untuk UPDATE ke database
        btnUpdate.addActionListener(e -> {
            String nama    = txtNama.getText().trim();
            String nomor   = txtNomor.getText().trim();
            String alamat  = txtAlamat.getText().trim();
            String telepon = txtTelepon.getText().trim();

            if (nama.isEmpty() || nomor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Nomor Anggota wajib diisi.");
                return;
            }
            try {
                dao.updateAnggota(anggota.getId(), nama, nomor, alamat, telepon);
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saat memperbarui: " + ex.getMessage());
            }
        });

        btnBatal.addActionListener(e -> dispose());
    }
}
