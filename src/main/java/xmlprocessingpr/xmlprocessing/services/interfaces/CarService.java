package xmlprocessingpr.xmlprocessing.services.interfaces;

import org.modelmapper.ModelMapper;
import xmlprocessingpr.xmlprocessing.domain.entities.Car;

import java.util.Set;

public interface CarService {
    void seedCars(String fileName, ModelMapper modelMapper);

    Set<Car> getAllCars();

    void toyotaModels(ModelMapper modelMapper);

    void carsAndParts(ModelMapper modelMapper);
}
