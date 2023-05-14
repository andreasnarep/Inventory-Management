package objects;

import java.util.Map;

public class PoloDoor {

    private String name;
    private Map<String, Integer> materialNameAndQuantity;

    PoloDoor(String name, Map<String, Integer> materialNameAndQuantity) {
        this.name = name;
        this.materialNameAndQuantity = materialNameAndQuantity;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getMaterialNameAndQuantity() {
        return materialNameAndQuantity;
    }
}
