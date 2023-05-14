package objects;

import java.util.Map;

public class Door {

    private String doorName;
    private Map<String, Long> doorNameAndQuantity;

    public Door(String doorName, Map<String, Long> doorNameAndQuantity) {
        this.doorName = doorName;
        this.doorNameAndQuantity = doorNameAndQuantity;
    }

    public String getDoorName() {
        return doorName;
    }

    public Map<String, Long> getDoorNameAndQuantity() {
        return doorNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : doorNameAndQuantity.keySet())
            sb.append(key + " - " + doorNameAndQuantity.get(key) + "\n");

        return doorName + "\n" + sb.toString();
    }
}
