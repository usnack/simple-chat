package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.request.CategoryCreateRequest;
import io.usnack.simplechat.dto.request.CategoryUpdateRequest;
import io.usnack.simplechat.dto.response.CategoryDetailResponse;
import io.usnack.simplechat.dto.response.CategoryListResponse;
import io.usnack.simplechat.entity.Category;
import io.usnack.simplechat.mapstruct.CategoryMapper;
import io.usnack.simplechat.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDetailResponse createCategory(CategoryCreateRequest request) {
        String name = request.name();
        long now = Instant.now().toEpochMilli();
        Category categoryToCreate = new Category(name, now);

        Category createdCategory = categoryRepository.save(categoryToCreate);
        CategoryDetailResponse response = categoryMapper.toDetailResponse(createdCategory);
        return response;
    }

    public CategoryDetailResponse findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> new CategoryDetailResponse(category.getId(), category.getName(), category.getCreatedAt()))
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id %s not found", categoryId)));
    }

    public CategoryListResponse findAllCategories() {
        List<CategoryDetailResponse> categories = categoryRepository.findAll()
                .stream().map(categoryMapper::toDetailResponse)
                .toList();

        return new CategoryListResponse(categories);
    }

    @Transactional
    public CategoryDetailResponse modifyCategory(CategoryUpdateRequest request) {
        UUID categoryId = request.categoryId();
        String name = request.name();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id %s not found", categoryId)));
        category.updateCategory(name);

        CategoryDetailResponse response = categoryMapper.toDetailResponse(category);
        return response;
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException(String.format("Category with id %s not found", categoryId));
        }
        categoryRepository.deleteById(categoryId);
    }
}
