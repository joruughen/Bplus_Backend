package RockieProject.backend.ExerciseSession.DTO;

import RockieProject.backend.ExerciseSession.Domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ExerciseSessionInDTO {

    private String name;
    private String description;
    private Category category;
    private String url;
}
