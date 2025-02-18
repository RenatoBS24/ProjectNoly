package com.projectnoly.Services;

import com.projectnoly.Model.MySql.Sale;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Repositories.SaleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepo saleRepo;
    private final TablesService tablesService;
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(SaleProductService.class);

    @Autowired
    public SaleService(SaleRepo saleRepo, TablesService tablesService, EmployeeService employeeService) {
        this.saleRepo = saleRepo;
        this.tablesService = tablesService;
        this.employeeService = employeeService;
    }
    @Transactional
    public List<Sale> getAllSales() {
        return saleRepo.getAllSales();
    }

    public int addSale(String pay_method,int id_employee,String valid_sale,int id_table){
        try {
            double total = tablesService.getTotal(id_table);
            if(valid_sale.equals("true")){
                total = 0;
            }
            if(pay_method.equalsIgnoreCase("Tarjeta")){
                total = total*1.05;
            }
            log.info("Se ha guardado la venta exitosamente");
            return saleRepo.addSale(total,pay_method,employeeService.getEmployeeById(id_employee).getId_employee());
        } catch (Exception e) {
            log.warn(e.getMessage());
            return 0;
        }
    }
    public double getTotalSales(){
        return saleRepo.getTotalSales();
    }
    @Transactional
    public double getTotalByMethod(String method){
        double total = 0;
        List<Sale> saleList = saleRepo.getSalesNow();
        for(Sale sale : saleList){
            if(sale.getPay_method().equalsIgnoreCase(method)){
                total += sale.getTotal();
            }
        }
        return total;
    }
    public void deleteSale(User user, String code, int id_sale){
        if (code.equals("1234")) {
            saleRepo.deleteSale(id_sale);
            log.info("El usuario {} ha eliminado una venta con id: {}", user.getUsername(), id_sale);
        }
    }
}
