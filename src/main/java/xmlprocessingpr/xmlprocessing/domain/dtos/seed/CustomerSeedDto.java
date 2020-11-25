package xmlprocessingpr.xmlprocessing.domain.dtos.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedDto {
    @XmlElement(name = "customer")
    private List<CustomerSeedRootDto> customers;

    public CustomerSeedDto(){}

    public CustomerSeedDto(List<CustomerSeedRootDto> customers) {
        this.customers = customers;
    }

    public List<CustomerSeedRootDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSeedRootDto> customers) {
        this.customers = customers;
    }
}
