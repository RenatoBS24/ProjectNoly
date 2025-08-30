package com.projectnoly.Services;

import com.projectnoly.DTO.Menu.MenuAddToTableDto;
import com.projectnoly.DTO.Menu.MenuIngredientDto;
import com.projectnoly.DTO.Menu.MenuResponseDto;
import com.projectnoly.DTO.ProductDataDto;
import com.projectnoly.Exception.ResourceNotFoundException;
import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Model.MySql.MenuIngredient;
import com.projectnoly.Repositories.MenuRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepo menuRepo;
    private final Logger log = LoggerFactory.getLogger(MenuService.class);
    private final String DEFAULT_IMAGE = "hamburguesa.jpg";
    @Autowired
    public MenuService(MenuRepo menuRepo){
        this.menuRepo = menuRepo;
    }
    @Transactional
    public List<Menu> getAllMenu(){
        return menuRepo.getAllMenu();
    }
    @Transactional
    public List<MenuResponseDto> getAllMenuResponse(){
       return menuRepo.getAllMenu().stream()
               .map(menu -> new MenuResponseDto(
               (long) menu.getId_menu(),
               menu.getName_item(),
               menu.getDescription(),
               menu.getPrice(),
               menu.getRoute_image(),
               (long) menu.getCategory().getId(),
               menu.getMenu_ingredients().stream().map(
                       menuIngredient -> new MenuIngredientDto(
                               (long) menuIngredient.getId(),
                               menuIngredient.getQuantity(),
                               (long) menuIngredient.getIngredient().getId_ingredient()
                       )
               ).toList(),
               isAvailable(menu.getId_menu())
       )).toList();
    }
    private  boolean isAvailable(int id){
        Menu menu = getMenuById(id);
        for(MenuIngredient menuIngredient : menu.getMenu_ingredients()){
            if (menuIngredient.getIngredient().getStock() <=0){
                return false;
            }
        }
        return true;
    }

    public Menu getMenuById(int id){
        return menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("menu","id",id));
    }
    public MenuResponseDto getDataMenuById(int id){
       Menu menu = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("menu","id",id));
       return new MenuResponseDto(
               (long)menu.getId_menu(),
               menu.getName_item(),
               menu.getDescription(),
               menu.getPrice(),
               menu.getRoute_image(),
               (long)menu.getCategory().getId(),
               menu.getMenu_ingredients().stream().map(
                       menuIngredient -> new MenuIngredientDto(
                               (long) menuIngredient.getId(),
                               menuIngredient.getQuantity(),
                               (long) menuIngredient.getIngredient().getId_ingredient()
                       )
               ).toList(),
               isAvailable(menu.getId_menu())
       );
    }
    @Transactional
    public List<MenuAddToTableDto> getAllMenuToCart(){
        return menuRepo.getAllMenu().stream()
                .map(menu -> new MenuAddToTableDto(
                        (long) menu.getId_menu(),
                        menu.getName_item(),
                        menu.getRoute_image(),
                        isAvailable(menu.getId_menu()),
                        menu.getPrice())
                ).toList();
    }

    public ProductDataDto getProductDataById(int id){
        Menu menu = menuRepo.findById(id).orElseThrow(() ->  new ResourceNotFoundException("menu","id",id));
        return new ProductDataDto((long) menu.getId_menu(), menu.getName_item(), menu.getPrice(), menu.getRoute_image());
    }
    public int addMenu(String name_menu, String description, double price, MultipartFile route_image, int id_category){
        if(name_menu.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 +]*$") && !name_menu.isEmpty() && price >0 && id_category>0){
            log.info("Se añadio un nuevo producto con nombre:{}", name_menu);
            return menuRepo.addMenuBy(name_menu, description, price, saveImage(0,route_image), id_category);
        }
        return 0;
    }
    private String saveImage(int id,MultipartFile img){
        if(img == null){
            return DEFAULT_IMAGE;
        }
        if(img.isEmpty()){
            return getMenuById(id).getRoute_image();
        }
        try{
            String so = System.getProperty("os.name").toLowerCase();
            String route = "";
            if(so.contains("win")){
                route = "C:/Users/DREP/Pictures/";
            }else if(so.contains("nux") || so.contains("nix")){
                route = "/home/renatob/Imagenes/Noly/";
            }
            File file = new File(route+img.getOriginalFilename());
            if(!file.exists()){
                file.mkdirs();
            }
            img.transferTo(file);
            return file.getName();

        }catch (IOException e){
            log.error(e.getMessage());
            return DEFAULT_IMAGE;

        }
    }
    public boolean editMenu(int id,String name_menu,String description,double price,int id_category,MultipartFile route_image){
        if(name_menu.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 +]*$") && !name_menu.isEmpty() && price >0 && id_category>0 && id>0){
            menuRepo.updateMenuBy(id,name_menu,description,price,saveImage(id,route_image),id_category);
            log.info("Se edito el menu con id: {}", id);
            return true;
        }
        log.warn("Los parametros para editar el menu no pasaron el matches");
        return false;

    }

    public boolean deleteMenu(int id){

        if(id <=0){
            return false;
        }
        menuRepo.deleteMenuBy(id);
        log.info("Se elimino el menu con id: {}", id);
        return false;
    }

}
