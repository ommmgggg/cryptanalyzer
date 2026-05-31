public class MainApp {

    public static void main(String[] args) {
        Cipher cipher = new Cipher();

        String исходныйТекст = "Привет, мир!";
        int ключ = 3;

        String зашифрованныйТекст = cipher.encrypt(исходныйТекст, ключ);
        String расшифрованныйТекст = cipher.decrypt(зашифрованныйТекст, ключ);

        System.out.println("Исходный текст: " + исходныйТекст);
        System.out.println("Ключ: " + ключ);
        System.out.println("Зашифрованный текст: " + зашифрованныйТекст);
        System.out.println("Расшифрованный текст: " + расшифрованныйТекст);
    }
}