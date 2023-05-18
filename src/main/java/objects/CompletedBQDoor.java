package objects;

import java.time.LocalDate;

public class CompletedBQDoor extends Door{

    private LocalDate date;
    private long quantity;

    public CompletedBQDoor( String doorName, String date, long quantity ) {
        super( doorName );
        this.date = LocalDate.parse( date );
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "(COMPLETEDBQDOOR) " + getDoorName() + ", Date: " + date.toString() + ", Quantity: " + quantity;
    }
}
