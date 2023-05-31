package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class Door {

    private String doorName;

    public Door(String doorName) {
        this.doorName = doorName;
    }

    public String getDoorName() {
        return doorName;
    }
}
