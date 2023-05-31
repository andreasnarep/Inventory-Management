package objects;

public class Material {

    private String name;
    private int quantity;
    private int lowerLimit; //When to notify user by low quantity

    public Material( String name, int quantity, int lowerLimit ) {
        this.name = name;
        this.quantity = quantity;
        this.lowerLimit = lowerLimit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material( String name, int quantity ) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "(MATERIAL) " + name + ", Quantity: " + quantity + ", Lower limit: " + lowerLimit;
    }
}
