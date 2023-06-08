package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DataManager;
import objects.BQWindow;
import objects.Box;
import objects.Material;
import objects.Order;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdersPage implements Initializable {

    private static final Logger logger = Logger.getLogger( PoloDoorsPage.class.getName() );

    @FXML
    private ChoiceBox<String> itemChoice;

    @FXML
    private ListView<String> orders;

    @FXML
    private Button addNewOrder;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private Button confirmOrder;

    @FXML
    private Button glassesChoice;

    @FXML
    private Button boxesChoice;

    @FXML
    private Button otherChoice;

    @FXML
    private ChoiceBox<Order> orderArrivedChoice;

    @FXML
    private Button confirmArrivedOrder;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, String> nameColumn;

    private boolean glassesActive = false;
    private boolean boxesActive = false;
    private boolean otherActive = false;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );

        table.setItems( FXCollections.observableArrayList( DataManager.getOrders() ) );
    }

    @FXML
    public void addNewOrderComponent( ActionEvent event ) {
        if ( glassesActive ) {
            String name = itemChoice.getValue();
            Integer quantityValue = quantity.getValue();
            Material component = new Material( itemChoice.getValue(), quantityValue );

            orders.getItems().add( name + " - " + quantityValue );
            DataManager.addOrderComponent( component );

            logger.log( Level.INFO, "NEW ORDER COMPONENT ADDED: " + component );
        } else if ( boxesActive ) {
            Map<String, Box> boxes = DataManager.getBoxes();
            Box box = boxes.get( itemChoice.getValue() );

            for ( Material component : box.getComponents() ) {
                DataManager.addOrderComponent( component );
            }

            orders.getItems().add( box.getName() + " - " + quantity.getValue() );
            logger.log( Level.INFO, "NEW ORDER COMPONENT ADDED: " + box );
        } else if ( otherActive ) {
            logger.log( Level.INFO, "NEW ORDER COMPONENT ADDED: OTHER" );
        }

        if ( !itemChoice.getItems().isEmpty() ) {
            itemChoice.setValue( itemChoice.getItems().get( 0 ) );
        }

        quantity.getValueFactory().setValue( 1 );
    }

    @FXML
    public void confirmArrivedOrder( ActionEvent event ) {
        logger.log( Level.INFO, "ORDER xxx ARRIVED" );
    }

    @FXML
    public void addNewOrder( ActionEvent event ) {
        if ( orders.getItems().size() != 0 ) {
            Order addedOrder = DataManager.addNewOrder();
            table.getItems().add( addedOrder );
        }

        orders.getItems().clear();
    }

    @FXML
    public void rollbackInsertedOrder( MouseEvent mouseEvent ) {
        try {
            Material removed = DataManager.rollbackOrderComponent();
            orders.getItems().remove( orders.getItems().size() - 1 );
            logger.log( Level.INFO, "ORDER ROLLBACK: " + removed );
        } catch ( NoSuchElementException e ) {
            logger.log( Level.WARNING, "CURRENT ORDER SESSION HOLDS NO DATA." );
        }
    }

    @FXML
    public void glassesChoiceButtonPressed( MouseEvent mouseEvent ) {
        glassesActive = true;
        boxesActive = false;
        otherActive = false;
        List<String> glasses = DataManager.getGlasses();
        itemChoice.setItems( FXCollections.observableArrayList( glasses ) );
        itemChoice.setValue( glasses.get( 0 ) );
    }

    @FXML
    public void boxesChoiceButtonPressed( MouseEvent mouseEvent ) {
        glassesActive = false;
        boxesActive = true;
        otherActive = false;
        Map<String, Box> boxes = DataManager.getBoxes();
        List<String> boxNames = new ArrayList<>( boxes.keySet() );
        itemChoice.setItems( FXCollections.observableArrayList( boxNames ) );
        itemChoice.setValue( boxNames.get( 0 ) );
    }

    @FXML
    public void otherChoiceButtonPressed( MouseEvent mouseEvent ) {
        glassesActive = false;
        boxesActive = false;
        otherActive = true;
    }
}
