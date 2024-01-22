import javax.swing.*;

/**
 * Main class. Only used for running
 */
public class Main {
    /**
     * Runs code by creating a new MainMenuWindow object of JSwing EDT
     * @param args main args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuWindow();
            }
        });
    }
}
