import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

public interface FileUtils {
    default boolean isValidPath(String path) {
        try {
            Paths.get(path);
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
}
