import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
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
    private static void printMenu() {
        System.out.println("=====================================");
        System.out.println(" AES File Encrypt / Decrypt System");
        System.out.println("=====================================");
        System.out.println(" 1. Encrypt a File");
        System.out.println(" 2. Decrypt a File");
        System.out.println(" 3. Quit");
        System.out.println("=====================================");
    }

    private static void encryptFile() {
        try {
            System.out.print("Enter filename to encrypt: ");
            String filename = scanner.nextLine().trim();
            Path filePath = Path.of(filename);

            if (!Files.exists(filePath)) {
                System.out.println("Error: File not found.\n");
                return;
            }

            // Read file
            byte[] plainBytes = Files.readAllBytes(filePath);

            // Generate AES key
            SecretKey key = generateAESKey();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

            // Encrypt
            byte[] cipherBytes = encryptAES(plainBytes, key);

            Files.write(Path.of("ciphertext.txt"), cipherBytes);

            System.out.println("\nFile encrypted successfully!");
            System.out.println("Encrypted file saved as ciphertext.txt");
            System.out.println("Your AES Key (save this to decrypt):");
            System.out.println(encodedKey + "\n");

        } catch (IOException e) {
            System.out.println("File read/write error: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Encryption failed: " + e.getMessage() + "\n");
        }
    }
    // -------------------- TASK 3: DECRYPT --------------------
    private static void decryptFile() {
        try {
            System.out.print("Enter filename to decrypt: ");
            String filename = scanner.nextLine().trim();
            Path filePath = Path.of(filename);

            if (!Files.exists(filePath)) {
                System.out.println("Error: File not found.\n");
                return;
            }

            System.out.print("Enter AES key: ");
            String keyInput = scanner.nextLine().trim();

            SecretKey key;
            try {
                byte[] decoded = Base64.getDecoder().decode(keyInput);
                key = new SecretKeySpec(decoded, "AES");
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid key format (must be Base64 encoded).\n");
                return;
            }

            byte[] cipherBytes = Files.readAllBytes(filePath);
            byte[] plainBytes = decryptAES(cipherBytes, key);

            Files.write(Path.of("plaintext.txt"), plainBytes);

            System.out.println("\nFile decrypted successfully!");
            System.out.println("Decrypted file saved as plaintext.txt\n");

        } catch (IOException e) {
            System.out.println("File read/write error: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Decryption failed: Invalid key or corrupted file.\n");
        }
    }

}
