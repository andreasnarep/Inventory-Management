package main;

import javafx.collections.FXCollections;
import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

    private static String host;
    private static String user;//change accordingly
    private static String password;//change accordingly
    private static String to;//change accordingly
    private static Properties props;

    //juhu
    private static final Logger logger = Logger.getLogger( DataManager.class.getName() );
    private static String[] filesToRead = { "BQDoors.json", "BQWindows.json", "Inventory.json",
            "Orders.json", "PoloDoors.json", "CompletedBQDoors.json",
            "CompletedBQWindows.json", "CompletedPoloDoors.json", "Glasses.json", "Boxes.json" };

    private static HashMap<String, PoloDoor> poloDoors;
    private static HashMap<String, BQDoor> bqDoors;
    private static HashMap<String, BQWindow> bqWindows;
    private static List<String> glasses;
    private static List<String> other; //Things to order
    private static Map<String, Box> boxes;
    private static HashMap<String, Material> inventory;
    private static DataReader dataReader = new DataReader();

    private static DataWriter dataWriter = new DataWriter();
    private static List<Material> lowerLimitInventory = new ArrayList<>();
    private static Map<String, Order> orders = new HashMap<>();
    private static List<Material> orderSession = new ArrayList<>();//When order is confirmed, this is
    //emptied and these components are added to the orders list as an order.
    private static List<CompletedPoloDoor> completedPoloDoors;
    private static List<CompletedPoloDoor> completedPoloDoorsSession = new ArrayList<>(); //When completed doors are confirmed, this is
    //emptied and these doors are added to completedPoloDoors.
    private static List<CompletedBQDoor> completedBQDoors;
    private static List<CompletedBQDoor> completedBQDoorsSession = new ArrayList<>();     //When completed doors are confirmed, this is
    //emptied and these doors are added to completedBQDoors.
    private static List<CompletedBQWindow> completedBQWindows;
    private static List<CompletedBQWindow> completedBQWindowsSession = new ArrayList<>(); //When completed windows are confirmed, this is
    //emptied and these windows are added to completedBQWindows.

    DataManager() {

    }

    public static void start() throws IOException, ParseException {
        try {
            readData();
            logger.log( Level.INFO, "All of  the data read succesfully." );
        } catch ( FileNotFoundException e ) {
            logger.log( Level.WARNING, "File not found: " + e.getMessage() );
        }
        setupMailService();
    }

    private static void setupMailService() {
        host = "smtp.gmail.com";
        user = "andreasnarep2@gmail.com";//change accordingly
        password = "ifslypzmdwrpoktx";//change accordingly
        to = "andreasnarep2@gmail.com";//change accordingly

        props = new Properties();
        props.put( "mail.smtp.host", host );
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.port", "465" );
        props.put( "mail.smtp.ssl.enable", "true" );
    }

    public static void readData() throws IOException, ParseException {

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
                    createLowerLimitInventory();
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
                case "Glasses.json":
                    glasses = dataReader.readGlasses();
                    break;
                case "Boxes.json":
                    boxes = dataReader.readBoxes();
                    break;
                default:
                    throw new FileNotFoundException( file );
            }
        }

        System.out.println( "----------------" );
        for ( String door : bqDoors.keySet() )
            System.out.println( bqDoors.get( door ) );

        System.out.println( "----------------" );
        for ( String material : inventory.keySet() )
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
        for ( String key : orders.keySet() )
            System.out.println( orders.get( key ) );

        System.out.println( "----------------" );
        for ( Material material : lowerLimitInventory )
            System.out.println( material );

        System.out.println( "----------------" );
        for ( String glass : glasses )
            System.out.println( glass );

        System.out.println( "----------------" );
        for ( String key : boxes.keySet() )
            System.out.println( boxes.get( key ) );
    }

    public static void sendOrderByMail( Order order ) {
        Session session = Session.getDefaultInstance( props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( user, password );
                    }
                } );

        //Compose the message
        try {
            MimeMessage message = new MimeMessage( session );
            message.setFrom( new InternetAddress( user ) );
            message.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );
            message.setSubject( "Tellimus - " + order.getName() );

            StringBuilder sb = new StringBuilder();
            sb.append( "Tellimuse nimi - " ).append( order.getName() ).append( "\n" );
            //sb.append("Tellitud asjad:");

            List<Material> slat15mm = new ArrayList<>();
            List<Material> slat10mm = new ArrayList<>();
            List<Material> other = new ArrayList<>();

            for ( Material material : order.getComponents() ) {
                if ( material.getSlat() == null ) {
                    other.add( material );
                } else if ( material.getSlat().equals( "10mm" ) ) {
                    slat10mm.add( material );
                } else if ( material.getSlat().equals( "15mm" ) ) {
                    slat15mm.add( material );
                }
            }

            if ( ! slat15mm.isEmpty() ) {
                sb.append( "\nKlaasid 15mm:" );
                for ( Material material : slat15mm ) {
                    sb.append( "\n\t" ).append( material.getName() ).append( " - " ).append( material.getQuantity() ).append( "tk" );
                }
            }

            if ( ! slat10mm.isEmpty() ) {
                sb.append( "\n\nKlaasid 10mm:" );
                for ( Material material : slat10mm ) {
                    sb.append( "\n\t" ).append( material.getName() ).append( " - " ).append( material.getQuantity() ).append( "tk" );
                }
            }

            if ( ! other.isEmpty() ) {
                sb.append( "\n\nMuu:" );
                for ( Material material : other ) {
                    sb.append( "\n\t" ).append( material.getName() ).append( " - " ).append( material.getQuantity() ).append( "tk" );
                }
            }

            //for (Material material : order.getComponents()) {
            //    sb.append("\n\t").append(material.getName()).append(" - ").append(material.getQuantity()).append("tk");
            //}

            message.setText( sb.toString() );

            //send the message
            Transport.send( message );

            logger.log( Level.INFO, "message sent successfully..." );

        } catch ( MessagingException e ) {
            e.printStackTrace();
        }
    }

    public static void confirmArrivedOrder( Order arrivedOrder ) {
        for ( Material component : arrivedOrder.getComponents() ) {
            Material inventoryItem = inventory.get( component.getName() );
            inventoryItem.addToQuantity( component.getQuantity() );
        }
        orders.remove( arrivedOrder.getName() );
    }

    public static void createLowerLimitInventory() {
        lowerLimitInventory.clear();
        for ( String key : inventory.keySet() ) {
            Material material = inventory.get( key );
            if ( material.isLow() ) {
                lowerLimitInventory.add( material );
            }
        }
    }

    public static void addOrderComponent( Material component ) {
        orderSession.add( component );
    }

    public static void addOrderComponent( Box component ) {
        for ( Material item : component.getComponents() ) {
            addOrderComponent( item );
        }
    }

    public static void addCompletedPoloDoor( CompletedPoloDoor completedPoloDoor ) {
        completedPoloDoorsSession.add( completedPoloDoor );
    }

    public static void addCompletedBQDoor( CompletedBQDoor completedBQDoor ) {
        completedBQDoorsSession.add( completedBQDoor );
    }

    public static void addCompletedBQWindow( CompletedBQWindow completedBQWindow ) {
        completedBQWindowsSession.add( completedBQWindow );
    }

    public static Order addNewOrder() {
        Material[] components = new Material[orderSession.size()];
        components = orderSession.toArray( components );
        String orderName = UUID.randomUUID().toString().substring( 0, 6 );

        Order order = new Order( orderName, LocalDate.now(), components );
        orderSession.clear();
        orders.put( orderName, order );

        logger.log( Level.INFO, "NEW ORDER: " + order );
        //sendOrderByMail(order);
        return order;
    }

    public static void confirmCompletedPoloDoors() {
        if ( completedPoloDoorsSession.size() != 0 ) {
            for ( CompletedPoloDoor completedDoor : completedPoloDoorsSession ) {
                PoloDoor door = poloDoors.get( completedDoor.getDoorName() );
                Map<String, Integer> materialNameAndQuantity = door.getMaterialNameAndQuantity();

                for ( String key : materialNameAndQuantity.keySet() ) {
                    Material inventoryMaterial = inventory.get( key );
                    int toSubtract = materialNameAndQuantity.get( key ) * completedDoor.getQuantity();
                    int subtractFrom = inventoryMaterial.getQuantity();
                    inventoryMaterial.setQuantity( subtractFrom - toSubtract );

                    System.out.println( inventoryMaterial );
                }
            }
            completedPoloDoors.addAll( FXCollections.observableArrayList( completedPoloDoorsSession ) );
            completedPoloDoorsSession.clear();
        }
    }

    public static void confirmCompletedBQDoors() {
        if ( completedBQDoorsSession.size() != 0 ) {
            for ( CompletedBQDoor completedDoor : completedBQDoorsSession ) {
                BQDoor door = bqDoors.get( completedDoor.getDoorName() );
                Map<String, Integer> materialNameAndQuantity = door.getMaterialNameAndQuantity();

                for ( String key : materialNameAndQuantity.keySet() ) {
                    Material inventoryMaterial = inventory.get( key );
                    int toSubtract = materialNameAndQuantity.get( key ) * completedDoor.getQuantity();
                    int subtractFrom = inventoryMaterial.getQuantity();
                    inventoryMaterial.setQuantity( subtractFrom - toSubtract );

                    System.out.println( inventoryMaterial );
                }
            }
            completedBQDoors.addAll( FXCollections.observableArrayList( completedBQDoorsSession ) );
            completedBQDoorsSession.clear();
        }
    }

    public static void confirmCompletedBQWindows() {
        if ( completedBQWindowsSession.size() != 0 ) {
            for ( CompletedBQWindow completedWindow : completedBQWindowsSession ) {
                BQWindow window = bqWindows.get( completedWindow.getWindowName() );
                Map<String, Integer> materialNameAndQuantity = window.getMaterialNameAndQuantity();

                for ( String key : materialNameAndQuantity.keySet() ) {
                    Material inventoryMaterial = inventory.get( key );
                    int toSubtract = materialNameAndQuantity.get( key ) * completedWindow.getQuantity();
                    int subtractFrom = inventoryMaterial.getQuantity();
                    inventoryMaterial.setQuantity( subtractFrom - toSubtract );

                    System.out.println( inventoryMaterial );
                }
            }
            completedBQWindows.addAll( FXCollections.observableArrayList( completedBQWindowsSession ) );
            completedBQWindowsSession.clear();
        }
    }

    public static Material rollbackOrderComponent() {
        if ( orderSession.size() != 0 ) {
            return orderSession.remove( orderSession.size() - 1 );
        }
        throw new NoSuchElementException();
    }

    public static CompletedPoloDoor rollbackCompletedPoloDoor() {
        if ( completedPoloDoorsSession.size() != 0 ) {
            return completedPoloDoorsSession.remove( completedPoloDoorsSession.size() - 1 );
        }
        throw new NoSuchElementException();
    }

    public static CompletedBQDoor rollbackCompletedBQDoor() {
        if ( completedBQDoorsSession.size() != 0 ) {
            return completedBQDoorsSession.remove( completedBQDoorsSession.size() - 1 );
        }
        throw new NoSuchElementException();
    }

    public static CompletedBQWindow rollbackCompletedBQWindow() {
        if ( completedBQWindowsSession.size() != 0 ) {
            return completedBQWindowsSession.remove( completedBQWindowsSession.size() - 1 );
        }
        throw new NoSuchElementException();
    }

    public static void setPoloDoors( HashMap<String, PoloDoor> poloDoors ) {
        DataManager.poloDoors = poloDoors;
    }

    public static void setBqDoors( HashMap<String, BQDoor> bqDoors ) {
        DataManager.bqDoors = bqDoors;
    }

    public static void setBqWindows( HashMap<String, BQWindow> bqWindows ) {
        DataManager.bqWindows = bqWindows;
    }

    public static void setInventory( HashMap<String, Material> inventory ) {
        DataManager.inventory = inventory;
    }

    public static void setOrders( Map<String, Order> orders ) {
        DataManager.orders = orders;
    }

    public static void setLowerLimitInventory( List<Material> lowerLimitInventory ) {
        DataManager.lowerLimitInventory = lowerLimitInventory;
    }

    public static void setCompletedPoloDoors( List<CompletedPoloDoor> completedPoloDoors ) {
        DataManager.completedPoloDoors = completedPoloDoors;
    }

    public static void setCompletedPoloDoorsSession( List<CompletedPoloDoor> completedPoloDoorsSession ) {
        DataManager.completedPoloDoorsSession = completedPoloDoorsSession;
    }

    public static void setCompletedBQDoors( List<CompletedBQDoor> completedBQDoors ) {
        DataManager.completedBQDoors = completedBQDoors;
    }

    public static void setCompletedBQDoorsSession( List<CompletedBQDoor> completedBQDoorsSession ) {
        DataManager.completedBQDoorsSession = completedBQDoorsSession;
    }

    public static void setCompletedBQWindows( List<CompletedBQWindow> completedBQWindows ) {
        DataManager.completedBQWindows = completedBQWindows;
    }

    public static void setCompletedBQWindowsSession( List<CompletedBQWindow> completedBQWindowsSession ) {
        DataManager.completedBQWindowsSession = completedBQWindowsSession;
    }

    public static void writeData() {
        dataWriter.writeData();
    }

    public static List<Material> getLowerLimitInventory() {
        return lowerLimitInventory;
    }

    public static HashMap<String, BQDoor> getBqDoors() {
        return bqDoors;
    }

    public static HashMap<String, PoloDoor> getPoloDoors() {
        return poloDoors;
    }

    public static HashMap<String, BQWindow> getBqWindows() {
        return bqWindows;
    }

    public static HashMap<String, Material> getInventory() {
        return inventory;
    }

    public static List<CompletedPoloDoor> getCompletedPoloDoors() {
        return completedPoloDoors;
    }

    public static List<CompletedBQDoor> getCompletedBQDoors() {
        return completedBQDoors;
    }

    public static List<CompletedBQWindow> getCompletedBQWindows() {
        return completedBQWindows;
    }

    public static List<CompletedPoloDoor> getCompletedPoloDoorsSession() {
        return completedPoloDoorsSession;
    }

    public static List<CompletedBQDoor> getCompletedBQDoorsSession() {
        return completedBQDoorsSession;
    }

    public static List<CompletedBQWindow> getCompletedBQWindowsSession() {
        return completedBQWindowsSession;
    }

    public static Map<String, Order> getOrders() {
        return orders;
    }

    public static List<String> getGlasses() {
        return glasses;
    }

    public static Map<String, Box> getBoxes() {
        return boxes;
    }

    public static List<Material> getOrderSession() {
        return orderSession;
    }
}

class DataWriter {
    protected void writeData() {
        writeCompletedDoorsAndWindows();
        writeInventory();
        writeOrders();
    }

    private void writeOrders() {
        Map<String, Order> orders = DataManager.getOrders();

        JSONArray ordersList = new JSONArray();

        for ( Order order : orders.values() ) {
            JSONObject orderJsonObject = new JSONObject();
            orderJsonObject.put( "name", order.getName() );
            orderJsonObject.put( "date", order.getDate().toString() );

            JSONObject componentsJsonObject = new JSONObject();
            for ( Material component : order.getComponents() ) {
                componentsJsonObject.put( component.getName(), component.getQuantity() );
            }

            orderJsonObject.put( "components", componentsJsonObject );
            ordersList.add( orderJsonObject );
        }

        try ( FileWriter writer = new FileWriter( "src/main/resources/data/TestForJson.json" ) ) {
            writer.write( ordersList.toJSONString().replace( "\\/", "/" ) );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }

    private void writeInventory() {
        HashMap<String, Material> inventory = DataManager.getInventory();

        JSONArray inventoryList = new JSONArray();

        for ( Material item : inventory.values() ) {
            JSONObject itemJsonObject = new JSONObject();
            itemJsonObject.put( "name", item.getName() );
            itemJsonObject.put( "quantity", item.getQuantity() );
            itemJsonObject.put( "lowerLimit", item.getLowerLimit() );
            inventoryList.add( itemJsonObject );
        }

        try ( FileWriter writer = new FileWriter( "src/main/resources/data/TestForJson.json" ) ) {
            writer.write( inventoryList.toJSONString().replace( "\\/", "/" ) );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }

    private void writeCompletedDoorsAndWindows() {
        List<CompletedBQDoor> completedBQDoors = DataManager.getCompletedBQDoors();
        List<CompletedPoloDoor> completedPoloDoors = DataManager.getCompletedPoloDoors();
        List<CompletedBQWindow> completedBQWindows = DataManager.getCompletedBQWindows();

        JSONArray completedBQDoorsList = new JSONArray();
        JSONArray completedPoloDoorsList = new JSONArray();
        JSONArray completedBQWindowsList = new JSONArray();

        for ( CompletedBQDoor door : completedBQDoors ) {
            JSONObject doorJsonObject = new JSONObject();
            doorJsonObject.put( "name", door.getDoorName() );
            doorJsonObject.put( "date", door.getDate().toString() );
            doorJsonObject.put( "quantity", door.getQuantity() );
            completedBQDoorsList.add( doorJsonObject );
        }

        for ( CompletedPoloDoor door : completedPoloDoors ) {
            JSONObject doorJsonObject = new JSONObject();
            doorJsonObject.put( "name", door.getDoorName() );
            doorJsonObject.put( "date", door.getDate().toString() );
            doorJsonObject.put( "quantity", door.getQuantity() );
            completedPoloDoorsList.add( doorJsonObject );
        }

        for ( CompletedBQWindow window : completedBQWindows ) {
            JSONObject doorJsonObject = new JSONObject();
            doorJsonObject.put( "name", window.getWindowName() );
            doorJsonObject.put( "date", window.getDate().toString() );
            doorJsonObject.put( "quantity", window.getQuantity() );
            completedBQWindowsList.add( doorJsonObject );
        }

        try ( FileWriter writer = new FileWriter( "src/main/resources/data/CompletedBQDoors.json" ) ) {
            writer.write( completedBQDoorsList.toJSONString().replace( "\\/", "/" ) );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }

        try ( FileWriter writer = new FileWriter( "src/main/resources/data/CompletedPoloDoors.json" ) ) { //change to completed polo doors file
            writer.write( completedPoloDoorsList.toJSONString().replace( "\\/", "/" ) );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }

        try ( FileWriter writer = new FileWriter( "src/main/resources/data/CompletedBQWindows.json" ) ) { //change to completed bq windows file
            writer.write( completedBQWindowsList.toJSONString().replace( "\\/", "/" ) );
            writer.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
}

class DataReader {
    protected HashMap<String, BQDoor> readBQDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQDoors.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        HashMap<String, BQDoor> doors = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            doors.put( doorName, new BQDoor( doorName, materialsMap ) );
        }

        fileReader.close();
        return doors;
    }

    protected HashMap<String, PoloDoor> readPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/PoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        HashMap<String, PoloDoor> doors = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            doors.put( doorName, new PoloDoor( doorName, materialsMap ) );
        }

        fileReader.close();
        return doors;
    }

    protected HashMap<String, BQWindow> readBQWindows() throws IOException, ParseException {
        String filePath = "src/main/resources/data/BQWindows.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        HashMap<String, BQWindow> windows = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "materials" );
            Map<String, Integer> materialsMap = new HashMap<>();

            for ( Object key : materialsJSONObject.keySet() ) {
                Long quantity = (Long) materialsJSONObject.get( key );
                materialsMap.put( (String) key, quantity.intValue() );
            }

            windows.put( windowName, new BQWindow( windowName, materialsMap ) );
        }

        fileReader.close();
        return windows;
    }

    protected HashMap<String, Material> readInventory() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Inventory.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( filePath );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        HashMap<String, Material> inventory = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String materialName = (String) ( (JSONObject) object ).get( "name" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );
            Long lowerLimit = (Long) ( (JSONObject) object ).get( "lowerLimit" );
            String slat = (String) ( (JSONObject) object ).get( "slat" );

            System.out.println( slat );

            inventory.put( materialName, new Material( materialName, quantity.intValue(), lowerLimit.intValue(), slat ) );
        }

        fileReader.close();
        return inventory;
    }

    protected List<CompletedPoloDoor> readCompletedPoloDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedPoloDoors.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        List<CompletedPoloDoor> doors = new ArrayList<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object ).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors.add( new CompletedPoloDoor( doorName, date, quantity.intValue() ) );
        }

        fileReader.close();
        return doors;
    }

    protected List<CompletedBQDoor> readCompletedBQDoors() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedBQDoors.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        List<CompletedBQDoor> doors = new ArrayList<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String doorName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object ).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );
            doors.add( new CompletedBQDoor( doorName, date, quantity.intValue() ) );
        }

        fileReader.close();
        return doors;
    }

    protected List<CompletedBQWindow> readCompletedBQWindows() throws IOException, ParseException {
        String filePath = "src/main/resources/data/CompletedBQWindows.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        List<CompletedBQWindow> doors = new ArrayList<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String windowName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object ).get( "date" );
            Long quantity = (Long) ( (JSONObject) object ).get( "quantity" );

            doors.add( new CompletedBQWindow( windowName, date, quantity.intValue() ) );
        }

        fileReader.close();
        return doors;
    }

    protected Map<String, Order> readOrders() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Orders.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        Map<String, Order> orders = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String orderName = (String) ( (JSONObject) object ).get( "name" );
            String date = (String) ( (JSONObject) object ).get( "date" );

            JSONObject componentsJSONObject = (JSONObject) ( (JSONObject) object ).get( "components" );
            Material[] components = new Material[componentsJSONObject.keySet().size()];
            int i = 0;

            for ( Object key : componentsJSONObject.keySet() ) {
                Long val = (Long) componentsJSONObject.get( key );
                components[i] = new Material( (String) key, val.intValue() );
                i++;
            }

            orders.put( orderName, new Order( orderName, LocalDate.parse( date ), components ) );
        }

        fileReader.close();
        return orders;
    }

    public List<String> readGlasses() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Glasses.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new ArrayList<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        List<String> glasses = new ArrayList<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String glassName = (String) ( (JSONObject) object ).get( "name" );
            glasses.add( glassName );
        }

        fileReader.close();
        return glasses;
    }

    public Map<String, Box> readBoxes() throws IOException, ParseException {
        String filePath = "src/main/resources/data/Boxes.json";
        JSONParser jsonParser = new JSONParser();

        File file = new File( filePath );

        if ( file.length() == 0 ) {
            return new HashMap<>();
        }

        FileReader fileReader = new FileReader( file );
        JSONArray jsonArray = (JSONArray) jsonParser.parse( fileReader );
        Map<String, Box> boxes = new HashMap<>( jsonArray.size() );

        for ( Object object : jsonArray.toArray() ) {
            String boxName = (String) ( (JSONObject) object ).get( "name" );

            JSONObject materialsJSONObject = (JSONObject) ( (JSONObject) object ).get( "components" );
            Material[] components = new Material[materialsJSONObject.keySet().size()];
            int i = 0;

            for ( Object key : materialsJSONObject.keySet() ) {
                Long val = (Long) materialsJSONObject.get( key );
                components[i] = new Material( (String) key, val.intValue() );
                i++;
            }

            Box box = new Box( boxName, components );
            boxes.put( boxName, box );
        }

        fileReader.close();
        return boxes;
    }
}
