package xmlprocessingpr.xmlprocessing.domain.dtos.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartDto {
    @XmlElement(name = "part")
    private List<PartsDto> parts;
    public PartDto(){}

    public PartDto(List<PartsDto> parts) {
        this.parts = parts;
    }

    public List<PartsDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsDto> parts) {
        this.parts = parts;
    }
}
