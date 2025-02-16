

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class CaesarCipher {
    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к',
            'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    public static void encryptText(Scanner scanner) {
        System.out.print("Введите путь к файлу с оригинальным текстом: ");
        String inputFilePath = scanner.nextLine();
        System.out.print("Введите путь к файлу для сохранения зашифрованного текста: ");
        String outputFilePath = scanner.nextLine();
        System.out.print("Введите сдвиг (ключ): ");
        int shift;
        try {
            shift = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }


        if (!Validator.validateFile(inputFilePath) || !Validator.validateShift(shift, ALPHABET.length)) {
            return;
        }

        try {
            String originalText = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            StringBuilder encryptedText = new StringBuilder();

            for (char c : originalText.toCharArray()) {
                int index = findIndex(c);
                if (index != -1) {
                    int newIndex = (index + shift) % ALPHABET.length;
                    encryptedText.append(ALPHABET[newIndex]);
                }
            }

            FileHandler.writeToFile(outputFilePath, encryptedText.toString());
            System.out.println("Текст успешно зашифрован и сохранен в " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public static void decryptText(Scanner scanner) {
        System.out.print("Введите путь к зашифрованному файлу: ");
        String inputFilePath = scanner.nextLine();
        System.out.print("Введите путь к файлу для сохранения расшифрованного текста: ");
        String outputFilePath = scanner.nextLine();
        System.out.print("Введите сдвиг (ключ): ");
        int shift;
        try {
            shift = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        if (!Validator.validateFile(inputFilePath) || !Validator.validateShift(shift, ALPHABET.length)) {
            return;
        }

        try {
            String encryptedText = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            StringBuilder decryptedText = new StringBuilder();

            for (char c : encryptedText.toCharArray()) {
                int index = findIndex(c);
                if (index != -1) {
                    int newIndex = (index - shift + ALPHABET.length) % ALPHABET.length;
                    decryptedText.append(ALPHABET[newIndex]);
                }
            }

            FileHandler.writeToFile(outputFilePath, decryptedText.toString());
            System.out.println("Текст успешно расшифрован и сохранен в " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public static void bruteForceDecrypt(Scanner scanner) {
        System.out.print("Введите путь к зашифрованному файлу: ");
        String inputFilePath = scanner.nextLine();
        System.out.print("Введите путь к файлу для сохранения расшифрованного текста: ");
        String outputFilePath = scanner.nextLine();

        if (!Validator.validateFile(inputFilePath)) {
            return;
        }

        try {
            String encryptedText = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            StringBuilder allDecryptedTexts = new StringBuilder();

            for (int shift = 0; shift < ALPHABET.length; shift++) {
                StringBuilder decryptedText = new StringBuilder();
                for (char c : encryptedText.toCharArray()) {
                    int index = findIndex(c);
                    if (index != -1) {
                        int newIndex = (index - shift + ALPHABET.length) % ALPHABET.length;
                        decryptedText.append(ALPHABET[newIndex]);
                    }
                }
                allDecryptedTexts.append("Сдвиг ").append(shift).append(": ").append(decryptedText.toString()).append("\n");
            }

            FileHandler.writeToFile(outputFilePath, allDecryptedTexts.toString());
            System.out.println("Все возможные расшифровки сохранены в " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static int findIndex(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        return -1; // Символ не найден
    }
}
