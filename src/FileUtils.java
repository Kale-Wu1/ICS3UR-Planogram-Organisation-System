import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Utilities interface for file interaction
 */
public interface FileUtils {
    /**
     * Returns if the directory is a valid filepath.
     * @param directory the directory
     * @return the boolean
     */
    default boolean isValidPath(String directory) {
        try {
            Paths.get(directory);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    /**
     * Read text from file.
     * @param directory the directory of the file to read
     * @return a String[] of the text from the file
     */
    default String[] readFromFile(String directory) {
        String[] layoutInfo;
        try {
            //Instantiate file readers
            FileReader fr = new FileReader(directory);
            BufferedReader br = new BufferedReader(fr);

            //Add all lines from file to tempContentList
            ArrayList<String> tempContentList = new ArrayList<>();
            String currentLine = br.readLine();
            while(currentLine != null) {
                tempContentList.add(currentLine);
                currentLine = br.readLine();
            }

            //Convert Arraylist to String[] and return
            layoutInfo = tempContentList.toArray(new String[0]);
            return layoutInfo;

        } catch (IOException e) {
            System.err.println("An unexpected error regarding file reading occurred...");
        }

        //return empty String[] if file was unreadable
        return new String[]{};
    }




}
