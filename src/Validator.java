

import java.io.File;

public class Validator {
    public static boolean validateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не существует или это не файл.");
            return false;
        }
        return true;
    }

    public static boolean validateShift(int shift, int alphabetSize) {
        if (shift < 0 || shift >= alphabetSize) {
            System.out.println("Сдвиг должен быть от 0 до " + (alphabetSize - 1));
            return false;
        }
        return true;
    }
}
