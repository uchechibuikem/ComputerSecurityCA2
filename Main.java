import java.util.Scanner;

public class Main {


    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){

        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("Enter option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> encryptFile();
                case "2" -> decryptFile();
                case "3" -> {
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
        }

    }
}
