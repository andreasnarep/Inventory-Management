package objects;

import java.time.LocalDate;
import java.util.Map;

public class CompletedBQWindow {

    private String windowName;
    private LocalDate date;
    private long quantity;

    public CompletedBQWindow( String windowName, String date, long quantity ) {
        this.windowName = windowName;
        this.date = LocalDate.parse( date );
        this.quantity = quantity;
    }

    public String getWindowName() {
        return windowName;
    }

    @Override
    public String toString() {
        return "(COMPLETEDBQWINDOW) " + windowName + ", Date: " + date.toString() + ", Quantity: " + quantity;
    }
}
