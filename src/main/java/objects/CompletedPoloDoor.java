package objects;

import java.time.LocalDate;

public class CompletedPoloDoor extends Door{

    private LocalDate date;
    private int quantity;

    public CompletedPoloDoor( String doorName, String date, Integer quantity ) {
        super( doorName );
        this.date = LocalDate.parse( date );
        this.quantity = quantity;
    }

    public CompletedPoloDoor( String doorName, LocalDate date, Integer quantity ) {
        super( doorName );
        this.date = date;
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
        return "(COMPLETEDPOLODOOR) " + getDoorName() + ", Date: " + date.toString() + ", Quantity: " + quantity;
    }
}
