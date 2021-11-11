package selenium_hw_5.util;

import selenium_hw_3.elements.UserLine;
import selenium_hw_3.elements.UserTable;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public final class UserTableUtil {

    public static List<WebElement> getFromUserTableByLambda(
            UserTable userTable,
            Function<UserLine, WebElement> function)
    {
        return userTable
                .getUsersInTable()
                .stream()
                .map(function)
                .collect(Collectors.toList());
    }
}
