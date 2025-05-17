package com.projectnoly;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.projectnoly.Model.MySql.Category;
import com.projectnoly.Repositories.CategoryRepo;
import com.projectnoly.Services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CategoryServiceTest {

    private CategoryRepo categoryRepo;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepo = mock(CategoryRepo.class);
        categoryService = new CategoryService(categoryRepo);
    }

    @Test
    void testGetAllCategories() {
        Category cat1 = new Category();
        Category cat2 = new Category();
        when(categoryRepo.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testGetCategoryById_Found() {
        Category cat = new Category();
        when(categoryRepo.findById(1)).thenReturn(Optional.of(cat));

        Category result = categoryService.getCategoryById(1);

        assertNotNull(result);
        verify(categoryRepo, times(1)).findById(1);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepo.findById(2)).thenReturn(Optional.empty());

        Category result = categoryService.getCategoryById(2);

        assertNull(result);
        verify(categoryRepo, times(1)).findById(2);
    }
}
