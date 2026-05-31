import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    public String readFile(String путьКФайлу) throws IOException {
        StringBuilder содержимоеФайла = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(путьКФайлу), StandardCharsets.UTF_8)) {
            String строка;

            while ((строка = reader.readLine()) != null) {
                содержимоеФайла.append(строка).append(System.lineSeparator());
            }
        }

        return содержимоеФайла.toString();
    }

    public void writeFile(String путьКФайлу, String текст) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(путьКФайлу), StandardCharsets.UTF_8)) {
            writer.write(текст);
        }
    }
}