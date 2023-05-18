package gui.controllers;

import Main.DataManager;
import Main.Logic;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdersPage {

    private static final Logger logger = Logger.getLogger(PoloPage.class.getName());

    @FXML
    private ListView listView;

    @FXML
    public void addNewOrderComponent( ActionEvent event) {
        ObservableList<String> items = listView.getItems();
        String name = "605x1537";
        int quantity = 3;
        items.add( name + " - " + quantity );
        listView.setItems( items );
        logger.log(Level.INFO, "NEW ORDER COMPONENT ADDED: " + name + " - " + quantity);
    }

    @FXML
    public void confirmArrivedOrder(ActionEvent event) {
        logger.log(Level.INFO, "ORDER xxx ARRIVED");
    }

    @FXML
    public void addNewOrder( ActionEvent event ) {
    }
}
