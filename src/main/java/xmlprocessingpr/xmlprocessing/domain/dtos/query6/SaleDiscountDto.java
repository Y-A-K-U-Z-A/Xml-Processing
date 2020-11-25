package xmlprocessingpr.xmlprocessing.domain.dtos.query6;

import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CarSeedRootDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountDto {
    @XmlElement(name = "car")
    private CarSeedRootDto car;
    @XmlElement(name = "customer-name")
    private String customerName;
    @XmlElement
    private double discount;
    @XmlElement
    private double price;
    @XmlElement(name = "price-with-discount")
    private double priceWithDiscount;

    public SaleDiscountDto(CarSeedRootDto car, String customerName, double discount, double price, double priceWithDiscount) {
        this.car = car;
        this.customerName = customerName;
        this.discount = discount;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public SaleDiscountDto() {
    }

    public CarSeedRootDto getCar() {
        return car;
    }

    public void setCar(CarSeedRootDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
