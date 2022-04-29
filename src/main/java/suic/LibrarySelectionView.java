package suic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import suic.controllers.LibrarySelectorController;

import java.io.IOException;

//aka stage
public class LibrarySelectionView extends AnchorPane {

    public LibrarySelectionView() {
        FXMLLoader loader = new FXMLLoader(this.getClass()
                .getResource("/fxml/libSelector.fxml"));

        loader.setRoot(this);

        getStylesheets().add(this.getClass()
                .getResource("/css/darktheme.css")
                .toExternalForm());


        loader.setController(new LibrarySelectorController());

        try {
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Best Library Selector");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
