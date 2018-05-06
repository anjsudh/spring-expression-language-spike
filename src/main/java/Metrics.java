import formula.Formula;
import formula.FormulaeResult;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static formula.Formula.parseExpression;
import static java.util.stream.Collectors.toList;
import static utils.NumberUtil.getBigDecimal;

//Class must be immutable
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metrics implements Serializable {
    private Integer groupId;
    private BigDecimal a;
    private BigDecimal b;

    private Map<String, FormulaeResult> calculatedMetrics =new HashMap<>();
    private List<Formula> formulaes = new ArrayList<>();

    public void recalculateMetrics(){
        calculatedMetrics.clear();
        formulaes.forEach(formula -> {
            BigDecimal value = parseExpression(formula.getExpression(), this);
            List<BigDecimal> subExpressionValues = formula.getSubExpressions()
                    .stream()
                    .map(subExpression -> parseExpression(subExpression, this))
                    .collect(toList());
            calculatedMetrics.put(
                    formula.getName(),
                    new FormulaeResult(formula.getName(),value, subExpressionValues)
                    );
        });
    }

    public static BigDecimal sum(Object data) {
        if(data instanceof List) {
            List keys = (List) data;
            return getBigDecimal(keys.get(0)).add(getBigDecimal(keys.get(1)));
        }
        return getBigDecimal(data);

    }

    public Metrics reduce(Metrics m) {
        this.recalculateMetrics();
        m.recalculateMetrics();
        Metrics build = Metrics.builder()
                .formulaes(this.getFormulaes())
                .groupId(this.getGroupId())
                .a(this.getA().add(m.getA()))
                .b(this.getB().add(m.getB()))
                .calculatedMetrics(new HashMap<>())
                .build();
        this.formulaes.forEach(formula -> {
                    build.calculatedMetrics.put(formula.getName(), FormulaeResult.reduce(
                        formula,
                        this.getCalculatedMetrics().get(formula.getName()),
                        m.getCalculatedMetrics().get(formula.getName()),
                        build));
        });

        return build;
    }
}


