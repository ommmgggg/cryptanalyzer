import java.io.IOException;
import java.util.Scanner;

public class MainApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cipher cipher = new Cipher();
    private static final FileManager fileManager = new FileManager();
    private static final Validator validator = new Validator();
    private static final BruteForce bruteForce = new BruteForce(cipher);
    private static final StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer(cipher);

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
                    bruteForceFile();
                    break;
                case "4":
                    statisticalAnalysisFile();
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

    private static void bruteForceFile() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String входнойФайл = scanner.nextLine();

            System.out.print("Введите путь к файлу для всех вариантов расшифровки: ");
            String выходнойФайл = scanner.nextLine();

            validator.validateInputFile(входнойФайл);
            validator.validateOutputFile(выходнойФайл);

            String зашифрованныйТекст = fileManager.readFile(входнойФайл);
            String всеВарианты = bruteForce.decryptAllVariants(зашифрованныйТекст);

            fileManager.writeFile(выходнойФайл, всеВарианты);

            System.out.println("Brute force выполнен.");
            System.out.println("Все варианты сохранены в файл: " + выходнойФайл);
        } catch (IllegalArgumentException ошибка) {
            System.out.println("Ошибка валидации: " + ошибка.getMessage());
        } catch (IOException ошибка) {
            System.out.println("Ошибка при работе с файлом: " + ошибка.getMessage());
        }
    }

    private static void statisticalAnalysisFile() {
    try {
        System.out.print("Введите путь к зашифрованному файлу: ");
        String входнойФайл = scanner.nextLine();

        System.out.print("Введите путь к файлу для результата статистического анализа: ");
        String выходнойФайл = scanner.nextLine();

        validator.validateInputFile(входнойФайл);
        validator.validateOutputFile(выходнойФайл);

        String зашифрованныйТекст = fileManager.readFile(входнойФайл);

        StatisticalAnalyzer.AnalysisResult результатАнализа =
                statisticalAnalyzer.analyze(зашифрованныйТекст);

        String текстДляЗаписи =
                "Статистический анализ завершён." + System.lineSeparator() +
                "Наиболее вероятный ключ: " + результатАнализа.getКлюч() + System.lineSeparator() +
                "Оценка текста: " + результатАнализа.getБалл() + System.lineSeparator() +
                "========================================" + System.lineSeparator() +
                результатАнализа.getРасшифрованныйТекст();

        fileManager.writeFile(выходнойФайл, текстДляЗаписи);

        System.out.println("Статистический анализ выполнен.");
        System.out.println("Наиболее вероятный ключ: " + результатАнализа.getКлюч());
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