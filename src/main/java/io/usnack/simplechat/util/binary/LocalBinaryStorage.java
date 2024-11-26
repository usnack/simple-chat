package io.usnack.simplechat.util.binary;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ConditionalOnProperty(name = "binary-storage.mode", havingValue = "local")
@Component
public class LocalBinaryStorage implements BinaryStorage {
    @Value("${binary-storage.local-disk.root-directory:./local-binary-storage}")
    private String rootDirectory;

    @PostConstruct
    protected void init() throws IOException {
        Path rootDirectoryPath = Paths.get(rootDirectory.replaceAll("/", File.separator));
        if (Files.notExists(rootDirectoryPath)) {
            Files.createDirectories(rootDirectoryPath);
        }
    }

    @Override
    public UUID saveBinary(UUID fileKey, byte[] bytes) {
        Path filePath = resolvePhysicalFilePath(fileKey);
        try(
                FileOutputStream fos = new FileOutputStream(filePath.toFile())
        ) {
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileKey;
    }

    private Path resolvePhysicalFilePath(UUID fileKey) {
        return Paths.get(rootDirectory.replaceAll("/", File.separator), fileKey.toString());
    }

    @Override
    public String resolvePath(UUID fileKey) {
        return "/binaryContents/" + fileKey.toString() + "/download";
    }

    @Override
    public InputStream loadBinary(String path) {
        String[] paths = path.split("/");
        String fileKey = paths[paths.length - 2];
        Path physicalFilePath = resolvePhysicalFilePath(UUID.fromString(fileKey));
        try {
            return new FileInputStream(physicalFilePath.toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileKey);
        }
    }
}
