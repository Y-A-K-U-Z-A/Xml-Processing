package xmlprocessingpr.xmlprocessing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xmlprocessingpr.xmlprocessing.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findById(long id);
}
