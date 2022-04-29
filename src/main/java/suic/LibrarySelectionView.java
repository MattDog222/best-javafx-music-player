package suic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import suic.controllers.LibrarySelectorController;

import java.io.IOException;


public class LibrarySelectionView extends AnchorPane {

    private final static String TITLE = "Manage Libraries";
    private final static String FXML_PATH = "/fxml/libSelector.fxml";
    private final static String CSS_PATH = "/css/darktheme.css";

    public LibrarySelectionView() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(FXML_PATH));
        loader.setRoot(this);
        getStylesheets().add(this.getClass().getResource(CSS_PATH).toExternalForm());
        loader.setController(new LibrarySelectorController());

        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(TITLE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
