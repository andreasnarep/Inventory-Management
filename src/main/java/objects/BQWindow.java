package objects;

import java.util.Map;

public class BQWindow {
    private String windowName;
    private Map<String, Long> windowNameAndQuantity;

    public BQWindow(String windowName, Map<String, Long> windowNameAndQuantity) {
        this.windowName = windowName;
        this.windowNameAndQuantity = windowNameAndQuantity;
    }

    public String getWindowName() {
        return windowName;
    }

    public Map<String, Long> getWindowNameAndQuantity() {
        return windowNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : windowNameAndQuantity.keySet())
            sb.append(key + " - " + windowNameAndQuantity.get(key) + "\n");

        return windowName + "\n" + sb.toString();
    }
}
