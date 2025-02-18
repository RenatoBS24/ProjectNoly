package com.projectnoly.Services;

import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Repositories.MenuRepo;
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
public class MenuService {
    private final MenuRepo menuRepo;
    private final Logger log = LoggerFactory.getLogger(MenuService.class);
    @Autowired
    public MenuService(MenuRepo menuRepo){
        this.menuRepo = menuRepo;
    }
    @Transactional
    public List<Menu> getAllMenu(){
        return menuRepo.getAllMenu();
    }
    public Menu getMenuById(int id){
        return menuRepo.findById(id).orElse(null);
    }
    public int addMenu(String name_menu, String description, double price, MultipartFile route_image, int id_category){
        if(name_menu.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 +]*$") && !name_menu.isEmpty() && price >0 && id_category>0){
            log.info("Se añadio un nuevo producto con nombre:{}", name_menu);
            return menuRepo.addMenuBy(name_menu, description, price, saveImage(route_image), id_category);
        }
        return 0;
    }
    private String saveImage(MultipartFile img){
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
                log.info("--------------------------------------------");
                log.warn(e.getMessage());
                log.info("--------------------------------------------");
            }
        }
        return "";
    }
    public boolean editMenu(int id,String name_menu,String description,double price,int id_category,MultipartFile route_image){
        if(name_menu.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 +]*$") && !name_menu.isEmpty() && price >0 && id_category>0 && id>0){
            menuRepo.updateMenuBy(id,name_menu,description,price,saveImage(route_image),id_category);
            log.info("Se edito el menu con id: {}", id);
            return true;
        }
        log.warn("Los parametros para editar el menu no pasaron el matches");
        return false;

    }

    public boolean deleteMenu(int id){
        try {
            if(id>0){
                menuRepo.deleteMenuBy(id);
                return true;
            }else{
                log.warn("el id recibido para eliminar el menu es igual o menor que 0");
                return false;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return false;
    }

}
