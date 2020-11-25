package xmlprocessingpr.xmlprocessing.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity{
    @Getter @Setter
    private String make;
    @Getter @Setter
    private String model;
    @Getter @Setter
    private long travelledDistance;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "cars")
    @Getter @Setter
    private Set<Part> parts;
    @OneToOne(mappedBy = "car")
    private Sale sale;
}