package xmlprocessingpr.xmlprocessing.domain.dtos.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierDto {
    @XmlElement(name = "supplier")
    private List<SupplierRootDto> suppliers;

    public SupplierDto(List<SupplierRootDto> suppliers) {
        this.suppliers = suppliers;
    }

    public SupplierDto() {
    }

    public List<SupplierRootDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierRootDto> suppliers) {
        this.suppliers = suppliers;
    }
}
