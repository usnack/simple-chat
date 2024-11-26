package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.BinaryContentInputStreamDto;
import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.entity.BinaryContent;
import io.usnack.simplechat.repository.BinaryContentRepository;
import io.usnack.simplechat.util.binary.BinaryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryStorage binaryStorage;

    @Transactional
    public BinaryContent createBinaryContent(BinaryContentCreateRequest request) {
        String fileName = request.fileName();
        String contentType = request.contentType();
        byte[] bytes = request.bytes();

        BinaryContent binaryContentToCreate = new BinaryContent(fileName, (long) bytes.length, contentType);
        BinaryContent createdBinaryContent = binaryContentRepository.save(binaryContentToCreate);

        UUID binaryContentIdAsKey = createdBinaryContent.getId();
        binaryStorage.saveBinary(binaryContentIdAsKey, bytes);

        return createdBinaryContent;
    }

    public String resolvePath(UUID binaryContentId) {
        return binaryStorage.resolvePath(binaryContentId);
    }

    public BinaryContentInputStreamDto loadBinaryContent(UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentRepository.findById(binaryContentId)
                .orElseThrow(() -> new NoSuchElementException("Binary content with id " + binaryContentId + " not found"));

        String resolvedPath = binaryStorage.resolvePath(binaryContentId);
        InputStream inputStream = binaryStorage.loadBinary(resolvedPath);

        BinaryContentInputStreamDto response = new BinaryContentInputStreamDto(binaryContent.getId(), binaryContent.getFileName(), binaryContent.getSize(), binaryContent.getContentType(), resolvedPath, inputStream);
        return response;
    }
}
