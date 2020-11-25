package xmlprocessingpr.xmlprocessing.services.interfaces;

import org.modelmapper.ModelMapper;
import xmlprocessingpr.xmlprocessing.domain.entities.Customer;
import xmlprocessingpr.xmlprocessing.domain.entities.Sale;

import java.util.List;

public interface SaleService {
    void seedSales(List<Customer> customers);

    List<Sale> getAllSales();

    void salesDiscounts(ModelMapper modelMapper);
}
