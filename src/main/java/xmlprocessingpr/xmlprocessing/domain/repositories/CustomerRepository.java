package xmlprocessingpr.xmlprocessing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xmlprocessingpr.xmlprocessing.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(long id);
    @Query("select c from Customer c order by c.birthDate asc, c.isYoungDriver desc")
    List<Customer> findAllOrderByBirthDate();
    @Query("select c from Customer c where c.sales.size >=1 ")
    List<Customer> findAllBySales();
}
