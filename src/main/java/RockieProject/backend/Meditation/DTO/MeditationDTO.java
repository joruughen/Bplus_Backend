package RockieProject.backend.Meditation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeditationDTO {
    @NotNull
    private int meditation_time;
}
