package suic.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {
    public double map(double value, double iStart, double iStop, double oStart, double oStop) {
        return oStart + (oStop - oStart) * ((value - iStart) / (iStop - iStart));
    }
}
