package RockieProject.backend.StudySession.DTO;

import RockieProject.backend.StudySession.Domain.Method;
import lombok.Data;

@Data
public class StudySessionDTOPatch {
    //why not reuse studysessiondto? because it can be null in patch
    //I need to add constraints like not negative
    private String name;
    private int study_time;
    private int break_time;
    private int repetitions;
    private Method method;
}
