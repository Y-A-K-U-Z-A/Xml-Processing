package xmlprocessingpr.xmlprocessing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.PartSeedDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.PartSeedRootDto;
import xmlprocessingpr.xmlprocessing.domain.entities.Car;
import xmlprocessingpr.xmlprocessing.domain.entities.Part;
import xmlprocessingpr.xmlprocessing.domain.repositories.PartRepository;
import xmlprocessingpr.xmlprocessing.services.interfaces.PartService;
import xmlprocessingpr.xmlprocessing.services.interfaces.SupplierService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final SupplierService supplierService;
    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts(String filePath, ModelMapper modelMapper) {
        try {
            System.out.println("Seeding..................................................................");
            JAXBContext context = JAXBContext.newInstance(PartSeedDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            PartSeedDto partSeedDto = (PartSeedDto) unmarshaller.unmarshal(new FileReader(filePath));
            for (PartSeedRootDto p : partSeedDto.getParts()) {
                Part part = modelMapper.map(p, Part.class);
                part.setSupplier(this.supplierService.getRandomSupplier());
                this.partRepository.saveAndFlush(part);
            }
            System.out.println("............................................................. successful.");
        } catch (JAXBException | FileNotFoundException e) {
            System.out.println("Oooops. Something went wrong with xml file reading or mapping objects in PartServiceImpl.");
        }


    }

    @Override
    public Set<Part> getRandomParts() {
        Random random = new Random();
        int bound = random.nextInt(10)+10;
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < bound; i++) {
            int id = random.nextInt((int) this.partRepository.count())+1;
            parts.add(this.partRepository.findById(id));
        }
        return parts;
    }

    @Override
    public void setCars(Set<Car> cars) {
        List<Part> all = this.partRepository.findAll();
        for (Part part : all) {
            part.setCars(getRandomCars(cars));
            this.partRepository.saveAndFlush(part);
        }
    }

    private Set<Car> getRandomCars(Set<Car> cars) {
        Car[] c = cars.toArray(Car[]::new);

        Random random  = new Random();
        int bound = random.nextInt(10)+10;
        Set<Car> carsSet = new HashSet<>();
        for (int i = 0; i < bound; i++) {
            int id = random.nextInt(cars.size())-1;
            if (id == -1 ){
                id = 0;
            }
            carsSet.add(c[id]);
        }
        return carsSet;
    }
}
