package RockieProject.backend.StudySession.DTO;

import RockieProject.backend.StudySession.Domain.Method;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudySessionDTOWITHOUTUSER {
    private Long id;
    private String name;
    private int study_time;
    private int break_time;
    private int repetitions;
    private Method method;
}
