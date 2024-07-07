package RockieProject.backend.Accessory.Application;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Accessory.Domain.Accessory;
import RockieProject.backend.Accessory.Domain.Type_Accessory;
import RockieProject.backend.Accessory.Infraestructure.AccessoryRepository;
import RockieProject.backend.Student.Domain.Role;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccessoryControllerSecurityTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder encoder;

    private Long accessoryId;

    private String token;
    private Accessory accessory;

    public void createAuthorizedStudent() throws Exception {
        var res = mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\": \"jorge.melgarejo@utec.edu.pe\", \"password\": \"password\", \"user_name\": \"Jorgito\"}"))
                .andReturn();

        JSONObject json = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = json.getString("token");
    }

    public void createAdmin() throws Exception {
        Student admin = new Student();
        admin.setEmail("jose.paca@utec.edu.pe");
        admin.setPassword(encoder.encode("jorgeriosmejorprofe"));
        admin.setUser_name("Admin Paca");
        admin.setRole(Role.ADMIN);
        admin.setLocked(false);
        admin.setEnable(true);
        admin.setExpired(false);
        admin.setCredentialsExpired(false);
        studentRepository.save(admin);
        var res = mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{\"email\": \"jose.paca@utec.edu.pe\", \"password\": \"jorgeriosmejorprofe\"}"))
                .andReturn();

        JSONObject json = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = json.getString("token");
    }


    @BeforeEach
    public void setUp() throws Exception {
        accessoryRepository.deleteAll();
        studentRepository.deleteAll();
        accessory = new Accessory();
        accessory.setName("Perro");
        accessory.setPrice(12.3);
        accessory.setUrl("https://www.google.com");
        accessory.setType(Type_Accessory.FACE);
        accessoryId = accessoryRepository.save(accessory).getId();
    }

    @Test
    public void whenUserCreatesAccessory_thenCreated() throws Exception {
        createAdmin();
        JSONObject request = new JSONObject();
        request.put("name", "Carita uwu");
        request.put("price", 12.3);
        request.put("url", "https://www.google.com");
        request.put("type", "FACE");

        mockMvc.perform(post("/accessory/new")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAnonymousCreatesAccessory_thenUnauthorized() throws Exception {
        createAuthorizedStudent();
        JSONObject request = new JSONObject();
        request.put("name", "New Accessory");

        mockMvc.perform(post("/accessory/new")
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserAccessAllAccessories_thenOk() throws Exception {
        createAuthorizedStudent();
        mockMvc.perform(get("/accessory/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessAllAccessories_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/accessory/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAdminAccessAllAccessories_thenOk() throws Exception {
        createAdmin();
        mockMvc.perform(get("/accessory/all_admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUserAccessAllAccessoriesAdmin_thenForbidden() throws Exception {
        createAuthorizedStudent();
        mockMvc.perform(get("/accessory/all_admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessAllAccessoriesAdmin_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/accessory/all_admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserBuysAccessory_thenOk() throws Exception {
        createAuthorizedStudent();

        mockMvc.perform(patch("/accessory/buy/accessory/"+accessory.getName())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousBuysAccessory_thenUnauthorized() throws Exception {
        JSONObject request = new JSONObject();
        request.put("accessoryId", accessory.toString());

        mockMvc.perform(patch("/accessory/buy/accessory")
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isUnauthorized());
    }
}
