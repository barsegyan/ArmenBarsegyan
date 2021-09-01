package selenium_hw_1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubTest {
    Calculator calculator;
    final double DELTA = 0.0000000000000001; 

    @DataProvider
    public static Object[][] subDataProviderLong() {
        return new Object[][] {
                {4L, 1L, 3L},
                {5L, -5L, 10L},
                {35L, 42L, -7L},
                {155555L, 155555L, 0L},
                {0L, 0L, 0L}
        };
    }

    @DataProvider
    public static Object[][] subDataProviderDouble() {
        return new Object[][] {
                {5.0, 1.0, 4.0},
                {0.3, 0.2, 0.1},
                {0.1, -0.1, 0.2},
                {20000000000.0, 10000000000.0, 10000000000.0},
                {0.0000000003, 0.0000000002, 0.0000000001},
                {0.000000001, 0.000000001, 0.0},
                {1.0, 0.000001, 0.999999},
                {0.0, 0.0, 0.0}
        };
    }

    @BeforeClass
    public void initCalculator() {
        calculator = new Calculator();
    }

    @Test(testName = "Simple Sub Long",
            dataProvider = "subDataProviderLong"
    )
    public void testSubLong(Long a, Long b, Long res) {
        Assert.assertEquals(calculator.sub(a, b), res.longValue());
    }

    @Test(testName = "Simple Sub Double",
            dataProvider = "subDataProviderDouble")
    public void testSubDouble(Double a, Double b, Double res) {
        Assert.assertEquals(calculator.sub(a, b), res, DELTA);
    }


}
