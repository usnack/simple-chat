package io.usnack.simplechat.repository.binary;

import io.usnack.simplechat.repository.BinaryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class FileBinaryRepository implements BinaryRepository {
    private final Path directory = Paths.get("binary");

    @PostConstruct
    public void init() throws IOException {
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

    @Override
    public String save(byte[] binary) {
        UUID fineName = UUID.randomUUID();
        Path filePath = directory.resolve(fineName.toString());
        try(
                OutputStream outputStream = Files.newOutputStream(filePath);
                ) {
            outputStream.write(binary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }
}
