import javax.swing.*;
import java.awt.*;

public class LayoutViewerWindow extends JFrame {
    //Parent Panel
    JPanel parentPanel;

    //Define Main Divisions
    JPanel parentViewPanel;
    JPanel parentToolMenuPanel;

    //Current Layout Object
    Layout currentLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LayoutViewerWindow();
            }
        });
    }

    public LayoutViewerWindow() {
        //currentLayout = currentLayout_;


        final int FRAME_WIDTH = 1000;
        final int FRAME_LENGTH = 500;

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle("[Layout Name] - Planogram v.1"); //TODO: Add Layout Name to Title
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Add Base Panels
        parentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc;

        gbc = createGbc(0, 0);
        gbc.weightx = 0.65;
        parentViewPanel = new JPanel(new CardLayout());
        parentViewPanel.setBackground(Color.RED);
        parentPanel.add(parentViewPanel, gbc);

        gbc = createGbc(1, 0);
        gbc.weightx = 0.35;
        parentToolMenuPanel = new JPanel(new CardLayout());
        parentToolMenuPanel.setBackground(Color.GREEN);
        parentToolMenuPanel.add(new JLabel(""));
        parentPanel.add(parentToolMenuPanel, gbc);

        parentPanel.setBackground(Color.BLUE);
        add(parentPanel);


        setVisible(true);

    }


    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

}