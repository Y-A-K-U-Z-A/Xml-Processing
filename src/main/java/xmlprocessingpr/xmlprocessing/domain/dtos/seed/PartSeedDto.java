package xmlprocessingpr.xmlprocessing.domain.dtos.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedDto {
    @XmlElement(name = "part")
    private List<PartSeedRootDto> parts;

    public PartSeedDto(){}

    public PartSeedDto(List<PartSeedRootDto> parts) {
        this.parts = parts;
    }

    public List<PartSeedRootDto> getParts() {
        return parts;
    }

    public void setParts(List<PartSeedRootDto> parts) {
        this.parts = parts;
    }
}
