package objects;

import java.util.Map;

public class BQDoor extends Door{
    public BQDoor(String doorName, Map<String, Long> materialNameAndQuantity) {
        super(doorName, materialNameAndQuantity);
    }
}
