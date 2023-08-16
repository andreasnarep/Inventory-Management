import objects.Door;
import objects.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestForJSON {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "src/main/resources/data/TestForJson.json";

        //"name": "605x1537",
         //       "quantity": 3,
          //      "lowerLimit": 5
        Material material = new Material( "605x1537", 3, 5 );
        Material material2 = new Material( "asd", 50, 10 );

        System.out.println(material.getName());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "name", material.getName() );
        jsonObject.put( "quantity", material.getQuantity() );
        jsonObject.put( "lowerLimit", material.getLowerLimit() );

        JSONArray jsonArray = new JSONArray();
        jsonArray.add( jsonObject );



        jsonObject = new JSONObject();

        //JSONObject jsonObject2 = new JSONObject();
        jsonObject.put( "name", material2.getName() );
        jsonObject.put( "quantity", material2.getQuantity() );
        jsonObject.put( "lowerLimit", material2.getLowerLimit() );

        jsonArray.add( jsonObject );

        try (FileWriter writer = new FileWriter( filePath )) {
            writer.write( jsonArray.toJSONString().replace( "\\\\" , "") );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println(e);
        }

        LocalDate date = LocalDate.now();

        System.out.println(date);

    }
}
