package suic.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import lombok.SneakyThrows;
import suic.model.Track;
import suic.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LibrarySelectorController implements Initializable {

    @FXML
    private ComboBox<String> profileDropdown;

    @FXML
    private Button libraryOkButton;

    private final Map<String, List<Path>> profiles = new HashMap<>();

    private static final String PROFILE_PATH = "/profiles";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.err.println("Hello nether");

        loadProfiles();
        addListeners();
    }

    @SneakyThrows(URISyntaxException.class)
    private void loadProfiles() {
        Path path = Path.of(getClass().getResource(PROFILE_PATH).toURI().normalize());
        try (Stream<Path> folders = Files.walk(path)) {
            folders.filter(Files::isRegularFile).forEach(path2 -> {
                String profileName = StringUtils.simpleName(path2);
                List<Path> profileLibraries = loadProfile(path2);
                profiles.put(profileName, profileLibraries);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Profiles: " + profiles);
    }

    @SneakyThrows(java.io.IOException.class)
    private List<Path> loadProfile(Path path) {
        List<String> lines = Files.readAllLines(path);
        List<Path> retval = new ArrayList<>(lines.size());
        lines.forEach(x -> retval.add(Paths.get(x)));
        return retval;
    }

    private void addListeners() {
        System.out.println(libraryOkButton);
    }

}
