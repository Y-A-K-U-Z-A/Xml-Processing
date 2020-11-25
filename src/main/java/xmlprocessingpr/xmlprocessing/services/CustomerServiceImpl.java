package xmlprocessingpr.xmlprocessing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmlprocessingpr.xmlprocessing.domain.dtos.query5.CustomerCarDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.query5.CustomerDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CustomerSeedDto;
import xmlprocessingpr.xmlprocessing.domain.dtos.seed.CustomerSeedRootDto;
import xmlprocessingpr.xmlprocessing.domain.entities.Customer;
import xmlprocessingpr.xmlprocessing.domain.entities.Sale;
import xmlprocessingpr.xmlprocessing.domain.repositories.CustomerRepository;
import xmlprocessingpr.xmlprocessing.services.interfaces.CustomerService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedCustomers(String fileName, ModelMapper modelMapper) {
        try {
            System.out.println("Seeding..................................................................");
            JAXBContext context = JAXBContext.newInstance(CustomerSeedDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CustomerSeedDto customerSeedDto = (CustomerSeedDto) unmarshaller.unmarshal(new FileReader(fileName));
            List<CustomerSeedRootDto> customerSeedRootDtos = customerSeedDto.getCustomers();
            for (CustomerSeedRootDto c : customerSeedRootDtos) {
                Customer customer = new Customer(c.getName(), getBirthDate(c.getBirthDate()), c.getYoungDriver(), new LinkedHashSet<>());
                this.customerRepository.saveAndFlush(customer);
            }

            System.out.println("................................................................ successful.");
        } catch (FileNotFoundException | JAXBException e) {
            System.out.println("Oooops. Something went wrong with reading xml files OR seeding entities in db.");
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public void setSales(List<Sale> allSales) {
        List<Customer> customers = this.customerRepository.findAll();
        for (Customer customer : customers) {
            customer.setSales(getRandomSales(allSales));
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public void orderedCustomers(ModelMapper m) {
        try {
            List<Customer> customers = this.customerRepository.findAllOrderByBirthDate();
            JAXBContext context = JAXBContext.newInstance(CustomerSeedDto.class);
            Marshaller marshaller = context.createMarshaller();
            List<CustomerSeedRootDto> customerSeedRootDtos = new ArrayList<>();
            for (Customer customer : customers) {
                customerSeedRootDtos.add(m.map(customer, CustomerSeedRootDto.class));
            }
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new CustomerSeedDto(customerSeedRootDtos), new File("src/main/resources/exported-files/ordered-customers.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void customersWithCars(ModelMapper modelMapper) {
        List<Customer> all = this.customerRepository.findAllBySales();
        List<CustomerDto> customerDtos = new LinkedList<>();
        for (Customer c : all) {
            CustomerDto customerDto;
            try {
                AtomicReference<Double> total = new AtomicReference<>((double) 0);
                c.getSales().stream().forEach(e -> e.getCar().getParts().stream().forEach(p -> total.updateAndGet(v -> new Double((double) (v + p.getPrice())))));
                customerDto = new CustomerDto(c.getName(), c.getSales().size(), total.get());
            }catch (Exception e){
                customerDto = new CustomerDto(c.getName(), 0, 0);
            }
            customerDtos.add(customerDto);
        }
        CustomerCarDto customerCarDto = new CustomerCarDto(customerDtos);
        try {
            JAXBContext context = JAXBContext.newInstance(CustomerCarDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(customerCarDto, new File("src/main/resources/exported-files/customers-total-sales.xml"));
            System.out.println("Check --------------------> resources.exported-files");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private Set<Sale> getRandomSales(List<Sale> allSales) {
        Random random = new Random();
        Set<Sale> sales = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            int id = random.nextInt(allSales.size()) - 1;
            if (id == -1) {
                id = 0;
            }
            sales.add(allSales.get(id));
        }
        return sales;
    }

    private LocalDateTime getBirthDate(String birthDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(birthDate, dateTimeFormatter);
        return time;
    }


}
