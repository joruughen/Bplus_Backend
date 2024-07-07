package RockieProject.backend.Meditation.Application;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.Meditation.Infraestructure.MeditationRepository;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.Domain.Status;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithUserDetails(value = "jorge.melgarejo@utec.edu.pe", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "STUDENT")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeditationControllerSecurityTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeditationRepository meditationRepository;

    @Autowired
    private StudentRepository studentRepository;

    String token;

    private Long createMeditation() {
        Meditation meditation = new Meditation();
        meditation.setMeditation_time(30);
        meditation.setStatus(Status.TODO);
        meditation.setStudent(studentRepository.findStudentByEmail("jorge.melgarejo@utec.edu.pe"));

        return meditationRepository.save(meditation).getId();
    }

    @BeforeEach
    public void setUp() throws Exception {
        meditationRepository.deleteAll();
        studentRepository.deleteAll();

        var res = mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON)
                .content("{\"email\": \"jorge.melgarejo@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jorgito\"}"))
                .andReturn();

        JSONObject json = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = json.getString("token");
    }


    @Test
    public void testAuthorizedGetAllMeditations() throws Exception {
        mockMvc.perform(get("/meditation/all")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedGetAllMeditations() throws Exception {
        mockMvc.perform(get("/meditation/all")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthorizedGetMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(get("/meditation/{id}", meditationId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedGetMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(get("/meditation/{id}", meditationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthorizedCreateMeditation() throws Exception {
        mockMvc.perform(post("/meditation/new")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"meditation_time\": 30, \"status\": \"PENDING\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedCreateMeditation() throws Exception {
        mockMvc.perform(post("/meditation/new")
                        .contentType(APPLICATION_JSON)
                        .content("{\"meditation_time\": 30, \"status\": \"PENDING\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthorizedUpdateMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(patch("/meditation/update/{id}", meditationId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"meditation_time\": 45, \"status\": \"COMPLETED\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedUpdateMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(patch("/meditation/update/{id}", meditationId)
                        .contentType(APPLICATION_JSON)
                        .content("{\"meditation_time\": 45, \"status\": \"COMPLETED\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthorizedDeleteMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(delete("/meditation/delete/{id}", meditationId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedDeleteMeditation() throws Exception {
        Long meditationId = createMeditation();

        mockMvc.perform(delete("/meditation/delete/{id}", meditationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
