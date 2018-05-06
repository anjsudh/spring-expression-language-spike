package formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.util.List;

import static utils.NumberUtil.getBigDecimal;

@AllArgsConstructor
@Getter
public class Formula {
    String name;
    String expression;
    List<String> subExpressions;

    public static BigDecimal parseExpression(String expression, Object contextObject) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expr = parser.parseExpression(expression);
        return getBigDecimal(expr.getValue(contextObject));
    }
}