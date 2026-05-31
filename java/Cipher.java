import java.util.HashMap;
import java.util.Map;

public class Cipher {

    // Русский алфавит + знаки препинания + пробелы и переносы строк
    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',

            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
            'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',

            '.', ',', '«', '»', '"', '\'', ':', ';', '-', '!', '?',
            ' ', '\n', '\r'
    };

    private static final Map<Character, Integer> INDEX_BY_SYMBOL = new HashMap<>();

    static {
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEX_BY_SYMBOL.put(ALPHABET[i], i);
        }
    }

    public String encrypt(String text, int key) {
        return shiftText(text, normalizeKey(key));
    }

    public String decrypt(String encryptedText, int key) {
        return shiftText(encryptedText, -normalizeKey(key));
    }

    public int getAlphabetSize() {
        return ALPHABET.length;
    }

    public boolean isValidKey(int key) {
        return key >= 0 && key < ALPHABET.length;
    }

    private int normalizeKey(int key) {
        return Math.floorMod(key, ALPHABET.length);
    }

    private String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder(text.length());

        for (char symbol : text.toCharArray()) {
            Integer currentIndex = INDEX_BY_SYMBOL.get(symbol);

            // Если символа нет в алфавите, оставляем его без изменений
            if (currentIndex == null) {
                result.append(symbol);
                continue;
            }

            int newIndex = Math.floorMod(currentIndex + shift, ALPHABET.length);
            result.append(ALPHABET[newIndex]);
        }

        return result.toString();
    }
}