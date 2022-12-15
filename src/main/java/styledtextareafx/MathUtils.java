package styledtextareafx;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static double roundUpDouble(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
