package xmlprocessingpr.xmlprocessing.services.interfaces;

import org.modelmapper.ModelMapper;
import xmlprocessingpr.xmlprocessing.domain.entities.Car;
import xmlprocessingpr.xmlprocessing.domain.entities.Part;

import java.util.Set;

public interface PartService {
    void seedParts(String filePath, ModelMapper modelMapper);

    Set<Part> getRandomParts();

    void setCars(Set<Car> cars);
}
