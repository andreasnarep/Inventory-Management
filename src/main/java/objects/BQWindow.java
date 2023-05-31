package objects;

import java.util.Map;

public class BQWindow {
    private String windowName;
    private Map<String, Integer> materialNameAndQuantity;

    public BQWindow( String windowName, Map<String, Integer> materialNameAndQuantity) {
        this.windowName = windowName;
        this.materialNameAndQuantity = materialNameAndQuantity;
    }

    public String getWindowName() {
        return windowName;
    }

    public Map<String, Integer> getMaterialNameAndQuantity() {
        return materialNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "{" );

        for (String key : materialNameAndQuantity.keySet())
            sb.append(key + " - " + materialNameAndQuantity.get(key) + ", ");

        sb.append( "}" );
        return "(BQWINDOW) " + windowName + " - " + sb.toString();
    }
}
