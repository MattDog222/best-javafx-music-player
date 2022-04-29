package suic.util;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class StringUtils {

    /**
     * Gets the file name with no extension.
     * @param path Path to file
     * @return file name without extension
     */
    public String simpleName(Path path) {
        String name = path.getFileName().toString();
        return name.substring(0, name.lastIndexOf("."));
    }

}
