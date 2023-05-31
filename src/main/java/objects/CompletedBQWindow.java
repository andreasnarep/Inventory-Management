package objects;

import java.time.LocalDate;

public class CompletedBQWindow {

    private String windowName;
    private LocalDate date;
    private int quantity;

    public CompletedBQWindow( String windowName, String date, Integer quantity ) {
        this.windowName = windowName;
        this.date = LocalDate.parse( date );
        this.quantity = quantity;
    }

    public CompletedBQWindow( String windowName, LocalDate date, Integer quantity ) {
        this.windowName = windowName;
        this.date = date;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getWindowName() {
        return windowName;
    }

    @Override
    public String toString() {
        return "(COMPLETEDBQWINDOW) " + windowName + ", Date: " + date.toString() + ", Quantity: " + quantity;
    }
}
