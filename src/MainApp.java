import javax.swing.SwingUtilities;

// DOKUMENTASI: Titik masuk aplikasi — file ini yang dijalankan pertama kali
// Klik tombol ▷ Run di VS Code pada file ini untuk memulai aplikasi
public class MainApp {

    public static void main(String[] args) {
        // DOKUMENTASI: SwingUtilities.invokeLater memastikan UI dibuat di thread yang benar
        // Ini adalah cara standar memulai aplikasi Swing di Java
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
