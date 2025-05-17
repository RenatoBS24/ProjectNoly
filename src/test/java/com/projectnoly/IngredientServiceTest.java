package com.projectnoly;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.Ingredient;
import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Model.MySql.MenuIngredient;
import com.projectnoly.Model.MySql.SaleMenu;
import com.projectnoly.Repositories.IngredientRepo;
import com.projectnoly.Services.IngredientService;
import com.projectnoly.Services.MenuService;
import com.projectnoly.Services.SaleMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {
    @Mock
    private IngredientRepo ingredientRepo;
    @Mock
    private MenuService menuService;
    @Mock
    private SaleMenuService saleMenuService;
    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientService(ingredientRepo, menuService, saleMenuService);
    }

    @Test
    void testGetAllIngredients() {
        List<Ingredient> ingredients = Arrays.asList(new Ingredient(), new Ingredient());
        when(ingredientRepo.getAllIngredients()).thenReturn(ingredients);
        List<Ingredient> result = ingredientService.getAllIngredients();
        assertEquals(2, result.size());
    }

    @Test
    void testEditIngredient() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        Ingredient mockIngredient = new Ingredient();
        mockIngredient.setRoute_image("existing_image.jpg");
        when(ingredientRepo.getReferenceById(anyInt())).thenReturn(mockIngredient);
        doNothing().when(ingredientRepo).editIngredient(anyInt(), anyString(), anyDouble(), anyInt(), anyString());
        ingredientService.editIngredient(1, "name", 2.0, 3, file);
        verify(ingredientRepo).editIngredient(anyInt(), anyString(), anyDouble(), anyInt(), anyString());
    }

    @Test
    void testAddIngredient() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        doNothing().when(ingredientRepo).addIngredient(anyString(), anyDouble(), anyString(), anyInt());
        ingredientService.addIngredient("name", 2.0, file, 3);
        verify(ingredientRepo).addIngredient(anyString(), anyDouble(), anyString(), anyInt());
    }

    @Test
    void testEditStockByAdd() {
        Product product = new Product();
        product.setId_product(1);
        product.setQuantity(2);
        MenuIngredient menuIngredient = mock(MenuIngredient.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(menuIngredient.getIngredient()).thenReturn(ingredient);
        when(ingredient.getId_ingredient()).thenReturn(1);
        when(menuIngredient.getQuantity()).thenReturn(3);
        Menu menu = mock(Menu.class);
        when(menu.getId_menu()).thenReturn(1);
        when(menu.getMenu_ingredients()).thenReturn(Collections.singletonList(menuIngredient));
        when(menuService.getAllMenu()).thenReturn(Collections.singletonList(menu));
        doNothing().when(ingredientRepo).updateStock(anyInt(), anyInt());
        ingredientService.editStockByAdd(Collections.singletonList(product));
        verify(ingredientRepo).updateStock(anyInt(), anyInt());
    }

    @Test
    void testEditStockByDelete() {
        SaleMenu saleMenu = mock(SaleMenu.class);
        Menu menu = mock(Menu.class);
        MenuIngredient menuIngredient = mock(MenuIngredient.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(saleMenu.getMenu()).thenReturn(menu);
        when(saleMenu.getQuantity()).thenReturn(2);
        when(menu.getMenu_ingredients()).thenReturn(Collections.singletonList(menuIngredient));
        when(menuIngredient.getIngredient()).thenReturn(ingredient);
        when(ingredient.getId_ingredient()).thenReturn(1);
        when(menuIngredient.getQuantity()).thenReturn(3);
        List<SaleMenu> saleMenuList = Collections.singletonList(saleMenu);
        doNothing().when(ingredientRepo).updateStock(anyInt(), anyInt());
        ingredientService.editStockByDelete("1234", saleMenuList);
        verify(ingredientRepo).updateStock(anyInt(), anyInt());
    }

    @Test
    void testGetIngredientsByTenHours() {
        SaleMenu saleMenu = mock(SaleMenu.class);
        Menu menu = mock(Menu.class);
        MenuIngredient menuIngredient = mock(MenuIngredient.class);
        Ingredient ingredient = new Ingredient();
        ingredient.setId_ingredient(1);
        ingredient.setStock(2);
        when(saleMenu.getMenu()).thenReturn(menu);
        when(saleMenu.getQuantity()).thenReturn(2);
        when(menuService.getMenuById(anyInt())).thenReturn(menu);
        when(menu.getMenu_ingredients()).thenReturn(Collections.singletonList(menuIngredient));
        when(menuIngredient.getIngredient()).thenReturn(ingredient);
        when(menuIngredient.getQuantity()).thenReturn(3);
        when(saleMenuService.getSaleMenuBy1OHours()).thenReturn(Collections.singletonList(saleMenu));
        List<Ingredient> result = ingredientService.getIngredientsByTenHours();
        assertFalse(result.isEmpty());
    }
}
