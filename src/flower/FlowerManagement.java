package flower;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlowerManagement {
    private List<Flower> availableFlowers;

    public FlowerManagement() {
        availableFlowers = new ArrayList<>();
        loadFlowersFromFile("src/flowers.txt");
    }

    private void loadFlowersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int price = Integer.parseInt(parts[1].trim());
                    availableFlowers.add(new Flower(name, price));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public List<Flower> getAvailableFlowers() {
        return new ArrayList<>(availableFlowers);
    }

    public void showAvailableFlowers() {
        System.out.println("\n=== ДОСТУПНЫЕ ЦВЕТЫ ===");
        System.out.printf("%-25s %s\n", "Название", "Цена");
        System.out.println("---------------------------------");

        for (Flower flower : availableFlowers) {
            System.out.printf("%-25s %d руб.\n", flower.getName(), flower.getPrice());
        }
    }
}
