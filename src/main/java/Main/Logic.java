package Main;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {

    private static final Logger logger = Logger.getLogger( Logic.class.getName() );
    private static DataManager dataManager;

    protected static void start() throws IOException, ParseException {
        dataManager = new DataManager();
        dataManager.readData();
        logger.log( Level.INFO, "All of  the data read succesfully." );
    }

    public static DataManager getDataManager() {
        return dataManager;
    }
}
