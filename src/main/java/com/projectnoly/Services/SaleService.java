package com.projectnoly.Services;

import com.projectnoly.DTO.Sale.PaymentMethodDto;
import com.projectnoly.DTO.Sale.SaleResponseDto;
import com.projectnoly.Model.MySql.*;
import com.projectnoly.Repositories.PaymentMethodRepo;
import com.projectnoly.Repositories.SaleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SaleService {

    private final SaleRepo saleRepo;
    private final TablesService tablesService;
    private final EmployeeService employeeService;
    private final PaymentMethodRepo paymentMethodRepo;
    private final Logger log = LoggerFactory.getLogger(SaleProductService.class);

    @Autowired
    public SaleService(SaleRepo saleRepo, TablesService tablesService, EmployeeService employeeService,PaymentMethodRepo paymentMethodRepo) {
        this.saleRepo = saleRepo;
        this.tablesService = tablesService;
        this.employeeService = employeeService;
        this.paymentMethodRepo = paymentMethodRepo;
    }
    @Transactional
    public List<Sale> getAllSales() {
        return saleRepo.getAllSales();
    }

    @Transactional
    public Page<SaleResponseDto> getAllSalesPage(Pageable pageable){
        List<Long> salesIdList = saleRepo.salesIdList(pageable);
        List<SaleResponseDto> saleList = saleRepo.findAll(salesIdList).stream().
                map(sale -> new SaleResponseDto(
                        (long)sale.getId_sale(),
                        sale.getDate_sale(),
                        sale.getTotal(),
                        sale.getSalePaymentMethodList().stream().map(spm -> new PaymentMethodDto(
                                spm.getPaymentMethod().getIdPaymentMethod(),
                                spm.getPaymentMethod().getName(),
                                spm.getTotal()
                        )).toList(),
                        (long)sale.getEmployee().getId_employee(),
                        sale.getEmployee().getName()
                )).toList();
        return new PageImpl<>(saleList,pageable,saleRepo.count());
    }
    public int addSale(String pay_method, int idEmployee, String valid_sale, int id_table){
        try {
            double total = tablesService.getTotal(id_table);
            if(valid_sale.equals("true")){
                total = 0;
            }
            if(pay_method.equalsIgnoreCase("Tarjeta")){
                total = total*1.05;
            }
            Employee employee = employeeService.getEmployeeById(idEmployee);
            Sale sale = new Sale(0,LocalDateTime.now(),total,"active","efectivo",employee,new ArrayList<>(),new ArrayList<>());
            Sale newSale = saleRepo.save(sale);
            List<SalePaymentMethod> salePaymentMethodList = new ArrayList<>();
            PaymentMethod paymentMethod = paymentMethodRepo.findByName(pay_method);
            SalePaymentMethodId salePaymentMethodId = new SalePaymentMethodId(newSale.getId_sale(),paymentMethod.getIdPaymentMethod());
            salePaymentMethodList.add(new SalePaymentMethod(salePaymentMethodId, newSale,paymentMethod,total));
            newSale.setSalePaymentMethodList(salePaymentMethodList);
            saleRepo.save(newSale);
            log.info("Se ha guardado la venta exitosamente");
            return newSale.getId_sale();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int addSale(int idTable, int idEmployee, Map<String,Double> payMethods){
        double total = tablesService.getTotal(idTable);
        Employee employee = employeeService.getEmployeeById(idEmployee);
        double totalPayMethods = 0;
        for(Double amount : payMethods.values()){
            totalPayMethods+= amount;
        }
        if(total != totalPayMethods){
            throw new IllegalArgumentException("La suma de los montos tiene que ser igual al total");

        }
        Sale sale = new Sale(0, LocalDateTime.now(),total,"active","efectivo",employee,new ArrayList<>(),new ArrayList<>());
        Sale newSale = saleRepo.save(sale);
        List<SalePaymentMethod> salePaymentMethodList = new ArrayList<>();
        for(Map.Entry<String,Double> entry : payMethods.entrySet()){
            if (entry.getValue() > 0 ) {
                PaymentMethod paymentMethod = paymentMethodRepo.findByName(entry.getKey());
                SalePaymentMethodId paymentMethodId = new SalePaymentMethodId(newSale.getId_sale(),paymentMethod.getIdPaymentMethod());
                salePaymentMethodList.add(new SalePaymentMethod(paymentMethodId,newSale,paymentMethod, entry.getValue()));
            }
        }
        newSale.setSalePaymentMethodList(salePaymentMethodList);
        saleRepo.save(newSale);
        return newSale.getId_sale();
    }
    public double getTotalSales(){
        return saleRepo.getTotalSales();
   }
    @Transactional
    public double getTotalByMethod(String method){
        Long paymentMethodId = paymentMethodRepo.findByName(method).getIdPaymentMethod();
        double total = 0;
        List<Sale> saleList = saleRepo.getSalesNow();
        for(Sale sale : saleList){
            for(SalePaymentMethod salePaymentMethod : sale.getSalePaymentMethodList()){
                if(Objects.equals(salePaymentMethod.getPaymentMethod().getIdPaymentMethod(), paymentMethodId)){
                    total += salePaymentMethod.getTotal();
                }
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
