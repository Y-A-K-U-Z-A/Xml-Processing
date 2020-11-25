package xmlprocessingpr.xmlprocessing.domain.dtos.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDto {
    @XmlElement(name = "car")
    private List<CarSeedRootDto> cars;

    public CarSeedDto(List<CarSeedRootDto> cars) {
        this.cars = cars;
    }
    public CarSeedDto(){}

    public List<CarSeedRootDto> getCars() {
        return cars;
    }

    public void setCars(List<CarSeedRootDto> cars) {
        this.cars = cars;
    }
}
