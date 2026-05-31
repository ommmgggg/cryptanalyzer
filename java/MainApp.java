import java.io.IOException;
import java.util.Scanner;

public class MainApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cipher cipher = new Cipher();
    private static final FileManager fileManager = new FileManager();
    private static final Validator validator = new Validator();

    public static void main(String[] args) {
        boolean программаРаботает = true;

        while (программаРаботает) {
            printMenu();

            String выбор = scanner.nextLine();

            switch (выбор) {
                case "1":
                    encryptFile();
                    break;
                case "2":
                    decryptFile();
                    break;
                case "3":
                    System.out.println("Режим brute force будет добавлен на следующем этапе.");
                    break;
                case "4":
                    System.out.println("Статистический анализ будет добавлен позже.");
                    break;
                case "0":
                    программаРаботает = false;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Ошибка: выберите пункт меню от 0 до 4.");
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Caesar Cipher Tool ===");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл с известным ключом");
        System.out.println("3. Brute force");
        System.out.println("4. Статистический анализ");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void encryptFile() {
        try {
            System.out.print("Введите путь к исходному файлу: ");
            String входнойФайл = scanner.nextLine();

            System.out.print("Введите путь к файлу для результата: ");
            String выходнойФайл = scanner.nextLine();

            int ключ = readKey();

            validator.validateInputFile(входнойФайл);
            validator.validateOutputFile(выходнойФайл);
            validator.validateKey(ключ, cipher.getAlphabetSize());

            String исходныйТекст = fileManager.readFile(входнойФайл);
            String зашифрованныйТекст = cipher.encrypt(исходныйТекст, ключ);

            fileManager.writeFile(выходнойФайл, зашифрованныйТекст);

            System.out.println("Файл успешно зашифрован.");
            System.out.println("Результат сохранён в файл: " + выходнойФайл);
        } catch (IllegalArgumentException ошибка) {
            System.out.println("Ошибка валидации: " + ошибка.getMessage());
        } catch (IOException ошибка) {
            System.out.println("Ошибка при работе с файлом: " + ошибка.getMessage());
        }
    }

    private static void decryptFile() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String входнойФайл = scanner.nextLine();

            System.out.print("Введите путь к файлу для результата: ");
            String выходнойФайл = scanner.nextLine();

            int ключ = readKey();

            validator.validateInputFile(входнойФайл);
            validator.validateOutputFile(выходнойФайл);
            validator.validateKey(ключ, cipher.getAlphabetSize());

            String зашифрованныйТекст = fileManager.readFile(входнойФайл);
            String расшифрованныйТекст = cipher.decrypt(зашифрованныйТекст, ключ);

            fileManager.writeFile(выходнойФайл, расшифрованныйТекст);

            System.out.println("Файл успешно расшифрован.");
            System.out.println("Результат сохранён в файл: " + выходнойФайл);
        } catch (IllegalArgumentException ошибка) {
            System.out.println("Ошибка валидации: " + ошибка.getMessage());
        } catch (IOException ошибка) {
            System.out.println("Ошибка при работе с файлом: " + ошибка.getMessage());
        }
    }

    private static int readKey() {
        System.out.print("Введите ключ шифрования: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ошибка) {
            throw new IllegalArgumentException("Ключ должен быть целым числом.");
        }
    }
}