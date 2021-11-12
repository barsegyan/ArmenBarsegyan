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

    public UserData(List<String> userData) {
        if (userData.size() != 3) {
            throw new IllegalArgumentException("3 args are required, but found: " + userData.size());
        }
        this.number = Integer.parseInt(userData.get(0));
        this.user = userData.get(1);
        this.description = userData.get(2);
    }
}
