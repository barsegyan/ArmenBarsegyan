package selenium_hw_1;
import com.epam.tat.module4.Calculator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Calculator calculator = new Calculator();
        System.out.println(calculator.sum(Long.MAX_VALUE - 1, 2));
        System.out.println("1.0 * 0.014 = " + calculator.mult(1.0, 0.014));
        System.out.println(calculator.div(0.0, 0.0));
        System.out.println( "Hello World!" );
    }
}
