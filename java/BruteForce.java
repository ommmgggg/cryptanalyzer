public class BruteForce {

    private final Cipher cipher;

    public BruteForce(Cipher cipher) {
        this.cipher = cipher;
    }

    public String decryptAllVariants(String зашифрованныйТекст) {
        StringBuilder результат = new StringBuilder();

        for (int ключ = 0; ключ < cipher.getAlphabetSize(); ключ++) {
            String вариантРасшифровки = cipher.decrypt(зашифрованныйТекст, ключ);

            результат.append("========================================").append(System.lineSeparator());
            результат.append("Вариант расшифровки с ключом: ").append(ключ).append(System.lineSeparator());
            результат.append("========================================").append(System.lineSeparator());
            результат.append(вариантРасшифровки).append(System.lineSeparator());
            результат.append(System.lineSeparator());
        }

        return результат.toString();
    }
}