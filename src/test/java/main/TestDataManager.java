package main;

import objects.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataManager {

    @Before
    public void setUp() {
        setBQDoors();
        setBQWindows();
        setPoloDoors();
        setInventory();
        DataManager.getOrders().clear();
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
    public void addingOnePoloDoor() {
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
    public void addingOnePoloDoorWithBigQuantity() {
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
    public void addingOneBQDoor() {
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
    public void addingOneBQDoorWithBigQuantity() {
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
    public void addingOneBQWindow() {
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
    public void addingOneBQWindowWithBigQuantity() {
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 3 ) );
        DataManager.confirmCompletedBQWindows();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 24, inventory.get( "545x775" ).getQuantity() );
        Assert.assertEquals( 16, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 38, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQWindowsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQWindows().size() );
    }

    @Test
    public void addingNewOrder() {
        Material orderComponent = new Material( "605x1537", 15 );
        DataManager.addOrderComponent( orderComponent );
        Order order = DataManager.addNewOrder();
        Material[] components = order.getComponents();

        Assert.assertEquals( orderComponent, components[0] );
        Assert.assertEquals( 0, DataManager.getOrderSession().size() );
    }

    @Test
    public void addingNewOrderWithBox() {
        Material firstBoxItem = new Material( "asd", 10 );
        Material secondBoxItem = new Material( "bgd", 20 );
        Box box = new Box( "a54wsd", new Material[]{firstBoxItem, secondBoxItem} );

        DataManager.addOrderComponent( box );
        Order order = DataManager.addNewOrder();
        Material[] components = order.getComponents();

        Assert.assertEquals( firstBoxItem, components[0] );
        Assert.assertEquals( secondBoxItem, components[1] );
        Assert.assertEquals( 0, DataManager.getOrderSession().size() );
    }

    @Test
    public void addingNewOrderMixed() {
        Material firstBoxItem = new Material( "asd", 10 );
        Material secondBoxItem = new Material( "bgd", 20 );
        Material orderComponent = new Material( "605x1537", 7 );
        Box box = new Box( "a54wsd", new Material[]{firstBoxItem, secondBoxItem} );

        DataManager.addOrderComponent( box );
        DataManager.addOrderComponent( orderComponent );
        Order order = DataManager.addNewOrder();
        Material[] components = order.getComponents();

        Assert.assertEquals( firstBoxItem, components[0] );
        Assert.assertEquals( secondBoxItem, components[1] );
        Assert.assertEquals( orderComponent, components[2] );
        Assert.assertEquals( 0, DataManager.getOrderSession().size() );
    }

    @Test
    public void addingPoloDoorWithRollback() {
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", LocalDate.now(), 4 ) );
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x2050 1/1", LocalDate.now(), 2 ) );
        DataManager.rollbackCompletedPoloDoor();
        DataManager.confirmCompletedPoloDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 7, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 36, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 34, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedPoloDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedPoloDoors().size() );
    }

    @Test
    public void addingBQWindowWithRollback() {
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 1 ) );
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1290x1000", LocalDate.now(), 1 ) );
        DataManager.rollbackCompletedBQWindow();
        DataManager.confirmCompletedBQWindows();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 28, inventory.get( "545x775" ).getQuantity() );
        Assert.assertEquals( 32, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 46, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQWindowsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQWindows().size() );
    }

    @Test
    public void addingBQDoorWithRollback() {
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", LocalDate.now(), 3 ) );
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1588x1965 1/1", LocalDate.now(), 3 ) );
        DataManager.rollbackCompletedBQDoor();
        DataManager.confirmCompletedBQDoors();
        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 12, inventory.get( "605x1537" ).getQuantity() );
        Assert.assertEquals( 28, inventory.get( "asd" ).getQuantity() );
        Assert.assertEquals( 26, inventory.get( "bgd" ).getQuantity() );
        Assert.assertEquals( 0, DataManager.getCompletedBQDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQDoors().size() );
    }

    @Test
    public void addingMixedDoorsAndWindows() {
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", LocalDate.now(), 2 ) );
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", LocalDate.now(), 4 ) );
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 1 ) );

        DataManager.confirmCompletedPoloDoors();
        DataManager.confirmCompletedBQDoors();
        DataManager.confirmCompletedBQWindows();
        DataManager.createLowerLimitInventory();

        HashMap<String, Material> inventory = DataManager.getInventory();

        Assert.assertEquals( 7 , inventory.get( "605x1537" ).getQuantity());
        Assert.assertEquals( 14 , inventory.get( "asd" ).getQuantity());
        Assert.assertEquals( 6 , inventory.get( "bgd" ).getQuantity());
        Assert.assertEquals( 28 , inventory.get( "545x775" ).getQuantity());
        Assert.assertEquals( 1, DataManager.getLowerLimitInventory().size() );

        Assert.assertEquals( 0, DataManager.getCompletedBQDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQDoors().size() );
        Assert.assertEquals( 0, DataManager.getCompletedBQWindowsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedBQWindows().size() );
        Assert.assertEquals( 0, DataManager.getCompletedPoloDoorsSession().size() );
        Assert.assertEquals( 1, DataManager.getCompletedPoloDoors().size() );
    }

    @Test
    public void addingBQDoorWithDifferentMonth() {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), 2, 1);
        DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", date, 3 ) );

        DataManager.confirmCompletedBQDoors();

        Assert.assertEquals(2, DataManager.getCompletedBQDoors().get(0).getDate().getMonthValue());
    }

    @Test
    public void addingBQWindowWithDifferentMonth() {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", date, 1 ) );

        DataManager.confirmCompletedBQWindows();

        Assert.assertEquals(4, DataManager.getCompletedBQWindows().get(0).getDate().getMonthValue());
    }

    @Test
    public void addingPoloDoorWithDifferentMonth() {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), 10, 1);
        DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", date, 2 ) );

        DataManager.confirmCompletedPoloDoors();

        Assert.assertEquals(10, DataManager.getCompletedPoloDoors().get(0).getDate().getMonthValue());
    }

    //DataManager.addCompletedPoloDoor( new CompletedPoloDoor( "1690x1900 1/1", LocalDate.now(), 2 ) );
    //DataManager.addCompletedBQDoor( new CompletedBQDoor( "1690x1900 1/1", LocalDate.now(), 4 ) );
    //DataManager.addCompletedBQWindow( new CompletedBQWindow( "1390x1000", LocalDate.now(), 1 ) );
}
