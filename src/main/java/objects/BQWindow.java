package objects;

import java.util.Map;

public class BQWindow {
    private String windowName;
    private Map<String, Long> materialNameAndQuantity;

    public BQWindow(String windowName, Map<String, Long> materialNameAndQuantity) {
        this.windowName = windowName;
        this.materialNameAndQuantity = materialNameAndQuantity;
    }

    public String getWindowName() {
        return windowName;
    }

    public Map<String, Long> getMaterialNameAndQuantity() {
        return materialNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : materialNameAndQuantity.keySet())
            sb.append(key + " - " + materialNameAndQuantity.get(key) + "\n");

        return windowName + "\n" + sb.toString();
    }
}
