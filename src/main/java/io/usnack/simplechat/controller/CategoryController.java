package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.CategoryDto;
import io.usnack.simplechat.dto.request.CategoryCreateRequest;
import io.usnack.simplechat.dto.request.CategoryUpdateRequest;
import io.usnack.simplechat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreateRequest request) {
        CategoryDto response = categoryService.createCategory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("categoryId") UUID categoryId) {
        CategoryDto response = categoryService.findCategoryById(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        List<CategoryDto> response = categoryService.findAllCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryUpdateRequest request) {
        CategoryDto response = categoryService.modifyCategory(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity delete(@PathVariable("categoryId") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
