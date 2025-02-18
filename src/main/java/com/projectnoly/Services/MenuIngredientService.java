package com.projectnoly.Services;

import com.projectnoly.Model.MySql.MenuIngredient;
import com.projectnoly.Repositories.MenuIngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class MenuIngredientService {

    private final MenuIngredientRepo menuIngredientRepo;
    private final Logger log = Logger.getLogger(MenuIngredientService.class.getName());

    @Autowired
    public MenuIngredientService(MenuIngredientRepo menuIngredientRepo){
        this.menuIngredientRepo = menuIngredientRepo;
    }

    public void addMenuIngredient(int id_menu, List<String> id_ingredients ){
        try {
            if (id_ingredients != null) {
                for(String id_ingredient : id_ingredients){
                    menuIngredientRepo.addMenuIngredient(id_menu,Integer.parseInt(id_ingredient));
                }
            }
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
        }

    }
    public void deleteMenuIngredient(int id_menu,List<String> id_ingredients){
        menuIngredientRepo.deleteMenuIngredient(id_menu);
        addMenuIngredient(id_menu,id_ingredients);
    }
    public MenuIngredient getMenuIngredientById(int id_product, int id_ingredient){
        return menuIngredientRepo.getMenuIngredient(id_product,id_ingredient);
    }
}
