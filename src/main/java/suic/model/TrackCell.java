package suic.model;

import javafx.scene.control.ListCell;

public class TrackCell extends ListCell<Track> {
    @Override
    protected void updateItem(Track item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(item.name());
    }
}
