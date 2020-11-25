package xmlprocessingpr.xmlprocessing.services.interfaces;

import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;
import xmlprocessingpr.xmlprocessing.domain.entities.Supplier;

import javax.xml.bind.JAXBException;

public interface SupplierService {
    void seedSuppliers(String filePath, ModelMapper modelMapper) throws JAXBException;
    Supplier getRandomSupplier();

    void localSuppliers(ModelMapper modelMapper);

}
