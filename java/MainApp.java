import java.io.IOException;

public class MainApp {

    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        FileManager fileManager = new FileManager();
        Validator validator = new Validator();

        String входнойФайл = "input.txt";
        String файлШифра = "encrypted.txt";
        String файлРасшифровки = "decrypted.txt";
        int ключ = 3;

        try {
            validator.validateInputFile(входнойФайл);
            validator.validateOutputFile(файлШифра);
            validator.validateOutputFile(файлРасшифровки);
            validator.validateKey(ключ, cipher.getAlphabetSize());

            String исходныйТекст = fileManager.readFile(входнойФайл);

            String зашифрованныйТекст = cipher.encrypt(исходныйТекст, ключ);
            fileManager.writeFile(файлШифра, зашифрованныйТекст);

            String расшифрованныйТекст = cipher.decrypt(зашифрованныйТекст, ключ);
            fileManager.writeFile(файлРасшифровки, расшифрованныйТекст);

            System.out.println("Проверка данных выполнена успешно.");
            System.out.println("Исходный файл: " + входнойФайл);
            System.out.println("Файл с шифром: " + файлШифра);
            System.out.println("Файл с расшифровкой: " + файлРасшифровки);
            System.out.println("Операция завершена.");
        } catch (IllegalArgumentException ошибка) {
            System.out.println("Ошибка валидации: " + ошибка.getMessage());
        } catch (IOException ошибка) {
            System.out.println("Ошибка при работе с файлом: " + ошибка.getMessage());
        }
    }
}