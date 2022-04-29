package suic.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;
import suic.MusicPlayerHandler;
import suic.PlayButtonHandler;
import suic.SharedProperties;
import suic.model.Track;
import suic.model.TrackCell;
import suic.util.MathUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class MusicPlayerController implements Initializable {

    @FXML
    private ListView<Track> trackView;

    @FXML
    private CheckBox loopCheckBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Circle currentTimeCircle;

    @FXML
    private ImageView playButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private MenuItem loadFolderItem;

    private final Path DEFAULT_PATH = Path.of("./mp3s");

    private PlayButtonHandler playButtonHandler;
    private MusicPlayerHandler musicPlayerHandler;

    private final Map<String, Image> images = new HashMap<>();

    private final SharedProperties sharedProperties = SharedProperties.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        playButtonHandler = new PlayButtonHandler();
        musicPlayerHandler = new MusicPlayerHandler();
        loadTracks(DEFAULT_PATH);
        loadImages();
        initCellFactories();
        initInputListeners();
        initBindings();
    }

    @SneakyThrows(URISyntaxException.class)
    private void loadImages() {
        Path path = Path.of(getClass().getResource("/images").toURI().normalize());
        try (Stream<Path> folders = Files.walk(path)) {
            folders.filter(Files::isRegularFile).forEach(file -> {
                String name = file.getFileName().toString();
                images.put(name.substring(0, name.lastIndexOf(".")), new Image(file.toString()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTracks(Path folder) {
        try (Stream<Path> files = Files.walk(folder)) {
            List<Track> tracks = files.filter(Files::isRegularFile)
                    .filter(this::isMp3)
                    .map(Track::of)
                    .toList();
            trackView.setItems(FXCollections.observableArrayList(tracks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initInputListeners() {

        playButton.setOnMousePressed(event -> {
            // if we haven't selected a track then the selected index will be -1 therefore we're returning the NOT_PLAYING state
            PlayButtonHandler.State newState = trackView.getSelectionModel()
                    .getSelectedIndex() == -1 ? PlayButtonHandler.State.NOT_PLAYING : playButtonHandler.toggle();
            switch (newState) {
                case PLAYING -> {
                    boolean successful = playTrack();
                    if (successful) {
                        playButton.setImage(images.get("pause_button_hover"));
                    }
                }
                case PAUSED -> {
                    boolean successful = pauseTrack();
                    if (successful) {
                        playButton.setImage(images.get("play_button_hover"));
                    }
                }
            }
        });

        playButton.setOnMouseEntered(event -> {

            Image image = switch (playButtonHandler.getState()) {
                case NOT_PLAYING, PAUSED -> images.get("play_button_hover");
                case PLAYING -> images.get("pause_button_hover");
            };

            playButton.setImage(image);
        });

        playButton.setOnMouseExited(event -> {

            Image image = switch (playButtonHandler.getState()) {
                case NOT_PLAYING, PAUSED -> images.get("play_button");
                case PLAYING -> images.get("pause_button");
            };

            playButton.setImage(image);
        });

        loadFolderItem.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);
            loadTracks(file.toPath());
        });
    }

    private void initBindings() {
        sharedProperties.getVolume().bind(volumeSlider.valueProperty());
    }

    private boolean playTrack() {
        Track selectedTrack = trackView.getSelectionModel().getSelectedItem();
        if (selectedTrack == null) {
            return false;
        }
        musicPlayerHandler.playTrack(selectedTrack);
        currentTimeCircle.setVisible(true);


        MediaPlayer player = musicPlayerHandler.getPlayer();
        player.currentTimeProperty().addListener((observable, oldValue, currentTime) -> {
            double currentProgress = currentTime.toMillis() / player.getStopTime()
                    .toMillis();
            int translateDotX = (int) MathUtils.map(currentProgress, 0, 1, 0, progressBar.getPrefWidth());
            currentTimeCircle.setTranslateX(Math.max(0, translateDotX - 8));
            progressBar.setProgress(currentProgress);
        });
        return true;
    }

    private boolean pauseTrack() {
        if (!musicPlayerHandler.isTrackSet()) {
            return false;
        }
        musicPlayerHandler.pauseTrack();
        return true;
    }

    private boolean isMp3(Path path) {
        return path.getFileName().toString().endsWith(".mp3");
    }

    private void initCellFactories() {
        trackView.setCellFactory(param -> new TrackCell());
    }
}
