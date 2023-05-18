package Main;

import gui.controllers.PoloPage;
import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DataManager {

    private static final Logger logger = Logger.getLogger( PoloPage.class.getName() );
    private static final String[] filesToRead = {"BQDoors.json", "BQWindows.json", "Inventory.json",
            "Orders.json", "PoloDoors.json", "CompletedBQDoors.json",
            "CompletedBQWindows.json", "CompletedPoloDoors.json"};
    private PoloDoor[] poloDoors;
    private BQDoor[] bqDoors;
    private BQWindow[] bqWindows;
    private Material[] inventory;
    private CompletedPoloDoor[] completedPoloDoors;
    private CompletedBQDoor[] completedBQDoors;
    private CompletedBQWindow[] completedBQWindows;
    private DataReader dataReader;

    DataManager() {
        this.dataReader = new DataReader();
    }

    public void readData() throws IOException, ParseException {
        for ( String file : filesToRead ) {
            switch ( file ) {
                case "BQDoors.json":
                    bqDoors = dataReader.readBQDoors();
                    break;
                case "PoloDoors.json":
                    poloDoors = dataReader.readPoloDoors();
                    break;
                case "BQWindows.json":
                    bqWindows = dataReader.readBQWindows();
                    break;
                case "Inventory.json":
                    inventory = dataReader.readInventory();
                    break;
                case "CompletedPoloDoors.json":
                    completedPoloDoors = dataReader.readCompletedPoloDoors();
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

        System.out.println( "----------------" );
        for ( CompletedPoloDoor door : completedPoloDoors )
            System.out.println( door );
    }
}

class DataReader {
    public BQDoor[] readBQDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        BQDoor[] doors = new BQDoor[jsonArray.size()];
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

    public PoloDoor[] readPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/PoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        PoloDoor[] doors = new PoloDoor[jsonArray.size()];
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

    public BQWindow[] readBQWindows() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQWindows.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        BQWindow[] windows = new BQWindow[jsonArray.size()];
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

    public Material[] readInventory() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Inventory.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        Material[] inventory = new Material[jsonArray.size()];
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

    public CompletedPoloDoor[] readCompletedPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedPoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        CompletedPoloDoor[] doors = new CompletedPoloDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors[i] = new CompletedPoloDoor( windowName, date, quantity );
            i++;
        }

        fileReader.close();
        return doors;
    }
}
