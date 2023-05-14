import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.*;

public class TestForJSON {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "src/main/resources/data/PoloDoors.json";

        JSONParser jsonParser = new JSONParser();

        JSONArray jsonObject = (JSONArray) jsonParser.parse(new FileReader(filePath));

        System.out.println(jsonObject.get(0));
        System.out.println(jsonObject.get(1));
        //System.out.println(jsonArray);
    }
}
