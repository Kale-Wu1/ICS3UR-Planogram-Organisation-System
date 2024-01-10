import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

public class MainMenu extends JFrame{
    //Parent Panel
    JPanel parentPanel;

    //CardLayout Panels
    JPanel mainMenu;
    JPanel instructions;
    JPanel layoutCreator;

    //Organisation Panels
    JPanel headerPanel;
    JPanel availableLayoutsPanel;
    JPanel searchDirectoryPanel;
    JPanel utilityButtonsPanel;
    JPanel exitButtonPanel;



    public static void main(String[] args) {
        new MainMenu();
    }

    public MainMenu() {
        //Initialise Window
        final int FRAME_WIDTH = 600;
        final int FRAME_LENGTH = 750;
        final int INDENT_GAP = 50;

        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle("Planogram v.1");

        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //Initialise parentPanel
        parentPanel = new JPanel(new CardLayout());


        //Define mainMenu JPanel
        mainMenu = new JPanel();
        GroupLayout mainMenuLayout = new GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);

        mainMenuLayout.setAutoCreateGaps(true);
        mainMenuLayout.setAutoCreateContainerGaps(true);

        //Add Header
        //TODO: Center text in title
        headerPanel = new JPanel();
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Welcome to the Planogram System");

        headerPanel.add(mainMenuHeader);


        //Add Available Layouts Message
        JLabel availLayoutMessage = new JLabel("Current Layouts: ");

        //Add Available Layouts Results
        availableLayoutsPanel = new JPanel();
        availableLayoutsPanel.setBounds(INDENT_GAP, 400, FRAME_WIDTH-2*INDENT_GAP, 800);
        availableLayoutsPanel.add(availLayoutMessage);

        JScrollBar availLayoutMenuScroll = new JScrollBar();
        availLayoutMenuScroll.setBounds(50, 20, 20, 300);
        availLayoutMenuScroll.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                System.out.println(e.getValue());
            }
        });
        availableLayoutsPanel.add(availLayoutMenuScroll);


        //headerPanel.setLayout(new GroupLayout());

        //Add Components to mainMenuLayout
        mainMenuLayout.setHorizontalGroup(
                mainMenuLayout.createSequentialGroup()
                        .addGroup(mainMenuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(mainMenuHeader)
                                .addComponent(availLayoutMessage)
                                .addComponent(availableLayoutsPanel)
                        )

        );

        mainMenuLayout.setVerticalGroup(
                mainMenuLayout.createSequentialGroup()
                        .addComponent(mainMenuHeader)
                        .addComponent(availLayoutMessage)
                        .addComponent(availableLayoutsPanel)

        );


        //Add Components to parentPanel
        parentPanel.add(mainMenu, "mainMenu");
        //parentPanel.add(instructions, "instructions");
        //parentPanel.add(layoutCreator, "layoutCreator");

        //Add Parent Panel to Window
        add(parentPanel);

        setVisible(true);
    }

    //Method searchFile searches a directory for valid layout files and returns all files that match using linear search
    private File[] searchFile(String directory) {
        return null;
    }

    //Code from Hovercraft Full Of Eels from https://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell
    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = (x == 0) ? new Insets(5, 0, 5, 5) : new Insets(5, 5, 5, 0);
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

}
