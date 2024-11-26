package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.BinaryContentInputStreamDto;
import io.usnack.simplechat.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/binaryContents")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @GetMapping("{binaryContentId}")
    public ResponseEntity<Resource> downloadBinaryContent(@PathVariable("binaryContentId") UUID binaryContentId) {
        BinaryContentInputStreamDto inputStreamDto = binaryContentService.loadBinaryContent(binaryContentId);
        Resource resource = new InputStreamResource(inputStreamDto.inputStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", inputStreamDto.fileName()))
                .header(HttpHeaders.CONTENT_TYPE, inputStreamDto.contentType())
                .contentLength(inputStreamDto.size())
                .body(resource);
    }
}
