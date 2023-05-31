package main;

import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DataManager {

    private static final Logger logger = Logger.getLogger( DataManager.class.getName() );
    private static String[] filesToRead = {"BQDoors.json", "BQWindows.json", "Inventory.json",
            "Orders.json", "PoloDoors.json", "CompletedBQDoors.json",
            "CompletedBQWindows.json", "CompletedPoloDoors.json"};

    private static PoloDoor[] poloDoors;
    private static BQDoor[] bqDoors;
    private static BQWindow[] bqWindows;
    private static Material[] inventory;
    private static CompletedPoloDoor[] completedPoloDoors; //TODO Convert to List.
    private static List<CompletedPoloDoor> completedPoloDoorsSession; //When completed doors are confirmed, this is
                                                                      //emptied and these doors are added to completedPoloDoors.
    private static CompletedBQDoor[] completedBQDoors; //TODO Convert to List.
    private static List<CompletedBQDoor> completedBQDoorsSession;     //When completed doors are confirmed, this is
                                                                      //emptied and these doors are added to completedBQDoors.
    private static CompletedBQWindow[] completedBQWindows; //TODO Convert to List.
    private static List<CompletedBQWindow> completedBQWindowsSession; //When completed windows are confirmed, this is
                                                                      //emptied and these windows are added to completedBQWindows.
    private static Order[] orders;
    private DataReader dataReader;

    DataManager() {
        this.dataReader = new DataReader();
        completedBQDoorsSession = new ArrayList<>();
        completedBQWindowsSession = new ArrayList<>();
        completedPoloDoorsSession = new ArrayList<>();
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
                case "CompletedBQDoors.json":
                    completedBQDoors = dataReader.readCompletedBQDoors();
                    break;
                case "CompletedBQWindows.json":
                    completedBQWindows = dataReader.readCompletedBQWindows();
                    break;
                case "Orders.json":
                    orders = dataReader.readOrders();
                    break;
                default:
                    throw new FileNotFoundException(file);
            }
        }

        System.out.println( "----------------" );
        for ( BQDoor door : bqDoors )
            System.out.println( door );

        System.out.println( "----------------" );
        for ( Material material : inventory )
            System.out.println( material );

        System.out.println( "----------------" );
        for ( CompletedPoloDoor door : completedPoloDoors )
            System.out.println( door );

        System.out.println( "----------------" );
        for ( CompletedBQDoor door : completedBQDoors )
            System.out.println( door );

        System.out.println( "----------------" );
        for ( CompletedBQWindow window : completedBQWindows )
            System.out.println( window );

        System.out.println( "----------------" );
        for ( Order order : orders )
            System.out.println( order );
    }

    public static BQDoor[] getBqDoors() {
        return bqDoors;
    }

    public static PoloDoor[] getPoloDoors() {
        return poloDoors;
    }

    public static BQWindow[] getBqWindows() {
        return bqWindows;
    }

    public static Material[] getInventory() {
        return inventory;
    }

    public static CompletedPoloDoor[] getCompletedPoloDoors() {
        return completedPoloDoors;
    }

    public static CompletedBQDoor[] getCompletedBQDoors() {
        return completedBQDoors;
    }

    public static CompletedBQWindow[] getCompletedBQWindows() {
        return completedBQWindows;
    }

    public static Order[] getOrders() {
        return orders;
    }
}

class DataReader {
    protected BQDoor[] readBQDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        BQDoor[] doors = new BQDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            doors[i] = new BQDoor( doorName, materialsMap );
            i++;
        }

        fileReader.close();
        return doors;
    }

    protected PoloDoor[] readPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/PoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        PoloDoor[] doors = new PoloDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            doors[i] = new PoloDoor( doorName, materialsMap );
            i++;
        }

        fileReader.close();
        return doors;
    }

    protected BQWindow[] readBQWindows() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQWindows.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        BQWindow[] windows = new BQWindow[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            windows[i] = new BQWindow( windowName, materialsMap );
            i++;
        }

        fileReader.close();
        return windows;
    }

    protected Material[] readInventory() throws IOException, ParseException {
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

            inventory[i] = new Material( windowName, quantity.intValue(), lowerLimit.intValue() );
            i++;
        }

        fileReader.close();
        return inventory;
    }

    protected CompletedPoloDoor[] readCompletedPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedPoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        CompletedPoloDoor[] doors = new CompletedPoloDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors[i] = new CompletedPoloDoor( doorName, date, quantity.intValue() );
            i++;
        }

        fileReader.close();
        return doors;
    }

    protected CompletedBQDoor[] readCompletedBQDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedBQDoors.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        CompletedBQDoor[] doors = new CompletedBQDoor[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors[i] = new CompletedBQDoor( doorName, date, quantity.intValue() );
            i++;
        }

        fileReader.close();
        return doors;
    }

    protected CompletedBQWindow[] readCompletedBQWindows() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedBQWindows.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        CompletedBQWindow[] doors = new CompletedBQWindow[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors[i] = new CompletedBQWindow( windowName, date, quantity.intValue() );
            i++;
        }

        fileReader.close();
        return doors;
    }

    protected Order[] readOrders() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Orders.json";
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        Order[] orders = new Order[jsonArray.size()];
        int i = 0;

        for ( Object object : jsonArray.toArray() ) {
            String orderName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "components" );
            Material[] components = new Material[materialsJSONObject.keySet().size()];
            int j = 0;

            for ( Object key : materialsJSONObject.keySet() ) {
                Long val = (Long) materialsJSONObject.get( key );
                components[j] = new Material( (String) key, val.intValue() );
                j++;
            }

            orders[i] = new Order( orderName, date, quantity.intValue(),  components);
            i++;
        }

        fileReader.close();
        return orders;
    }
}
