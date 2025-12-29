package ui;

import flower.FlowerManagement;
import bouquet.Bouquet;
import bouquet.BouquetManagement;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private FlowerManagement flowerManager;
    private BouquetManagement bouquetManager;

    public ConsoleUI() {
        flowerManager = new FlowerManagement();
        bouquetManager = new BouquetManagement();
    }

    public void showMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║      СИСТЕМА \"ЦВЕТОЧНИЦА\"        ║");
            System.out.println("╚══════════════════════════════════╝");

            while (true) {
                System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
                System.out.println("1. Собрать букет на заданную сумму");
                System.out.println("2. Показать все доступные цветы");
                System.out.println("3. Выйти из программы");
                System.out.print("Выберите действие (1-3): ");

                try {
                    int choice = Integer.parseInt(scanner.nextLine().trim());

                    switch (choice) {
                        case 1:
                            findBouquetsMenu(scanner);
                            break;
                        case 2:
                            flowerManager.showAvailableFlowers();
                            System.out.print("\nНажмите Enter для продолжения...");
                            scanner.nextLine();
                            break;
                        case 3:
                            System.out.println("Выход из программы...");
                            return;
                        default:
                            System.out.println("Неверный выбор. Попробуйте снова.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число от 1 до 3");
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }

    private void findBouquetsMenu(Scanner scanner) {
        try {
            System.out.println("\n=== ПОИСК БУКЕТОВ ===");

            System.out.print("Введите сумму для букета (в рублях): ");
            int targetPrice = Integer.parseInt(scanner.nextLine().trim());

            if (targetPrice <= 0) {
                System.out.println("Сумма должна быть положительной!");
                return;
            }

            System.out.println("\nОграничение на одинаковые цветы:");
            System.out.println("  - Введите 0 для букетов без ограничений");
            System.out.println("  - Или укажите максимальное количество одинаковых цветов");
            System.out.print("Максимум одинаковых цветов (рекомендуется 3-5): ");

            int maxSameFlowers = Integer.parseInt(scanner.nextLine().trim());

            if (maxSameFlowers < 0) {
                System.out.println("Ограничение не может быть отрицательным!");
                return;
            }

            List<flower.Flower> flowers = flowerManager.getAvailableFlowers();
            List<Bouquet> bouquets = bouquetManager.findBouquetsByPrice(flowers, targetPrice, maxSameFlowers);

            if (bouquets.isEmpty()) {
                System.out.print("\nНа сумму " + targetPrice + " руб. ");
                if (maxSameFlowers > 0) {
                    System.out.print("с ограничением " + maxSameFlowers + " одинаковых цветов ");
                }
                System.out.print("невозможно собрать букет.");
                System.out.println("\nПопробуйте:");
                System.out.println("1. Увеличить бюджет");
                System.out.println("2. Ослабить ограничение");
                System.out.println("3. Выбрать другую сумму");
                return;
            }

            System.out.println("\n=== НАЙДЕНО БУКЕТОВ: " + bouquets.size() + " ===");
            if (maxSameFlowers > 0) {
                System.out.println("(ограничение: не более " + maxSameFlowers + " одинаковых цветов)");
            }

            int pageSize = 5;
            int totalPages = (int) Math.ceil((double) bouquets.size() / pageSize);
            int currentPage = 1;

            while (true) {
                System.out.println("\n--- Страница " + currentPage + " из " + totalPages + " ---");

                int startIndex = (currentPage - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, bouquets.size());

                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("\n--- Букет #" + (i + 1) + " ---");
                    System.out.println(bouquets.get(i));
                }

                System.out.println("\n" + "═".repeat(50));
                System.out.println("Выберите действие:");

                if (currentPage < totalPages) {
                    System.out.println("  \"далее\\next\" - следующая страница (" + (currentPage + 1) + ")");
                }
                if (currentPage > 1) {
                    System.out.println("  \"назад\\back\" - предыдущая страница (" + (currentPage - 1) + ")");
                }
                System.out.println("  номер букета (1-" + bouquets.size() + ") - выбрать букет");
                System.out.println("  \"новый\\new\" - задать новые параметры поиска");
                System.out.println("  \"меню\\menu\" - вернуться в главное меню");
                System.out.print("Ваш выбор: ");

                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("далее") || input.equals("next")) {
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Это последняя страница!");
                    }
                } else if (input.equals("назад") || input.equals("back") || input.equals("prev")) {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Это первая страница!");
                    }
                } else if (input.equals("новый") || input.equals("new")) {
                    return;
                } else if (input.equals("меню") || input.equals("menu")) {
                    return;
                } else {
                    try {
                        int bouquetNumber = Integer.parseInt(input);

                        if (bouquetNumber >= 1 && bouquetNumber <= bouquets.size()) {
                            System.out.println("\nВыбран букет #" + bouquetNumber);
                            System.out.println(bouquets.get(bouquetNumber));
                            processBouquetMenu(scanner, bouquets.get(bouquetNumber - 1));

                            System.out.print("\nВернуться к просмотру букетов? (да/нет): ");
                            String answer = scanner.nextLine().trim().toLowerCase();
                            if (!answer.equals("да") && !answer.equals("yes") && !answer.equals("y")) {
                                return;
                            }

                            System.out.println("\n" + "═".repeat(50));
                        } else {
                            System.out.println("Нет букета с таким номером. Доступные номера: 1-" + bouquets.size());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Неизвестная команда. Попробуйте снова.");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void processBouquetMenu(Scanner scanner, Bouquet bouquet) {
        try {
            System.out.println("\n=== ОБРАБОТКА БУКЕТА ===");

            System.out.println("\nСортировка букета:");
            System.out.println("1. По стоимости цветов");
            System.out.println("2. По названиям цветов");
            System.out.print("Выберите тип сортировки (1-2): ");

            int sortChoice = Integer.parseInt(scanner.nextLine().trim());

            if (sortChoice == 1) {
                bouquet.sortByPrice();
                System.out.println("\nБукет отсортирован по стоимости:");
            } else if (sortChoice == 2) {
                bouquet.sortByName();
                System.out.println("\nБукет отсортирован по названию:");
            } else {
                System.out.println("\nСортировка не применена:");
            }

            System.out.println(bouquet);

            System.out.print("\nНайти цветы по названию (например: \"роза\") ");
            String searchName = scanner.nextLine().trim();

            if (!searchName.isEmpty()) {
                int count = bouquet.countFlowersWithName(searchName);
                System.out.println("Количество цветов с названием содержащим \"" + searchName + "\": " + count);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}