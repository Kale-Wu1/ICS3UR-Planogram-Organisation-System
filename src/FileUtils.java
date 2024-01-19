import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public interface FileUtils {
    default boolean isValidPath(String directory) {
        try {
            Paths.get(directory);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    default String[] readFromFile(String directory) {
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

    default void addLine(String directory, String line) {
        //Get list of content from file
        String[] oldFileInfo = readFromFile(directory);
        String[] fileInfo = new String[oldFileInfo.length + 1];

        System.arraycopy(oldFileInfo, 0, fileInfo, 0, oldFileInfo.length);

        fileInfo[fileInfo.length-1] = line;

        try {
            FileWriter fw = new FileWriter(directory);
            PrintWriter pw = new PrintWriter(fw);

            for(String element : fileInfo) {
                pw.println();
            }

            pw.close();
        } catch (IOException e) {
            System.err.println("There was a problem writing to file.");
        }
    }

    default void setLine(String directory, int lineIndex, String newString) {
        String[] fileInfo = readFromFile(directory);
        fileInfo[lineIndex] = newString;

        try {
            FileWriter fw = new FileWriter(directory);
            PrintWriter pw = new PrintWriter(fw);

            for(String element : fileInfo) {
                pw.println();
            }

            pw.close();
        } catch (IOException e) {
            System.err.println("There was a problem writing to file.");
        }
    }
}
