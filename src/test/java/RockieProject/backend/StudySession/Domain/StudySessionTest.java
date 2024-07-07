package RockieProject.backend.StudySession.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudySessionTest {
    StudySession studySession;

    @BeforeEach
    public void setUp() {
        studySession = new StudySession();
        studySession.setStudy_time(60);
        studySession.setBreak_time(10);
        studySession.setMethod(Method.POMODORO);
    }

    @Test
    public void testStudySession() {
        assertEquals(60,studySession.getStudy_time());
        assertEquals(10,studySession.getBreak_time());
        assertEquals(Method.POMODORO,studySession.getMethod());
    }
}
