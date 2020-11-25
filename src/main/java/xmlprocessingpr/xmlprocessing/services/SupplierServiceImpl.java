package xmlprocessingpr.xmlprocessing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmlprocessingpr.xmlprocessing.domain.dtos.query3.SupplierDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query3.SupplierRootDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.SupplierSeedDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.SupplierSeedRootDto;
import xmlprocessingpr.xmlprocessing.domain.entities.Supplier;
import xmlprocessingpr.xmlprocessing.domain.repositories.SupplierRepository;
import xmlprocessingpr.xmlprocessing.services.interfaces.SupplierService;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedSuppliers(String filePath, ModelMapper modelMapper) {
        try {
            System.out.println("Seeding..................................................................");
            JAXBContext context = JAXBContext.newInstance(SupplierSeedDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            SupplierSeedDto supplierSeedDto = (SupplierSeedDto) unmarshaller.unmarshal(new FileReader(filePath));
            List<SupplierSeedRootDto> suppliers = supplierSeedDto.getSuppliers();

            for (SupplierSeedRootDto supplier : suppliers) {
                this.supplierRepository.saveAndFlush(modelMapper.map(supplier, Supplier.class));
            }
            System.out.println("................................................................ successful.");
        } catch (FileNotFoundException | JAXBException e) {
            System.out.println("Oooops. Something went wrong with reading xml files OR seeding entities in db.");
        }
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        int id = random.nextInt((int) this.supplierRepository.count())+1;
        return this.supplierRepository.findById(id);
    }

    @Override
    public void localSuppliers(ModelMapper modelMapper) {
        List<Supplier> all = this.supplierRepository.findAllByImporterIsFalse();
        List<SupplierRootDto> supplierRootDtos = all.stream()
                                .map(e -> modelMapper.map(e, SupplierRootDto.class))
                                .collect(Collectors.toList());
        SupplierDto s = new SupplierDto(supplierRootDtos);

        try {
            JAXBContext context = JAXBContext.newInstance(SupplierDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(s, new File("src/main/resources/exported-files/local-suppliers.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
