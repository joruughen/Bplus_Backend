package RockieProject.backend.StudySession.Application;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.Domain.StudySession;
import RockieProject.backend.StudySession.Infraestructure.StudySessionRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "jorge.melgarejo@utec.edu.pe", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "STUDENT")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudySessionControllerSecurityTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Long studySessionId;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        studySessionRepository.deleteAll();
        studentRepository.deleteAll();
        var res = mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\": \"jorge.melgarejo@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jorgito\"}"))
                .andReturn();

        JSONObject json = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = json.getString("token");

        StudySession studySession = new StudySession();
        studySession.setName("Test Study Session");
        studySession.setStudent(studentRepository.findStudentByEmail("jorge.melgarejo@utec.edu.pe"));
        studySessionRepository.save(studySession);
        studySessionId = studySessionRepository.save(studySession).getId();
    }

    @Test
    public void whenUserAccessStudySession_thenOk() throws Exception {
        mockMvc.perform(get("/study_session/" + studySessionId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessStudySession_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/study_session/" + studySessionId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserAccessAllStudySession_thenOk() throws Exception {
        mockMvc.perform(get("/study_session/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessAllStudySession_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/study_session/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserCreatesStudySession_thenCreated() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "New Study Session");

        mockMvc.perform(post("/study_session/new")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousCreatesStudySession_thenUnauthorized() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "New Study Session");

        mockMvc.perform(post("/study_session/new")
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserUpdatesStudySession_thenOk() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "Updated Study Session");

        mockMvc.perform(patch("/study_session/update/" + studySessionId)
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousUpdatesStudySession_thenUnauthorized() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "Updated Study Session");

        mockMvc.perform(put("/study_session/update/" + studySessionId)
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserDeletesStudySession_thenNoContent() throws Exception {
        mockMvc.perform(delete("/study_session/delete/" + studySessionId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousDeletesStudySession_thenUnauthorized() throws Exception {
        mockMvc.perform(delete("/study_session/delete/" + studySessionId))
                .andExpect(status().isUnauthorized());
    }
}
