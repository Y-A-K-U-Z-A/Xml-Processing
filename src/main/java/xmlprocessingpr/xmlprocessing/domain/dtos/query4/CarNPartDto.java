package xmlprocessingpr.xmlprocessing.domain.dtos.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarNPartDto {
    @XmlElement(name = "car")
    private List<CarRootDto> cars;

    public CarNPartDto(){}

    public CarNPartDto(List<CarRootDto> cars) {
        this.cars = cars;
    }

    public List<CarRootDto> getCars() {
        return cars;
    }

    public void setCars(List<CarRootDto> cars) {
        this.cars = cars;
    }
}
