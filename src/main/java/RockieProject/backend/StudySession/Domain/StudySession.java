package RockieProject.backend.StudySession.Domain;

import RockieProject.backend.Student.Domain.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "repetitions")
    private int repetitions;

    @Column(name = "study_time")
    private int study_time; //Minutes

    @Column(name = "break_time")
    private int break_time; //Minutes

    @Enumerated(EnumType.STRING)
    private Method method;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id") //define the foreign key
    private Student student;

}
