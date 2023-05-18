package objects;

import java.util.Map;

public class BQDoor extends Door{

    private Map<String, Long> materialNameAndQuantity;

    public BQDoor( String doorName, Map<String, Long> materialNameAndQuantity) {
        super(doorName);
        this.materialNameAndQuantity = materialNameAndQuantity;
    }

    public Map<String, Long> getMaterialNameAndQuantity() {
        return materialNameAndQuantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "{" );

        for (String key : materialNameAndQuantity.keySet())
            sb.append(key + " - " + materialNameAndQuantity.get(key) + ", ");

        sb.append( "}" );
        return "(BQDOOR) " + super.getDoorName() + " - " + sb.toString();
    }
}
