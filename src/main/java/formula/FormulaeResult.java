
package formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

import static formula.Formula.parseExpression;
import static java.util.stream.Collectors.toList;

@ToString
@AllArgsConstructor
@Getter
public class FormulaeResult {
    private String name;
    private BigDecimal value;
    private List<BigDecimal> subExpressionValues;

    public static FormulaeResult reduce(Formula f, FormulaeResult x, FormulaeResult y, Object object){
        String expression = f.getExpression();
        List<BigDecimal> subExpressionValues = f.getSubExpressions()
                .stream()
                .map(subExpression -> parseExpression(subExpression, object))
                .collect(toList());

        List thisMetricValues = x.getSubExpressionValues();
        List thatMetricValues = y.getSubExpressionValues();
        for(int i = 0; i< f.getSubExpressions().size(); i++) {
            String from = f.getSubExpressions().get(i);
            from = from.replaceAll("[\\<\\(\\[\\{\\\\\\^\\-\\=\\$\\!\\|\\]\\}\\)\\?\\*\\+\\.\\>]", "\\\\$0");
            String to = String.format("T(java.util.Arrays).asList(%s,%s)",
                    thisMetricValues.get(i), thatMetricValues.get(i));
            expression = expression.replaceAll(from, to);
        }
        BigDecimal expressionResult = parseExpression(expression, object);
        return new FormulaeResult(f.getName(), expressionResult, subExpressionValues);
    }
}