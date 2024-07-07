package RockieProject.backend.StudySession.DTO;

import RockieProject.backend.StudySession.Domain.Method;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudySessionDTO {
    @NotNull
    private String name;
    @NotNull
    @Size(min = 1, max = 60)//We don't have more than an hour because it's not healthy
    private int study_time;
    @Size(min = 1, max = 30)//maybe we need to consider a big break attribute
    @NotNull
    private int break_time;
    @NotNull
    @Size(min = 0, max = 4)
    private int repetitions;
    @NotNull
    private Method method;
}
