import javax.swing.*;

public class PanelTester extends JFrame {
    public PanelTester(JPanel panel) {
        setBounds(0, 0, 500, 600);
        setTitle("Planogram v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);

        setVisible(true);
    }
}
