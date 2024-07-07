package RockieProject.backend.Student.Application;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "jorge.melgarejo@utec.edu.pe", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "STUDENT")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentControllerSecurityTest extends AbstractContainerBaseTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    private Long studentId;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        studentRepository.deleteAll();
        var res = mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\": \"jorge.melgarejo@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jorgito\"}"))
                .andReturn();

        JSONObject json = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = json.getString("token");
        studentId = studentRepository.findStudentByEmail("jorge.melgarejo@utec.edu.pe").getId();
    }

    @Test
    public void whenUserAccessStudent_thenOk() throws Exception {
        mockMvc.perform(get("/student/" + studentId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessStudent_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/student/" + studentId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserAccessJuJaJaJa_thenOk() throws Exception {
        mockMvc.perform(get("/student/JUJAJAJA")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessJuJaJaJa_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/student/JUJAJAJA"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserAccessMe_thenOk() throws Exception {
        mockMvc.perform(get("/student/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessMe_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/student/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserPatchMe_thenOk() throws Exception {

        mockMvc.perform(get("/student/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousPatchMe_thenUnauthorized() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "Updated Student Name");

        mockMvc.perform(patch("/student/me")
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isUnauthorized());
    }
}
