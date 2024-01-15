import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Layout {
    //File Management
    private String directory;
    private File layoutInfoFile;
    private String[] fileInfo;


    //Layout Info
    private String name;
    private String units;
    private int roomLength;
    private int roomWidth;
    private String notes;
    private Shelf[] shelfArr;


    public Layout(String directory) {
        layoutInfoFile = new File(directory);
    }

    //Accessor Methods
    public String getName() {
        return name;
    }
    public int getroomLength() {
        return roomLength;
    }

    public int getroomWidth() {
        return roomWidth;
    }


}
