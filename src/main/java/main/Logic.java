package main;

import objects.CompletedBQDoor;
import objects.CompletedBQWindow;
import objects.CompletedPoloDoor;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {

    private static final Logger logger = Logger.getLogger( Logic.class.getName() );
    private static DataManager dataManager;

    public static void start() throws IOException, ParseException {
        dataManager = new DataManager();

        try {
            dataManager.readData();
            logger.log( Level.INFO, "All of  the data read succesfully." );
        } catch ( FileNotFoundException e) {
            logger.log( Level.WARNING, "File not found: " + e.getMessage() );
        }
    }

    public static void addCompletedPoloDoor( CompletedPoloDoor completedPoloDoor ) {

    }

    public static void addCompletedBQDoor( CompletedBQDoor completedBQDoor ) {

    }

    public static void addCompletedBQWindow( CompletedBQWindow completedBQWindow ) {

    }

    public static void confirmCompletedPoloDoors() {

    }

    public static void confirmCompletedBQDoors() {

    }

    public static void confirmCompletedBQWindows() {

    }

}
