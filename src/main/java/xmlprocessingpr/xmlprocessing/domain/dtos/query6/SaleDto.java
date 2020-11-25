package xmlprocessingpr.xmlprocessing.domain.dtos.query6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDto {
    @XmlElement(name = "sale")
    private List<SaleDiscountDto> saleDiscountDtos;

    public SaleDto(List<SaleDiscountDto> saleDiscountDtos) {
        this.saleDiscountDtos = saleDiscountDtos;
    }
    public SaleDto(){}

    public List<SaleDiscountDto> getSaleDiscountDtos() {
        return saleDiscountDtos;
    }

    public void setSaleDiscountDtos(List<SaleDiscountDto> saleDiscountDtos) {
        this.saleDiscountDtos = saleDiscountDtos;
    }

}
