package objects;

public class Box {

    private String name;
    private Material[] components;

    public Box(String name, Material[] components) {
        this.name = name;
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public Material[] getComponents() {
        return components;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "{" );

        for (Material component : components)
            sb.append(component.getName() + " - " + component.getQuantity() + ", ");

        sb.append( "}" );

        return "(BOX) " + name + " - " + sb.toString();
    }
}
