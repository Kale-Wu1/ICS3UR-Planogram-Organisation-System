import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The layout class. Stores information and translates between GUI and file information
 */
public class Layout implements FileUtils{
    //File Management
    private String directory;
    private String[] layoutInfo;


    //Layout Info
    private String name;
    private String author;
    private String units;
    private int roomLength;
    private int roomWidth;
    private String notes;
    private ArrayList<Shelf> shelfList;


    /**
     * Instantiates a new Layout.
     *
     * @param directory_ the directory
     */
    public Layout(String directory_) {
        layoutInfo = readFromFile(directory_);
        name = layoutInfo[0];
        author = layoutInfo[1];
        units = layoutInfo[2];
        roomWidth = Integer.parseInt(layoutInfo[3]);
        roomLength = Integer.parseInt(layoutInfo[4]);
        directory = directory_;
        notes = layoutInfo[6];

        //Add all other lines to shelfArr as Shelf objects
        shelfList = new ArrayList<>();
        if(layoutInfo.length > 7) {
            String[] shelfInfo;
            for(int i = 7; i < layoutInfo.length; i++) {
                shelfInfo = layoutInfo[i].split("\\|");
                if(shelfInfo.length == 6) { //If shelf has no items
                    shelfList.add(new Shelf(shelfInfo[0], Integer.parseInt(shelfInfo[1]), Integer.parseInt(shelfInfo[2]), Integer.parseInt(shelfInfo[3]), Integer.parseInt(shelfInfo[4]), Integer.parseInt(shelfInfo[5]), new String[0]));

                } else {
                    shelfList.add(new Shelf(shelfInfo[0], Integer.parseInt(shelfInfo[1]), Integer.parseInt(shelfInfo[2]), Integer.parseInt(shelfInfo[3]), Integer.parseInt(shelfInfo[4]), Integer.parseInt(shelfInfo[5]), shelfInfo[6].split(",")));

                }
            }
        }
    }

    /**
     * Gets name.
     *
     * @return the name of the layout
     */
//Accessor Methods
    public String getName() {
        return name;
    }

    /**
     * Gets author.
     *
     * @return the author of the layout
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets units.
     *
     * @return the units of the layout
     */
    public String getUnits() {
        return units;
    }

    /**
     * Gets length.
     *
     * @return the length of the layout
     */
    public int getroomLength() {
        return roomLength;
    }

    /**
     * Gets width.
     *
     * @return the width of the layout
     */
    public int getroomWidth() {
        return roomWidth;
    }

    /**
     * Get directory (filepath).
     *
     * @return the filepath of the layout
     */
    public String getDirectory(){
        return directory;
    }

    /**
     * Gets notes.
     *
     * @return the author notes of the layout
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Gets shelf list.
     *
     * @return the shelf list
     */
    public ArrayList<Shelf> getShelfList() {
        return shelfList;
    }


    /**
     * Sets notes.
     *
     * @param notes_ the updated author notes
     */
    public void setNotes(String notes_) {
        notes = notes_;
    }

    /**
     * Adds shelf to layout's shelfList.
     *
     * @param newShelf the shelf to add
     */
    public void addShelf(Shelf newShelf) {
        shelfList.add(newShelf);
    }

    /**
     * Removes shelf from layout's shelfList.
     * @param shelfToDelete the shelf to delete
     */
    public void removeShelf(Shelf shelfToDelete) {
        for(int i = 0; i < shelfList.size(); i++) {
            if(shelfList.get(i).getName().equals(shelfToDelete.getName())) {
                shelfList.remove(i);
                break;
            }
        }
    }

    /**
     * Save current layout information to file at directory.
     */
    public void saveToFile() {
        layoutInfo = new String[7+shelfList.size()];
        layoutInfo[0] = name;
        layoutInfo[1] = author;
        layoutInfo[2] = units;
        layoutInfo[3] = String.valueOf(roomWidth);
        layoutInfo[4] = String.valueOf(roomLength);
        layoutInfo[5] = directory;
        layoutInfo[6] = notes;

        //Write shelfArr to layoutInfo
        String shelfInfo;
        Shelf currentShelf;
        for(int i = 7; i < 7+shelfList.size(); i++) {
            currentShelf = shelfList.get(i-7);
            shelfInfo = currentShelf.getName() + "|" + currentShelf.getXPos() + "|" + currentShelf.getYPos() + "|"
                    + currentShelf.getLength() + "|" + currentShelf.getWidth() + "|" + currentShelf.getRotationAngle()
                    + "|" + Arrays.toString(currentShelf.getItemArr()).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "");
            layoutInfo[i] = shelfInfo;
        }

        //Write layoutInfo to file
        try {
            FileWriter fw = new FileWriter(directory);
            PrintWriter pw = new PrintWriter(fw);
            for(String element : layoutInfo) {
                pw.println(element);
            }
            pw.close();
        } catch (IOException e) {
            System.err.println("There was an error writing to file " + directory + ".");
        }
    }
}
