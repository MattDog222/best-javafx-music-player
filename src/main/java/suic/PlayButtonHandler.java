package suic;

import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PlayButtonHandler {
    private State state = State.NOT_PLAYING;

    public State toggle() {
        State newState = switch (state) {
            case PLAYING -> State.PAUSED;
            case PAUSED, NOT_PLAYING -> State.PLAYING;
        };

        state = newState;

        return newState;

    }

    public enum State {
        PLAYING, PAUSED, NOT_PLAYING
    }
}
