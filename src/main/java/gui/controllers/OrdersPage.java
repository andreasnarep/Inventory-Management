package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdersPage {

    private static final Logger logger = Logger.getLogger(PoloPage.class.getName());

    @FXML
    public void addNewOrder(ActionEvent event) {
        logger.log(Level.INFO, "NEW ORDER ADDED: ");
    }

    @FXML
    public void confirmArrivedOrder(ActionEvent event) {
        logger.log(Level.INFO, "ORDER xxx ARRIVED");
    }
}
