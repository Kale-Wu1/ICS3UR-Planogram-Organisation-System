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

        //Add header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Creator Notes");
        headerPanel.add(mainMenuHeader);

        //Add Notes
        final String NOTES_TEXT = parentWindow.getStorageLayout().getNotes().replaceAll("\\\\n", "\n");

        JPanel notesPanel = new JPanel(new GridBagLayout());
        JTextArea notesTextArea = new JTextArea(NOTES_TEXT);

        notesPanel.add(notesTextArea, createGbc(0, 1));

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

        add(headerPanel, createGbc(0, 0));
        add(notesPanel, createGbc(0, 1));
        add(returnButtonPanel, createGbc(0, 2));
    }
    
}
