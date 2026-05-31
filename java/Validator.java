import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Validator {

    public void validateInputFile(String путьКФайлу) {
        if (путьКФайлу == null || путьКФайлу.isBlank()) {
            throw new IllegalArgumentException("Путь к входному файлу не может быть пустым.");
        }

        try {
            Path путь = Path.of(путьКФайлу);

            if (!Files.exists(путь)) {
                throw new IllegalArgumentException("Входной файл не найден: " + путьКФайлу);
            }

            if (!Files.isRegularFile(путь)) {
                throw new IllegalArgumentException("Указанный путь не является файлом: " + путьКФайлу);
            }

            if (!Files.isReadable(путь)) {
                throw new IllegalArgumentException("Нет доступа для чтения файла: " + путьКФайлу);
            }
        } catch (InvalidPathException ошибка) {
            throw new IllegalArgumentException("Некорректный путь к входному файлу: " + путьКФайлу);
        }
    }

    public void validateOutputFile(String путьКФайлу) {
        if (путьКФайлу == null || путьКФайлу.isBlank()) {
            throw new IllegalArgumentException("Путь к выходному файлу не может быть пустым.");
        }

        try {
            Path путь = Path.of(путьКФайлу);

            if (Files.exists(путь) && Files.isDirectory(путь)) {
                throw new IllegalArgumentException("Выходной путь указывает на папку, а не на файл: " + путьКФайлу);
            }

            if (!путьКФайлу.toLowerCase().endsWith(".txt")) {
                throw new IllegalArgumentException("Выходной файл должен иметь расширение .txt");
            }
        } catch (InvalidPathException ошибка) {
            throw new IllegalArgumentException("Некорректный путь к выходному файлу: " + путьКФайлу);
        }
    }

    public void validateKey(int ключ, int размерАлфавита) {
        if (ключ < 0 || ключ >= размерАлфавита) {
            throw new IllegalArgumentException(
                    "Ключ должен быть от 0 до " + (размерАлфавита - 1)
            );
        }
    }
}