package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.request.CategoryCreateRequest;
import io.usnack.simplechat.dto.request.CategoryUpdateRequest;
import io.usnack.simplechat.dto.response.CategoryDetailResponse;
import io.usnack.simplechat.dto.response.CategoryListResponse;
import io.usnack.simplechat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDetailResponse> create(@RequestBody CategoryCreateRequest request) {
        CategoryDetailResponse response = categoryService.createCategory(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDetailResponse> findById(@PathVariable("categoryId") UUID categoryId) {
        CategoryDetailResponse response = categoryService.findCategoryById(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll() {
        CategoryListResponse response = categoryService.findAllCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<CategoryDetailResponse> update(@RequestBody CategoryUpdateRequest request) {
        CategoryDetailResponse response = categoryService.modifyCategory(request);
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
