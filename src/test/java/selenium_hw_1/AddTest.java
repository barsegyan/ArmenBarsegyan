package selenium_hw_1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddTest {
    Calculator calculator;
    final double DELTA = Double.MIN_VALUE;

    @DataProvider
    public static Object[][] addDataProviderLong() {
        return new Object[][] {
                {1L, 4L, 5L},
                {5L, -5L, 0L},
                {35L, 42L, 77L}
        };
    }

    @DataProvider
    public static Object[][] addDataProviderDouble() {
        return new Object[][] {
                {1.0, 5.0, 6.0},
                {0.3, 0.2, 0.5},
                {0.1, -0.1, 0.0},
                {10000000000.0, 20000000000.0, 30000000000.0},
                {0.0000000001, 0.0000000002, 0.0000000003}
        };
    }

    @BeforeClass
    public void initCalculator() {
        calculator = new Calculator();
    }

    @Test(testName = "Simple Add Long",
            dataProvider = "addDataProviderLong"
    )
    public void testAddLong(Long a, Long b, Long res) {
        Assert.assertEquals(calculator.sum(a, b), res.longValue());
    }

    @Test(testName = "Simple Add Double",
            dataProvider = "addDataProviderDouble")
    public void testAddDouble(Double a, Double b, Double res) {
        Assert.assertEquals(calculator.sum(a, b), res, DELTA);
    }

}
