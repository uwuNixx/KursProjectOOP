package bouquet;

import flower.Flower;

import java.util.*;

public class Bouquet {
    private Map<Flower, Integer> flowerCounts;
    private int totalPrice;

    public Bouquet() {
        flowerCounts = new HashMap<>();
        totalPrice = 0;
    }

    public Bouquet(List<Flower> flowers) {
        this();
        addFlowers(flowers);
    }

    public void addFlower(Flower flower) {
        flowerCounts.put(flower, flowerCounts.getOrDefault(flower, 0) + 1);
        totalPrice += flower.getPrice();
    }

    public void addFlowers(List<Flower> flowers) {
        for (Flower flower : flowers) {
            addFlower(flower);
        }
    }

    public int getTotalFlowerCount() {
        int total = 0;
        for (int count : flowerCounts.values()) {
            total += count;
        }
        return total;
    }

    public int getFlowerCount(Flower flower) {
        return flowerCounts.getOrDefault(flower, 0);
    }

    public List<Flower> getUniqueFlowers() {
        return new ArrayList<>(flowerCounts.keySet());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isWithinLimit(int maxSameFlowers) {
        for (int count : flowerCounts.values()) {
            if (count > maxSameFlowers) {
                return false;
            }
        }
        return true;
    }

    public int countFlowersWithName(String namePart) {
        int count = 0;
        String lowerNamePart = namePart.toLowerCase();

        for (Map.Entry<Flower, Integer> entry : flowerCounts.entrySet()) {
            Flower flower = entry.getKey();
            int flowerCount = entry.getValue();

            if (flower.getName().toLowerCase().contains(lowerNamePart)) {
                count += flowerCount;
            }
        }
        return count;
    }

    public void sortByPrice() {
        List<Flower> sortedFlowers = new ArrayList<>(flowerCounts.keySet());
        sortedFlowers.sort(Comparator.comparingInt(Flower::getPrice));

        Map<Flower, Integer> sortedMap = new LinkedHashMap<>();
        for (Flower flower : sortedFlowers) {
            sortedMap.put(flower, flowerCounts.get(flower));
        }
        flowerCounts = sortedMap;
    }

    public void sortByName() {
        List<Flower> sortedFlowers = new ArrayList<>(flowerCounts.keySet());
        sortedFlowers.sort(Comparator.comparing(Flower::getName));

        Map<Flower, Integer> sortedMap = new LinkedHashMap<>();
        for (Flower flower : sortedFlowers) {
            sortedMap.put(flower, flowerCounts.get(flower));
        }
        flowerCounts = sortedMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Букет из ").append(getTotalFlowerCount()).append(" цветов (стоимость: ").append(totalPrice).append(" руб.)\n");

        int index = 1;
        for (Map.Entry<Flower, Integer> entry : flowerCounts.entrySet()) {
            Flower flower = entry.getKey();
            int count = entry.getValue();
            int subtotal = flower.getPrice() * count;

            sb.append(String.format("%d. %s × %d шт. = %d руб. (%d руб./шт.)\n", index++, flower.getName(), count, subtotal, flower.getPrice()));
        }
        return sb.toString();
    }

    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Букет из ").append(getTotalFlowerCount()).append(" цветов (стоимость: ").append(totalPrice).append(" руб.)\n");

        int index = 1;
        for (Map.Entry<Flower, Integer> entry : flowerCounts.entrySet()) {
            Flower flower = entry.getKey();
            int count = entry.getValue();

            for (int i = 0; i < count; i++) {
                sb.append(String.format("%d. %s - %d руб.\n", index++, flower.getName(), flower.getPrice()));
            }
        }
        return sb.toString();
    }
}