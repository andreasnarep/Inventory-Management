import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Logic {
    private DataManager dataManager;

    Logic() throws IOException, ParseException {
        dataManager = new DataManager();
        dataManager.readData();
    }
}
