import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructionsPanel extends JPanel implements GBCLayoutOrganiser{
    private final Main parentWindow;

    public InstructionsPanel(Main parentWindow_) {
        parentWindow = parentWindow_;
        //Set layout manager for this panel
        setLayout(new GridBagLayout());

        //Add Instructions
        final String INSTRUCTIONS_TEXT = "";

        JPanel instructionsPanel = new JPanel(new GridBagLayout());
        JLabel instructionsHeader = new JLabel("Instructions");
        JLabel instructionsLabel = new JLabel(INSTRUCTIONS_TEXT);

        instructionsPanel.add(instructionsHeader, createGbc(0, 0));
        instructionsPanel.add(instructionsLabel, createGbc(0, 1));

        //Add Return Button
        JPanel returnButtonPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return to Main Menu");

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnButtonPanel.add(returnButton, createGbc(0, 0));

        add(instructionsPanel, createGbc(0, 0));
        add(returnButtonPanel, createGbc(0, 1));
    }
}