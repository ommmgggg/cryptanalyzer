import java.io.IOException;

public class MainApp {

    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        FileManager fileManager = new FileManager();

        String входнойФайл = "input.txt";
        String файлШифра = "encrypted.txt";
        String файлРасшифровки = "decrypted.txt";
        int ключ = 3;

        try {
            String исходныйТекст = fileManager.readFile(входнойФайл);

            String зашифрованныйТекст = cipher.encrypt(исходныйТекст, ключ);
            fileManager.writeFile(файлШифра, зашифрованныйТекст);

            String расшифрованныйТекст = cipher.decrypt(зашифрованныйТекст, ключ);
            fileManager.writeFile(файлРасшифровки, расшифрованныйТекст);

            System.out.println("Исходный файл: " + входнойФайл);
            System.out.println("Файл с шифром: " + файлШифра);
            System.out.println("Файл с расшифровкой: " + файлРасшифровки);
            System.out.println("Операция выполнена успешно.");
        } catch (IOException ошибка) {
            System.out.println("Ошибка при работе с файлом: " + ошибка.getMessage());
        }
    }
}