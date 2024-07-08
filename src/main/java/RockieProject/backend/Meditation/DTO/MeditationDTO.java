package RockieProject.backend.Meditation.DTO;

import RockieProject.backend.Meditation.Domain.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeditationDTO {
    @NotNull
    private int meditation_time;
    @NotNull
    private Status status; // Agrega este campo
}
