package RockieProject.backend.Rockie.Domain;

import RockieProject.backend.Accessory.Domain.Accessory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Rockie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private int level;

    @Column
    private String face;

    @Column
    private String accessory;

    @Column
    private String upper_accessory;

    @Column
    private String lower_accessory;



    @ManyToMany()
    @JoinTable(name = "rockie_accessory",
        joinColumns = @JoinColumn(name = "rockie_id"),
        inverseJoinColumns = @JoinColumn(name = "accessory_id"))
    private List<Accessory> accessories;

}
