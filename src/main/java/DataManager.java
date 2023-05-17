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

    private static final Logger logger = Logger.getLogger( PoloPage.class.getName() );
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
        for ( String file : filesToRead ) {
            switch ( file ) {
                case "BQDoors.json":
                    bqDoors = dataReader.read( file, bqDoors );
                    break;
                case "PoloDoors.json":
                    poloDoors = dataReader.read( file, poloDoors );
                    break;
                case "BQWindows.json":
                    bqWindows = dataReader.read( file, bqWindows );
                    break;
                case "Inventory.json":
                    inventory = dataReader.read( file, inventory );
                    break;
                default: //TODO Throw error once all of the cases have been covered.
                    System.out.println( "default" );
                    //throw new FileNotFoundException();
            }
        }


        for ( PoloDoor door : poloDoors )
            System.out.println( door );

        System.out.println( "----------------" );
        for ( BQDoor door : bqDoors )
            System.out.println( door );

        System.out.println( "----------------" );
        for ( BQWindow window : bqWindows )
            System.out.println( window );

        System.out.println( "----------------" );
        for ( Material material : inventory )
            System.out.println( material );
    }
}

class DataReader {
    public BQDoor[] read( String filePath, BQDoor[] doors ) throws IOException, ParseException {
        filePath = "src/main/resources/data/" + filePath;
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        doors = new BQDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Long> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                materialsMap.put( (String) key, (Long) materialsJSONObject.get( key ) );
            }

            doors[i] = new BQDoor( doorName, materialsMap );
            i++;
        }

        fileReader.close();
        return doors;
    }

    public PoloDoor[] read( String filePath, PoloDoor[] doors ) throws IOException, ParseException {
        filePath = "src/main/resources/data/" + filePath;
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        doors = new PoloDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Long> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                materialsMap.put( (String) key, (Long) materialsJSONObject.get( key ) );
            }

            doors[i] = new PoloDoor( doorName, materialsMap );
            i++;
        }

        fileReader.close();
        return doors;
    }

    public BQWindow[] read( String filePath, BQWindow[] windows ) throws IOException, ParseException {
        filePath = "src/main/resources/data/" + filePath;
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        windows = new BQWindow[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Long> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                materialsMap.put( (String) key, (Long) materialsJSONObject.get( key ) );
            }

            windows[i] = new BQWindow( windowName, materialsMap );
            i++;
        }

        fileReader.close();
        return windows;
    }

    public Material[] read( String filePath, Material[] inventory ) throws IOException, ParseException {
        filePath = "src/main/resources/data/" + filePath;
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        inventory = new Material[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );
            Long lowerLimit = (Long) ( (JSONObject) object ).get( "lowerLimit" );

            inventory[i] = new Material( windowName, quantity, lowerLimit );
            i++;
        }

        fileReader.close();
        return inventory;
    }
}
