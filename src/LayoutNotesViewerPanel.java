import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutNotesViewerPanel extends JPanel implements GBCLayoutOrganiser{
    LayoutViewerWindow parentWindow;
    public LayoutNotesViewerPanel(LayoutViewerWindow parentWindow_) {
        parentWindow = parentWindow_;
        //Set layout manager for this panel
        setLayout(new GridBagLayout());

        //Add Notes
        final String NOTES_TEXT = "[This is filler text that will be created by the user!]";

        JPanel notesPanel = new JPanel(new GridBagLayout());
        JLabel notesHeader = new JLabel("Notes");
        JLabel notesLabel = new JLabel(NOTES_TEXT);

        notesPanel.add(notesHeader, createGbc(0, 0));
        notesPanel.add(notesLabel, createGbc(0, 1));

        //Add Return Button
        JPanel returnButtonPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Back");

        returnButton.addActionListener(new ActionListener() {
            //TODO: Return to previous card
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnButtonPanel.add(returnButton, createGbc(0, 0));

        add(notesPanel, createGbc(0, 0));
        add(returnButtonPanel, createGbc(0, 1));
    }
    
}
