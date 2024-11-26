package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.entity.BinaryContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = {
                "binary-storage.mode=local"
        }
)
class BinaryContentServiceTest {
    @Autowired
    BinaryContentService binaryContentService;

    @Rollback(value = false)
    @Transactional
    @Test
    void createTest() {
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

        byte[] bytes = profileBinaries.get("woody");
        BinaryContent binaryContent = binaryContentService.createBinaryContent(
                new BinaryContentCreateRequest(
                        "woody.jpeg",
                        "image/jpeg",
                        bytes
                )
        );
    }
}