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

    public Material( String name, int quantity ) {
        this.name = name;
        this.quantity = quantity;
    }

    public void addToQuantity(int add) { quantity += add; }

    public boolean isLow() {
        return quantity <= lowerLimit;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    @Override
    public String toString() {
        return "(MATERIAL) " + name + ", Quantity: " + quantity + ", Lower limit: " + lowerLimit;
    }
}
