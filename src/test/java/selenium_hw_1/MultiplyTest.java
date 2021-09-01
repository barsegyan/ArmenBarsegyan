package selenium_hw_1;

import com.epam.tat.module4.Calculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultiplyTest {
    Calculator calculator;
    final double DELTA = 0.000000000000000000000001;

    @BeforeClass
    public void initCalculator() {
        calculator = new Calculator();
    }

    @DataProvider
    public static Object[][] multiplyDataProviderLong() {
        return new Object[][] {
                {2L, 2L, 4L},
                {5L, 3L, 15L},
                {-5L, 4L, -20L},
                {-10L, -7L, 70L},
                {5L, 0L, 0L},
                {-0L, 1000L, 0L},
                {14L, 1L, 14L},
                {-1L, 20L, -20L}
        };
    }

    @DataProvider
    public static Object[][] multiplyDataProviderDouble() {
        return new Object[][] {
                {0.2, 0.2, 0.04},
                {5.0, 3.0, 15.0},
                {-5.0, 0.4, -2.0},
                {-10.0, -0.07, 0.7},
                {5.0, 0.0, 0.0},
                {-0.0, 1000.0, 0.0},
                {0.014, 1.0, 0.014},
                {-.01, 200.0, -2.0}
        };
    }

    @Test(testName = "Simple Multiply Long",
            dataProvider = "multiplyDataProviderLong")
    public void multiplyTestLong(Long a, Long b, Long res) {
        Assert.assertEquals(calculator.mult(a, b), res.longValue());
    }

    @Test(testName = "Simple Multiply Double",
            dataProvider = "multiplyDataProviderDouble")
    public void multiplyTestDouble(Double a, Double b, Double res) {
        Assert.assertEquals(calculator.mult(a, b), res, DELTA);
    }

}
