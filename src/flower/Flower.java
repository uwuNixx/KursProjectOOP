package flower;

public class Flower implements Comparable<Flower> {
    private final String name;
    private final int price;

    public Flower(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - " + price + " руб.";
    }

    @Override
    public int compareTo(Flower other) {
        return Integer.compare(this.price, other.price);
    }
}