package suic.model;

import java.nio.file.Path;

public record Track(String name, Path path) {
    public static Track of(Path path) {
        String name = path.getFileName().toString();
        return new Track(name.substring(0, name.lastIndexOf(".")), path);
    }
}
