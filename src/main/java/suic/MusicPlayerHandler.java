package suic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import suic.model.Track;
import suic.util.MathUtils;

public class MusicPlayerHandler {
    private Track track = null;
    @Getter
    private MediaPlayer player = null;

    private final SharedProperties sharedProperties = SharedProperties.getInstance();

    public boolean isTrackSet() {
        return track != null;
    }

    public void playTrack(Track track) {
        if (player != null && player.getStatus() == MediaPlayer.Status.PAUSED) {
            player.play();
            return;
        }
        this.track = track;
        if (player != null) {
            player.stop();
            player = null;
        }
        Media media = new Media(track.path().toUri().toString());
        player = new MediaPlayer(media);
        player.volumeProperty().bind(sharedProperties.getVolume());
        player.play();
    }

    public void pauseTrack() {
        player.pause();
    }
}
