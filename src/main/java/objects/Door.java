package objects;

import java.util.Map;

public class Door {

    private String doorName;
    private Map<String, Long> materialNameAndQuantity;

    public Door(String doorName, Map<String, Long> materialNameAndQuantity) {
        this.doorName = doorName;
        this.materialNameAndQuantity = materialNameAndQuantity;
    }

    public String getDoorName() {
        return doorName;
    }

    public Map<String, Long> getMaterialNameAndQuantity() {
        return materialNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : materialNameAndQuantity.keySet())
            sb.append(key + " - " + materialNameAndQuantity.get(key) + "\n");

        return doorName + "\n" + sb.toString();
    }
}
