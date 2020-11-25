package xmlprocessingpr.xmlprocessing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmlprocessingpr.xmlprocessing.domain.dtos.query4.CarRootDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query6.SaleDiscountDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query6.SaleDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CarSeedRootDto;
import xmlprocessingpr.xmlprocessing.domain.entities.Car;
import xmlprocessingpr.xmlprocessing.domain.entities.Customer;
import xmlprocessingpr.xmlprocessing.domain.entities.Sale;
import xmlprocessingpr.xmlprocessing.domain.repositories.SaleRepository;
import xmlprocessingpr.xmlprocessing.services.interfaces.CarService;
import xmlprocessingpr.xmlprocessing.services.interfaces.SaleService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final int[] discounts = new int[]{0, 5, 10, 15, 20, 30, 40, 50};

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService) {
        this.saleRepository = saleRepository;
        this.carService = carService;
    }

    @Override
    public void seedSales(List<Customer> customers) {
        System.out.println("Seeding..................................................................");
        Car[] allCars = this.carService.getAllCars().toArray(Car[]::new);
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            int idCustomer = random.nextInt(customers.size()) - 1;
            if (idCustomer == -1) {
                idCustomer = 0;
            }
            int idCar = random.nextInt(allCars.length) - 1;
            if (idCar == -1) {
                idCar = 0;
            }
            Car car = allCars[idCar];
            int idDiscount = random.nextInt(this.discounts.length) - 1;
            if (idDiscount == -1) {
                idDiscount = 0;
            }
            Sale sale = new Sale(this.discounts[idDiscount], car, customers.get(idCustomer));
            this.saleRepository.saveAndFlush(sale);
        }
        System.out.println("............................................................. successful.");
    }

    @Override
    public List<Sale> getAllSales() {
        return this.saleRepository.findAll();
    }

    @Override
    public void salesDiscounts(ModelMapper modelMapper) {
        List<Sale> all = this.saleRepository.findAll();
        List<SaleDiscountDto> saleDiscountDtos = new ArrayList<>();
        for (Sale sale : all) {
            CarSeedRootDto carRootDto = modelMapper.map(sale.getCar(), CarSeedRootDto.class);
            AtomicReference<Double> price = new AtomicReference<>((double) 0);

            double sumWithDiscount = price.get() * (1 - sale.getDiscountPercentage());

            sale.getCar().getParts().stream().forEach(e -> price.updateAndGet(v -> new Double((double) (v + e.getPrice()))));
            saleDiscountDtos.add(new SaleDiscountDto(carRootDto, sale.getCustomer().getName(), sale.getDiscountPercentage(),
                    price.get(), sumWithDiscount));
        }
        SaleDto saleDto = new SaleDto(saleDiscountDtos);
        try {
            JAXBContext context = JAXBContext.newInstance(SaleDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(saleDto, new File("src/main/resources/exported-files/sales-discounts.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
