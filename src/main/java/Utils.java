import java.util.UUID;

public class Utils {

    public long generateUID() {
        return UUID.randomUUID().getLeastSignificantBits();
    }
}
