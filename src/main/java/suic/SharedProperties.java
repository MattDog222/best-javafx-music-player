package suic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SharedProperties {

    private final DoubleProperty volume = new SimpleDoubleProperty(0.05);

    private static SharedProperties instance = null;

    public static SharedProperties getInstance() {
        if (instance == null) {
            instance = new SharedProperties();
        }
        return instance;
    }

    private SharedProperties() {

    }
}
