package RockieProject.backend.Auth;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() throws Exception {
        studentRepository.deleteAll();
    }

    @Test
    public void testSignup() throws Exception {
        String signupRequest = "{\"email\": \"jose.paca@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jose\"}";

        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(signupRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        String signupRequest = "{\"email\": \"jose.paca@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jose\"}";
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(signupRequest))
                .andExpect(status().isOk());

        String loginRequest = "{\"email\": \"jose.paca@utec.edu.pe\", \"password\": \"password\"}";
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk());
    }
}
