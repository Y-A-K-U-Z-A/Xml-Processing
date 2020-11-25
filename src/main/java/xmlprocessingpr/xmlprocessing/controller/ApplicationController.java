package xmlprocessingpr.xmlprocessing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import xmlprocessingpr.xmlprocessing.components.ApplicationBeanConfiguration;
import xmlprocessingpr.xmlprocessing.components.Constant;
import xmlprocessingpr.xmlprocessing.services.interfaces.*;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Controller
public class ApplicationController implements CommandLineRunner {
    private final ConfigurableApplicationContext cac;
    private final CarService carService;
    private final Constant constant = new Constant();
    private final Scanner sc = new Scanner(System.in);
    private final SupplierService supplierService;
    private final ApplicationBeanConfiguration abc;
    private final PartService partService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public ApplicationController(ConfigurableApplicationContext cac, CarService carService, SupplierService supplierService, ApplicationBeanConfiguration abc, PartService partService, CustomerService customerService, SaleService saleService) {
        this.cac = cac;
        this.carService = carService;
        this.supplierService = supplierService;
        this.abc = abc;
        this.partService = partService;
        this.customerService = customerService;
        this.saleService = saleService;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(constant.WELCOMING_STRING);
        System.out.println(constant.QUERIES_STRING);
        System.out.println(constant.EXITING_STRING);

        String line = sc.nextLine();
        while (!"exit".equals(line)){
            switch (line){
                case "0":
                    seed();
                    break;
                case "1":
                    this.customerService.orderedCustomers(this.abc.modelMapper());
                    break;
                case "2":
                    this.carService.toyotaModels(this.abc.modelMapper());
                    break;
                case "3":
                    this.supplierService.localSuppliers(this.abc.modelMapper());
                    break;
                case "4":
                    this.carService.carsAndParts(this.abc.modelMapper());
                    break;
                case "5":
                    this.customerService.customersWithCars(this.abc.modelMapper());
                    break;
                case "6":
                    this.saleService.salesDiscounts(this.abc.modelMapper());
                    break;

            }

            System.out.println(constant.QUERIES_STRING);
            System.out.println(constant.EXITING_STRING);
            line = sc.nextLine();
        }
        this.cac.close();
    }

    private void seed() throws JAXBException {
//        this.supplierService.seedSuppliers(constant.SUPPLIERS_PATH_FILE, abc.modelMapper());
//        this.partService.seedParts(constant.PARTS_PATH_FILE, abc.modelMapper());
//        this.carService.seedCars(constant.CARS_PATH_FILE, abc.modelMapper());
//        this.customerService.seedCustomers(constant.CUSTOMERS_PATH_FILE, abc.modelMapper());
//        this.saleService.seedSales(this.customerService.getAllCustomers());
//        this.partService.setCars(this.carService.getAllCars());
//        this.customerService.setSales(this.saleService.getAllSales());

    }
}
