package bouquet;

import flower.Flower;

import java.util.*;

public class BouquetManagement {

    public List<Bouquet> findBouquetsByPrice(List<Flower> flowers, int targetPrice, int maxSameFlowers) {
        List<Bouquet> result = new ArrayList<>();
        Set<String> uniqueCombinations = new HashSet<>();

        findCombinations(flowers, targetPrice, maxSameFlowers, 0, new ArrayList<>(), 0, result, uniqueCombinations);

        return result;
    }

    private void findCombinations(List<Flower> flowers, int targetPrice, int maxSameFlowers, int startIndex, List<Flower> currentCombination, int currentSum, List<Bouquet> result, Set<String> uniqueCombinations) {

        if (currentSum > targetPrice || uniqueCombinations.size() == 100000) {
            return;
        }

        if (currentSum == targetPrice) {
            Bouquet tempBouquet = new Bouquet(currentCombination);

            if (maxSameFlowers <= 0 || tempBouquet.isWithinLimit(maxSameFlowers)) {
                String key = generateCombinationKey(currentCombination);
                if (uniqueCombinations.add(key)) {
                    result.add(tempBouquet);
                }
            }
            return;
        }

        for (int i = startIndex; i < flowers.size(); i++) {
            Flower flower = flowers.get(i);

            if (maxSameFlowers > 0 && countFlower(currentCombination, flower) >= maxSameFlowers) {
                continue;
            }

            currentCombination.add(flower);
            findCombinations(flowers, targetPrice, maxSameFlowers, i, currentCombination, currentSum + flower.getPrice(), result, uniqueCombinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private int countFlower(List<Flower> combination, Flower flower) {
        int count = 0;
        for (Flower f : combination) {
            if (f.equals(flower)) count++;
        }
        return count;
    }

    private String generateCombinationKey(List<Flower> flowers) {
        List<Flower> sorted = new ArrayList<>(flowers);
        sorted.sort(Comparator.comparingInt(Flower::getPrice).thenComparing(Flower::getName));

        StringBuilder key = new StringBuilder();
        for (Flower flower : sorted) {
            key.append(flower.getName()).append("-").append(flower.getPrice()).append(";");
        }
        return key.toString();
    }
}