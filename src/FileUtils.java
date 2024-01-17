import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public interface FileUtils {
    default boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
