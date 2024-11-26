package io.usnack.simplechat.util;

import io.usnack.simplechat.util.binary.S3BinaryStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Slf4j
class S3BinaryStorageTest {
    @Autowired
    S3BinaryStorage s3BinaryStorage;

    @Test
    void test() {
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

        profileBinaries.forEach((key, bytes) -> {
            UUID fileKey = s3BinaryStorage.saveBinary(UUID.randomUUID(), bytes);
            log.debug("url : {}", fileKey);
        });

    }

    @Test
    void test2() {
        String url = s3BinaryStorage.resolvePath(UUID.randomUUID());
        log.debug("url : {}", url);
    }

}