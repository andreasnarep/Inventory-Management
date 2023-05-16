package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PoloPage {

    private static final Logger logger = Logger.getLogger(PoloPage.class.getName());
    @FXML
    public void insertButtonPressed(ActionEvent event) {
        logger.log(Level.INFO, "DATA INSERTED: ");
    }

    @FXML
    public void confirmInsertedData(ActionEvent event) {
        logger.log(Level.INFO, "INSERTED DATA CONFIRMED");
    }

    @FXML
    public void rollbackInsertedData(ActionEvent event) {
        logger.log(Level.INFO, "DATA ROLLBACK");
    }
}
