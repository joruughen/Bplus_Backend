package RockieProject.backend.Accessory.Domain;

import RockieProject.backend.Rockie.Domain.Rockie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    private Type_Accessory type;

    @ManyToMany(mappedBy = "accessories")
    private List<Rockie> rockies;





}
