package com.projectnoly.Services;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.SaleMenu;
import com.projectnoly.Repositories.SaleMenuRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleMenuService {
    private final SaleMenuRepo saleMenuRepo;
    public SaleMenuService(SaleMenuRepo saleMenuRepo) {
        this.saleMenuRepo = saleMenuRepo;
    }

    public void createSaleMenu(List<Product> productList,int id_sale){
        for (Product product:productList){
            addSaleMenu(id_sale,product.getId_product(),product.getQuantity());
        }
    }
    private void addSaleMenu(int p_id_sale, int p_id_menu, int p_quantity) {
        saleMenuRepo.addSaleMenu(p_id_sale, p_id_menu, p_quantity);
    }

    @Transactional
    public List<SaleMenu> getSaleMenuByid(int p_id_sale) {
        return saleMenuRepo.getSaleMenuByid(p_id_sale);
    }
    @Transactional
    public List<SaleMenu> getSaleMenuBy1OHours() {
        return saleMenuRepo.getSaleMenuBy1OHours();
    }

    public void deleteSaleMenu(String code,int id_sale) {
        if (code.equals("1234")) {
            saleMenuRepo.deleteSaleMenu(id_sale);
        }
    }


}
