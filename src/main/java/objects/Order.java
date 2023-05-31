package objects;

import java.time.LocalDate;

public class Order {

    private String name;
    private LocalDate date;
    private int quantity;
    private Material[] components;

    public Order( String name, String date, int quantity, Material[] components) {
        this.name = name;
        this.quantity = quantity;
        this.date = LocalDate.parse( date );
        this.components = components;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //System.out.println(components[0]);

        sb.append( "{" );
        for (Material component : components)
            sb.append( component.getName() + " - " + component.getQuantity() + ", " );
        sb.append( "}" );

        return "(ORDER) " + name + ", Date: " + date.toString() + ", Quantity: " + quantity + ", Components: " + sb.toString();
    }
}
