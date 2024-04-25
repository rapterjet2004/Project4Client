import java.io.Serializable;

public class User implements Serializable {
    public String userName;
    public long userId;
    User(String name, long id) {
        userName = name;
        userId = id;
    }
}
