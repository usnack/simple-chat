package io.usnack.simplechat.util.binary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(properties = {
        "binary-storage.mode=local"
})
class LocalBinaryStorageTest {
    @Autowired private LocalBinaryStorage localBinaryStorage;

    @Test
    void save() {
        Path profileDir = Paths.get("src", "test", "resources", "profiles");
        Map<String, byte[]> profileBinaries = new HashMap<>();
        List.of("woody", "buzz", "jessie").forEach(key -> {
            try (
                    FileInputStream fis = new FileInputStream(profileDir.resolve(key + ".jpeg").toFile())
            ) {
                byte[] bytes = fis.readAllBytes();
                profileBinaries.put(key, bytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        profileBinaries.forEach((k, v) -> {
            localBinaryStorage.saveBinary(UUID.randomUUID(), v);
        });

    }

    @Test
    void load() throws IOException {
        String resolvedPath = localBinaryStorage.resolvePath(UUID.fromString("388c63d6-c539-4a73-9e34-777774af47bd"));
        InputStream inputStream = localBinaryStorage.loadBinary(resolvedPath);
        byte[] bytes = inputStream.readAllBytes();
        try (
                FileOutputStream fos = new FileOutputStream(new File("./Hello.jpeg"))
        ){
            fos.write(bytes);
        }

    }
}