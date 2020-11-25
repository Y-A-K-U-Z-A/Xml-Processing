package xmlprocessingpr.xmlprocessing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xmlprocessingpr.xmlprocessing.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Part findById(long id);
}
