import objects.Door;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TestForJSON {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "src/main/resources/data/PoloDoors.json";

        JSONParser jsonParser = new JSONParser();

        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        Door[] doors = new Door[jsonArray.size()];
        int i = 0;

        for (Object object : jsonArray.toArray()) {
            String poloDoorName = (String) ((JSONObject) object).get("name");
            JSONObject materialsJSONObject = (JSONObject) ((JSONObject) object).get("materials");
            Map<String, Long> materialsMap = new HashMap<>();

            for (Object key : materialsJSONObject.keySet()) {
                materialsMap.put((String) key, (Long) materialsJSONObject.get(key));
            }

            //doors[i] = new Door(poloDoorName, materialsMap);
            i++;
        }

        for (Door door : doors)
            System.out.println(door);
    }
}
