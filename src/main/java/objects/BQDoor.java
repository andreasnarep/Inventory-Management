package objects;

import java.util.Map;

public class BQDoor extends Door{

    private Map<String, Integer> materialNameAndQuantity;

    public BQDoor( String doorName, Map<String, Integer> materialNameAndQuantity) {
        super(doorName);
        this.materialNameAndQuantity = materialNameAndQuantity;
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
        return "(BQDOOR) " + super.getDoorName() + " - " + sb.toString();
    }
}
