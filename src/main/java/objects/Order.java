package objects;

import java.time.LocalDate;

public class Order {

    private String name;
    private LocalDate date;

    private Material[] components;

    public Order( String name, LocalDate date, Material[] components ) {
        this.name = name;
        this.date = date;
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }


    public Material[] getComponents() {
        return components;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //System.out.println(components[0]);

        sb.append( "{" );
        for ( Material component : components )
            sb.append( component.getName() + " - " + component.getQuantity() + ", " );
        sb.append( "}" );

        return "(ORDER) " + name + ", Date: " + date.toString() + ", Components: " + sb.toString();
    }
}
