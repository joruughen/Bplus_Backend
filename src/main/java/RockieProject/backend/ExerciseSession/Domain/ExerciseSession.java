package RockieProject.backend.ExerciseSession.Domain;

import RockieProject.backend.Student.Domain.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private Category category;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id")
    private Student student;
}
