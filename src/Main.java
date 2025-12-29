import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();

        try {
            ui.showMainMenu();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}