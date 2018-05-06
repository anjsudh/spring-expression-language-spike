import formula.Formula;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MetricsTest {

    @Test
    public void shouldReduce() {
        List<Formula> formulaes = new ArrayList<>();
        formulaes.add(new Formula("formula_1","sum(a * b) + 1000", asList("a * b")));
        formulaes.add(new Formula("formula_2","sum(a + b + 1000)", asList("a + b + 1000")));
        formulaes.add(new Formula("formula_3","sum(a) + sum(b) + 2000", asList()));
        formulaes.add(new Formula("formula_4","sum(a) * sum(b)", asList()));

        Metrics metrics1= TestDataMaker.getDefaultMetricsBuilder(formulaes).build();
//        metrics1.recalculateMetrics();
        Metrics metrics2= TestDataMaker.getDefaultMetricsBuilder(formulaes).build();
//        metrics2.recalculateMetrics();
        Metrics reducedKPI = metrics1.reduce(metrics2);
        assertEquals(BigDecimal.valueOf(1200l), reducedKPI.getCalculatedMetrics().get("formula_1").getValue());
        assertEquals(BigDecimal.valueOf(2040l), reducedKPI.getCalculatedMetrics().get("formula_2").getValue());
        assertEquals(BigDecimal.valueOf(2040l), reducedKPI.getCalculatedMetrics().get("formula_3").getValue());
        assertEquals(BigDecimal.valueOf(400l), reducedKPI.getCalculatedMetrics().get("formula_4").getValue());
    }
}