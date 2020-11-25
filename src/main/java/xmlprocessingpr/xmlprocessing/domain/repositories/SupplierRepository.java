package xmlprocessingpr.xmlprocessing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xmlprocessingpr.xmlprocessing.domain.entities.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findById(long id);

    @Query("select s from Supplier s where s.isImporter = false")
    List<Supplier> findAllByImporterIsFalse();
}
