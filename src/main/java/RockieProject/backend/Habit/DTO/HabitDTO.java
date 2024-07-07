package RockieProject.backend.Habit.DTO;

import RockieProject.backend.Student.Domain.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
public class HabitDTO {
    private String name;
    private String description;
    private int relevance;
}
