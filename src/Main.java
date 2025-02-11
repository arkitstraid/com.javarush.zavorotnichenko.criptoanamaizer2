

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Выберите действие: ");
            System.out.println("1. Зашифровать текст");
            System.out.println("2. Расшифровать текст");
            System.out.println("3. Взломать текст (brute force)");
            System.out.println("Введите 'exit' для выхода");

            command = scanner.nextLine();

            switch (command) {
                case "1":
                    CaesarCipher.encryptText(scanner);
                    break;
                case "2":
                    CaesarCipher.decryptText(scanner);
                    break;
                case "3":
                    CaesarCipher.bruteForceDecrypt(scanner);
                    break;
                case "exit":
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        } while (!command.equals("exit"));

        scanner.close();
    }
}
