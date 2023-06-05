package main;

import objects.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TestDataManager {

    @Before
    public void setUp() {
        setBQDoors();
        setBQWindows();
        setPoloDoors();
        setInventory();
        DataManager.setCompletedBQDoors( new ArrayList<>() );
        DataManager.setCompletedPoloDoors( new ArrayList<>() );
        DataManager.setCompletedBQWindows( new ArrayList<>() );
        DataManager.createLowerLimitInventory();
    }

    private void setInventory() {
        HashMap<String, Material> inventory = new HashMap<>();
        String firstName = "605x1537";
        String secondName = "asd";
        String thirdName = "bgd";
        String fourthName = "545x775";

        inventory.put( firstName, new Material( firstName, 15, 5 ) );
        inventory.put( secondName, new Material( secondName, 40, 8 ) );
        inventory.put( thirdName, new Material( thirdName, 50, 10 ) );
        inventory.put( fourthName, new Material( fourthName, 30, 7 ) );

        DataManager.setInventory( inventory );

    }

    private void setPoloDoors() {
        HashMap<String, PoloDoor> doors = new HashMap<>();

        String firstDoorName = "1690x1900 1/1";
        HashMap<String, Integer> materialNameAndQuantity1 = new HashMap<>();
        materialNameAndQuantity1.put( "605x1537", 2 );
        materialNameAndQuantity1.put( "asd", 1 );
        materialNameAndQuantity1.put( "bgd", 4 );

        String secondDoorName = "1690x2050 1/1";
        HashMap<String, Integer> materialNameAndQuantity2 = new HashMap<>();
        materialNameAndQuantity2.put( "605x1685", 2 );
        materialNameAndQuantity2.put( "asd", 4 );

        doors.put( firstDoorName, new PoloDoor( firstDoorName, materialNameAndQuantity1 ) );
        doors.put( secondDoorName, new PoloDoor( secondDoorName, materialNameAndQuantity2 ) );
        DataManager.setPoloDoors( doors );

    }

    private void setBQWindows() {
        HashMap<String, BQWindow> windows = new HashMap<>();

        String firstWindowName = "1390x1000";
        HashMap<String, Integer> materialNameAndQuantity1 = new HashMap<>();
        materialNameAndQuantity1.put( "545x775", 2 );
        materialNameAndQuantity1.put( "asd", 8 );
        materialNameAndQuantity1.put( "bgd", 4 );

        String secondWindowName = "1290x1000";
        HashMap<String, Integer> materialNameAndQuantity2 = new HashMap<>();
        materialNameAndQuantity2.put( "495x775", 2 );
        materialNameAndQuantity2.put( "asd", 5 );

        windows.put( firstWindowName, new BQWindow( firstWindowName, materialNameAndQuantity1 ) );
        windows.put( secondWindowName, new BQWindow( secondWindowName, materialNameAndQuantity2 ) );
        DataManager.setBqWindows( windows );

    }

    private void setBQDoors() {
        HashMap<String, BQDoor> doors = new HashMap<>();

        String firstDoorName = "1690x1900 1/1";
        HashMap<String, Integer> materialNameAndQuantity1 = new HashMap<>();
        materialNameAndQuantity1.put( "605x1537", 1 );
        materialNameAndQuantity1.put( "asd", 4 );
        materialNameAndQuantity1.put( "bgd", 8 );

        String secondDoorName = "1588x1965 1/1";
        HashMap<String, Integer> materialNameAndQuantity2 = new HashMap<>();
        materialNameAndQuantity2.put( "605x1685", 2 );
        materialNameAndQuantity2.put( "bgd", 1 );

        doors.put( firstDoorName, new BQDoor( firstDoorName, materialNameAndQuantity1 ) );
        doors.put( secondDoorName, new BQDoor( secondDoorName, materialNameAndQuantity2 ) );
        DataManager.setBqDoors( doors );
    }

    @Test
    public void testAddingOnePoloDoor() {
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", LocalDate.now(), 1 ) );
        DataManager.confirmCompletedPoloDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 13, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 39, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 46, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedPoloDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedPoloDoors().size() );
    }

    @Test
    public void testAddingOnePoloDoorWithBigQuantity() {
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", LocalDate.now(), 4 ) );
        DataManager.confirmCompletedPoloDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 7, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 36, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 34, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedPoloDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedPoloDoors().size() );
    }

    @Test
    public void testAddingOneBQDoor() {
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", LocalDate.now(), 1 ) );
        DataManager.confirmCompletedBQDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 14, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 36, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 42, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQDoors().size() );
    }

    @Test
    public void testAddingOneBQDoorWithBigQuantity() {
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", LocalDate.now(), 3 ) );
        DataManager.confirmCompletedBQDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 12, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 28, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 26, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQDoors().size() );
    }

    @Test
    public void testAddingOneBQWindow() {
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 1 ) );
        DataManager.confirmCompletedBQWindows();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 28, inventory.get( "545x775" ).getQuantity() );
        Assert.assertEquals( 32, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 46, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQWindowsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQWindows().size() );
    }

    @Test
    public void testAddingOneBQWindowWithBigQuantity() {
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 3 ) );
        DataManager.confirmCompletedBQWindows();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 24, inventory.get( "545x775" ).getQuantity() );
        Assert.assertEquals( 16, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 38, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQWindowsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQWindows().size() );
    }
}
