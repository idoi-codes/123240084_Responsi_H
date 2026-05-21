import controller.CattyCareController;
import view.CattyCareView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Menjalankan GUI di thread yang aman (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            // Set look and feel bawaan sistem operasi agar tampilan interaktif
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            CattyCareView view = new CattyCareView();
            new CattyCareController(view);
            view.setVisible(true);
        });
    }
}