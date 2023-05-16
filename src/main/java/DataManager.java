import gui.controllers.PoloPage;
import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

    private static final Logger logger = Logger.getLogger(PoloPage.class.getName());
    private static final String[] filesToRead = {"BQDoors.json", "BQWindows.json", "Inventory.json",
                                                "Orders.json", "PoloDoors.json"};
    private PoloDoor[] poloDoors;
    private BQDoor[] bqDoors;
    private BQWindow[] bqWindows;
    private Material[] inventory;
    private DataReader dataReader;

    DataManager() {
        this.dataReader = new DataReader();
    }

    public void readData() throws IOException, ParseException {
        for (String file : filesToRead) {
            switch (file) {
                //case "BQDoors.json":
                //    dataReader.read(file, bqDoors);
                case "PoloDoors.json":
                    poloDoors = dataReader.read(file, poloDoors);
                default:
                    //throw new FileNotFoundException();
            }
        }

        for (PoloDoor door : poloDoors)
            System.out.println(door);
    }
}

class DataReader {
    public BQDoor[] read(String filePath, BQDoor[] doors) {
        return null;
    }

    public PoloDoor[] read(String filePath, PoloDoor[] doors) throws IOException, ParseException {
        filePath = "src/main/resources/data/" + filePath;
        JSONParser jsonParser = new JSONParser();

        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        doors = new PoloDoor[jsonArray.size()];
        int i = 0;

        for (Object object : jsonArray.toArray()) {
            String poloDoorName = (String) ((JSONObject) object).get("name");
            JSONObject materialsJSONObject = (JSONObject) ((JSONObject) object).get("materials");
            Map<String, Long> materialsMap = new HashMap<>();

            for (Object key : materialsJSONObject.keySet()) {
                materialsMap.put((String) key, (Long) materialsJSONObject.get(key));
            }

            doors[i] = new PoloDoor(poloDoorName, materialsMap);
            i++;
        }

        return doors;
    }
}
