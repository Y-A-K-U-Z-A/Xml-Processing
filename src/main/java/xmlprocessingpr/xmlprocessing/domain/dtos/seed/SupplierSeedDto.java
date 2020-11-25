package xmlprocessingpr.xmlprocessing.domain.dtos.seed;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedDto {
    @XmlElement(name = "supplier")
    private List<SupplierSeedRootDto> suppliers;

    public SupplierSeedDto(){}

    public SupplierSeedDto(List<SupplierSeedRootDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SupplierSeedRootDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierSeedRootDto> suppliers) {
        this.suppliers = suppliers;
    }
}
