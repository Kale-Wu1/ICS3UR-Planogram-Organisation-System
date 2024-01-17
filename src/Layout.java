import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class Layout {
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
    private Shelf[] shelfArr;


    public Layout(String directory_) {
        layoutInfo = readFromFile(directory_);
        name = layoutInfo[0];
        author = layoutInfo[1];
        units = layoutInfo[2];
        roomWidth = Integer.parseInt(layoutInfo[3]);
        roomLength = Integer.parseInt(layoutInfo[4]);
        directory = directory_;
        notes = layoutInfo[6];

    }

    //Method read information from given directory and returns a String[] with the information line by line. Returns empty String[] if reading fails
    private String[] readFromFile(String directory) {
        String[] layoutInfo;
        try {
            //Read information from file
            FileReader fr = new FileReader(directory);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> tempContentList = new ArrayList<>();
            String currentLine = br.readLine();
            while(currentLine != null) {
                tempContentList.add(currentLine);
                currentLine = br.readLine();
            }

            layoutInfo = new String[tempContentList.toArray().length];
            for(int i = 0; i < layoutInfo.length; i++) {
                layoutInfo[i] = tempContentList.get(i);
            }
            return layoutInfo;

        } catch (IOException e) {
            System.err.println("An unexpected error regarding file reading occurred...");
        }
        return new String[]{};
    }

    //Accessor Methods
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getUnits() {
        return units;
    }
    public int getroomLength() {
        return roomLength;
    }
    public int getroomWidth() {
        return roomWidth;
    }
    public String getDirectory(){
        return directory;
    }
    public String getNotes() {
        return notes;
    }
    public Shelf[] getShelfArr() {
        return shelfArr;
    }
}
