package xmlprocessingpr.xmlprocessing.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xmlprocessingpr.xmlprocessing.domain.entities.BaseEntity;
import xmlprocessingpr.xmlprocessing.domain.entities.Part;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends BaseEntity {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private boolean isImporter;
    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    @Getter @Setter
    private Set<Part> parts;
}
