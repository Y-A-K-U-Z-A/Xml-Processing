package xmlprocessingpr.xmlprocessing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmlprocessingpr.xmlprocessing.domain.dtos.query2.CarDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query2.CarOrderDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query4.CarNPartDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query4.CarRootDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query4.PartDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query4.PartsDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CarSeedDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CarSeedRootDto;
import xmlprocessingpr.xmlprocessing.domain.entities.Car;
import xmlprocessingpr.xmlprocessing.domain.entities.Part;
import xmlprocessingpr.xmlprocessing.domain.repositories.CarRepository;
import xmlprocessingpr.xmlprocessing.services.interfaces.CarService;
import xmlprocessingpr.xmlprocessing.services.interfaces.PartService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartService partService) {
        this.carRepository = carRepository;
        this.partService = partService;
    }

    @Override
    public void seedCars(String fileName, ModelMapper modelMapper) {
        try {
            System.out.println("Seeding..................................................................");
            JAXBContext context = JAXBContext.newInstance(CarSeedDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CarSeedDto carSeedDto = (CarSeedDto) unmarshaller.unmarshal(new FileReader(fileName));
            List<CarSeedRootDto> carsSeedRootDtos = carSeedDto.getCars();
            for (CarSeedRootDto c : carsSeedRootDtos) {
                Car car = modelMapper.map(c, Car.class);
                car.setParts(this.partService.getRandomParts());
                this.carRepository.saveAndFlush(car);
            }

            System.out.println("............................................................. successful.");
        } catch (JAXBException | FileNotFoundException e) {
            System.out.println("Oooops. Something went wrong with xml file reading or mapping objects in PartServiceImpl.");
        }

    }

    @Override
    public Set<Car> getAllCars() {
        return new LinkedHashSet<>(this.carRepository.findAll());
    }

    @Override
    public void toyotaModels(ModelMapper modelMapper) {

        List<Car> toyotas = this.carRepository.findByMakeOrderByModelAsc("Toyota");
        List<CarDto> cars = new ArrayList<>();
        for (Car toyota : toyotas) {
            cars.add(modelMapper.map(toyota, CarDto.class));
        }
        CarOrderDto carOrderDto = new CarOrderDto(cars);
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(CarOrderDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(carOrderDto, new File("src/main/resources/exported-files/toyota-cars.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void carsAndParts(ModelMapper modelMapper) {
        List<Car> all = this.carRepository.findAll();

        List<CarRootDto> cars = new ArrayList<>();
        for (Car car : all) {
            List<PartsDto> partsDtos = new ArrayList<>();
            for (Part part : car.getParts()) {
                partsDtos.add(modelMapper.map(part, PartsDto.class));
            }
            PartDto partDto = new PartDto(partsDtos);
            cars.add(new CarRootDto(car.getMake(), car.getModel(), car.getTravelledDistance(), partDto));
        }
        CarNPartDto carNPartDto = new CarNPartDto(cars);
        try {
            JAXBContext context = JAXBContext.newInstance(CarNPartDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(carNPartDto, new File("src/main/resources/exported-files/cars-and-parts.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
