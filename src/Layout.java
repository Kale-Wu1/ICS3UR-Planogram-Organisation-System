import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

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

        //Add all other lines to shelfArr as Shelf objects
        shelfArr = new Shelf[layoutInfo.length-7];
        if(layoutInfo.length > 7) {
            String[] shelfInfo;
            for(int i = 7; i < layoutInfo.length; i++) {
                shelfInfo = layoutInfo[i].split(" ");
                shelfArr[i-7] = new Shelf(shelfInfo[0], Integer.parseInt(shelfInfo[1]), Integer.parseInt(shelfInfo[2]),Integer.parseInt(shelfInfo[3]), Integer.parseInt(shelfInfo[4]),Integer.parseInt(shelfInfo[5]), shelfInfo[6].split(","));
            }
        }
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
    public String[] getLayoutInfo() {
        return layoutInfo;
    }

    public void setShelfArr(Shelf[] shelfArr_) {
        shelfArr = shelfArr_;
    }
}
