package objects;

import java.time.LocalDate;

public class CompletedBQDoor extends Door{

    private LocalDate date;
    private int quantity;

    public CompletedBQDoor( String doorName, String date, Integer quantity ) {
        super( doorName );
        this.date = LocalDate.parse( date );
        this.quantity = quantity;
    }

    public CompletedBQDoor( String doorName, LocalDate date, Integer quantity ) {
        super( doorName );
        this.date =  date;
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "(COMPLETEDBQDOOR) " + getDoorName() + ", Date: " + date.toString() + ", Quantity: " + quantity;
    }
}
