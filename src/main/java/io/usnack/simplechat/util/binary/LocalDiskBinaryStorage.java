package io.usnack.simplechat.util.binary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ConditionalOnProperty(name = "binary-storage.mode", havingValue = "local")
@Component
public class LocalDiskBinaryStorage implements BinaryStorage {
    @Value("${binary-storage.local-disk.root-directory:files}")
    private String rootDirectory;

    @Override
    public UUID saveBinary(UUID fileKey, byte[] bytes) {
        Path filePath = Paths.get(rootDirectory, fileKey.toString());
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

    @Override
    public String resolvePath(UUID fileKey) {
        Path path = Paths.get("/local-disk", rootDirectory, fileKey.toString());
        return path.toString();
    }

    @Override
    public InputStream loadBinary(String path) {
        return null;
    }
}
