package xmlprocessingpr.xmlprocessing.services.interfaces;

import org.modelmapper.ModelMapper;
import xmlprocessingpr.xmlprocessing.domain.entities.Customer;
import xmlprocessingpr.xmlprocessing.domain.entities.Sale;

import java.util.List;

public interface CustomerService {
    void seedCustomers(String fileName, ModelMapper modelMapper);

    List<Customer> getAllCustomers();

    void setSales(List<Sale> allSales);

    void orderedCustomers(ModelMapper m);

    void customersWithCars(ModelMapper modelMapper);
}
