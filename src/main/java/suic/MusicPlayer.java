package suic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO
 * Add lyric text file displays
 * Add pre-song (i.e. 3 second) delay loop option
 * Add listener to playbuttonhandler to change image
 */

public class MusicPlayer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/css/darktheme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Better Music Player");
        primaryStage.show();
    }
}
