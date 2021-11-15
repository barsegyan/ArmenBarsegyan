package selenium_hw_3.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter(AccessLevel.NONE)
public class UserData {
    private int number;
    private String user;
    private String description;
    final int userDataMaxSize = 3;
    final int userDataFirstElementNumber = 0;
    final int userDataSecondElementNumber = 1;
    final int userDataThirdElementNumber = 2;

    public UserData(List<String> userData) {
        if (userData.size() != userDataMaxSize) {
            throw new IllegalArgumentException("3 args are required, but found: " + userData.size());
        }
        this.number = Integer.parseInt(userData.get(userDataFirstElementNumber));
        this.user = userData.get(userDataSecondElementNumber);
        this.description = userData.get(userDataThirdElementNumber);
    }
}
