package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.CategoryDto;
import io.usnack.simplechat.dto.request.CategoryCreateRequest;
import io.usnack.simplechat.dto.request.CategoryUpdateRequest;
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
    public CategoryDto createCategory(CategoryCreateRequest request) {
        String name = request.name();
        long now = Instant.now().toEpochMilli();
        Category categoryToCreate = new Category(name, now);

        Category createdCategory = categoryRepository.save(categoryToCreate);
        CategoryDto response = categoryMapper.toDto(createdCategory);
        return response;
    }

    public CategoryDto findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id %s not found", categoryId)));
    }

    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public CategoryDto modifyCategory(CategoryUpdateRequest request) {
        UUID categoryId = request.categoryId();
        String name = request.name();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Category with id %s not found", categoryId)));
        category.updateCategory(name);

        CategoryDto response = categoryMapper.toDto(category);
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
