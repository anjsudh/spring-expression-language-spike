package utils;

import java.math.BigDecimal;

public class NumberUtil {

    public static BigDecimal getBigDecimal(Object result) {
        BigDecimal value = null;
        if(result instanceof BigDecimal) {
            value = (BigDecimal)result;
        } else if(result instanceof Double) {
            value = BigDecimal.valueOf((Double)result);
        } else if(result instanceof Float) {
            value = BigDecimal.valueOf((Float)result);
        } else if(result instanceof Integer) {
            value = BigDecimal.valueOf((Integer)result);
        }
        return value;
    }
}
