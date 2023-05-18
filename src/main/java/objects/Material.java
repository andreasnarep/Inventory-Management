package objects;

public class Material {

    private String name;
    private long quantity;
    private long lowerLimit; //When to notify user by low quantity

    public Material(String name, long quantity, long lowerLimit) {
        this.name = name;
        this.quantity = quantity;
        this.lowerLimit = lowerLimit;
    }

    @Override
    public String toString() {
        return "(MATERIAL) " + name + ", Quantity: " + quantity + ", Lower limit: " + lowerLimit;
    }
}
