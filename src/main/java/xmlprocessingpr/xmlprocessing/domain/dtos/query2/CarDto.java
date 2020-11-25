package xmlprocessingpr.xmlprocessing.domain.dtos.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDto {
    @XmlAttribute
    private long id;
    @XmlAttribute
    private String make;
    @XmlAttribute(name = "travelled-distance")
    private long travelledDistance;

    public CarDto(){}

    public CarDto(long id, String make, long travelledDistance) {
        this.id = id;
        this.make = make;
        this.travelledDistance = travelledDistance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
