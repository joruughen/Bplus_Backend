package RockieProject.backend.Habit.Domain;


import RockieProject.backend.Student.Domain.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "relevance")
    private int relevance;

    @Column(name = "completion")
    private boolean completion;

    @Column(name = "progression")
    private int progression;

    @Column(name = "progression_segment")
    private int progression_segment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id")
    private Student student;


}
