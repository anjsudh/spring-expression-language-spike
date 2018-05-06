import formula.Formula;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class TestDataMaker {
    public static Metrics.MetricsBuilder getDefaultMetricsBuilder(List<Formula> formulaes) {
        return new Metrics.MetricsBuilder()
                .a(BigDecimal.TEN)
                .b(BigDecimal.TEN)
                .groupId(1)
                .formulaes(formulaes)
                .calculatedMetrics(new HashMap<>());
    }
}
