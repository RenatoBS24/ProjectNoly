package com.projectnoly.Services;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.Ingredient;
import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Model.MySql.MenuIngredient;
import com.projectnoly.Model.MySql.SaleMenu;
import com.projectnoly.Repositories.IngredientRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class IngredientService {
    private final IngredientRepo ingredientRepo;
    private final MenuService menuService;
    private final SaleMenuService saleMenuService;
    private final Logger log = LoggerFactory.getLogger(IngredientService.class);
    @Autowired
    public IngredientService(IngredientRepo ingredientRepo, MenuService menuService, SaleMenuService saleMenuService) {
        this.ingredientRepo = ingredientRepo;
        this.menuService = menuService;
        this.saleMenuService = saleMenuService;
    }


    @Transactional
    public List<Ingredient> getAllIngredients(){
        return ingredientRepo.getAllIngredients();
    }
    public Ingredient getIngredientById(Integer id){
        return ingredientRepo.getReferenceById(id);
    }
    public void deleteIngredient(Integer id){
        ingredientRepo.deleteIngredient(id);
    }


    public void editIngredient(Integer id, String name, Double price, Integer stock, MultipartFile image){
        String route = saveImage(id,image);
        ingredientRepo.editIngredient(id,name,price,stock,route);
    }
    public void addIngredient(String name, Double price, MultipartFile image, Integer stock){
        String route = saveImage(0,image);
        ingredientRepo.addIngredient(name,price,route,stock);
    }

    private String saveImage(int id,MultipartFile img){
        if(img != null && !img.isEmpty()){
            try{
                String so = System.getProperty("os.name").toLowerCase();
                String route = "";
                if(so.contains("win")){
                    route = "C:/Users/DREP/Pictures/";

                }else if(so.contains("nux") || so.contains("nix")){
                    route = "/home/renatob/Imagenes/Noly/";
                }
                File carpet = new File(route);
                if(carpet.exists()){
                    carpet.mkdirs();
                }
                File file = new File(route + img.getOriginalFilename());
                img.transferTo(file);
                return file.getName();
            }catch (IOException e){
                log.warn(e.getMessage());
            }
        }else if (id >0){
            return getIngredientById(id).getRoute_image();
        }
        return "hamburguesa.jpg";
    }
    public void editStockByAdd(List<Product> products){
        List<Menu> menuList = menuService.getAllMenu();
        for(Product product: products){
            for(Menu menu: menuList){
                if(menu.getId_menu() == product.getId_product()){
                    List<MenuIngredient> menuIngredients = menu.getMenu_ingredients();
                    for(MenuIngredient menuIngredient: menuIngredients){
                        Integer id = menuIngredient.getIngredient().getId_ingredient();
                        int quantityIngredient = menuIngredient.getQuantity();
                        log.info(product.getQuantity()*quantityIngredient+"");
                        updateStock(id,product.getQuantity() * quantityIngredient);
                    }
                }
            }
        }
    }
    public void editStockByDelete(String code,List<SaleMenu> saleMenuList){
        if (code.equals("1234")) {
            HashMap<Menu,Integer> menuHashMap = new HashMap<>();
            for(SaleMenu saleMenu:saleMenuList){
                menuHashMap.put(saleMenu.getMenu(),saleMenu.getQuantity());
            }
            for(Map.Entry<Menu,Integer> entry: menuHashMap.entrySet()){
                Menu menu = entry.getKey();
                Integer quantity = entry.getValue();
                List<MenuIngredient> menuIngredients = menu.getMenu_ingredients();
                for(MenuIngredient menuIngredient: menuIngredients){
                    Integer id = menuIngredient.getIngredient().getId_ingredient();
                    Integer quantityIngredient = menuIngredient.getQuantity();
                    int newStock = (quantity * quantityIngredient)*-1;
                    updateStock(id,newStock);
                }
            }
        }
    }
    private void updateStock(Integer id, Integer discount){
        ingredientRepo.updateStock(id, discount);
    }
    private List<Ingredient> getIngredientsByMenu(Menu menu){
        List<MenuIngredient> menuIngredients = menu.getMenu_ingredients();
        List<Ingredient> ingredients = new LinkedList<>();
        for(MenuIngredient menuIngredient: menuIngredients){
            Ingredient ingredient = new Ingredient(menuIngredient.getIngredient().getId_ingredient(), menuIngredient.getIngredient().getName_ingredient(),menuIngredient.getIngredient().getPrice(),menuIngredient.getIngredient().getState(), menuIngredient.getIngredient().getRoute_image(),menuIngredient.getIngredient().getStock(),menuIngredient.getIngredient().getMenuIngredientList(), menuIngredient.getIngredient().getLots());
            int quantity = menuIngredient.getQuantity();
            ingredient.setStock(quantity);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public List<Ingredient> getIngredientsByTenHours(){
        List<SaleMenu> saleMenuList = saleMenuService.getSaleMenuBy1OHours();
        List<Ingredient> ingredientList = new LinkedList<>();
        for(SaleMenu saleMenu : saleMenuList){
            Menu menu = menuService.getMenuById(saleMenu.getMenu().getId_menu());
            int quantity = saleMenu.getQuantity();
            List<Ingredient> ingredients = getIngredientsByMenu(menu);
            for(Ingredient ingredient : ingredients){
                ingredient.setStock(ingredient.getStock()*quantity);
                if(ingredientList.contains(ingredient)){
                    int pos = ingredientList.indexOf(ingredient);
                    Ingredient ingredient1 = ingredientList.remove(pos);
                    ingredient1.setStock(ingredient1.getStock()+ingredient.getStock());
                    ingredientList.add(ingredient1);
                }else{
                    ingredientList.add(ingredient);
                }
            }

        }
        return ingredientList;
    }

}
