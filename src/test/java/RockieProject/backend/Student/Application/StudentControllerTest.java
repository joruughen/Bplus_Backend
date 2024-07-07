package RockieProject.backend.Student.Application;

import RockieProject.backend.ControladorGlobalDeExcepciones;
import RockieProject.backend.Student.DTO.StudentDTO;
import RockieProject.backend.Student.DTO.StudentInfoDTO;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Domain.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                    .setControllerAdvice(new ControladorGlobalDeExcepciones()).build();
            objectMapper = new ObjectMapper();
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        @Primary
        public UserDetailsService userDetailsService() {
            return email -> {
                if ("jorge.melgarejo@utec.edu.pe".equals(email)) {
                    return User.withUsername(email)
                            .password("contraseña")
                            .roles("STUDENT")
                            .build();
                } else {
                    throw new UsernameNotFoundException(email + "no esta registrado.");
                }
            };
        }
    }

    @Test
    @WithUserDetails(value = "jorge.melgarejo@utec.edu.pe")
    public void testGetStudentInfo() throws Exception {
        StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
        studentInfoDTO.setUser_name("Jorge Melgarejo");
        studentInfoDTO.setEmail("jorge.melgarejo@utec.edu.pe");

        when(studentService.getMeInfo()).thenReturn(studentInfoDTO);

        mockMvc.perform(get("/student/me")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(studentInfoDTO)));
    }

    @Test
    @WithUserDetails(value = "jorge.melgarejo@utec.edu.pe")
    public void testUpdateStudentInfo() throws Exception {
        String jsonContent = "{ \"user_name\": \"Jorge 2\" }";
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUser_name("Jorge 2");

        doNothing().when(studentService).updateStudentInfo(studentDTO);

        mockMvc.perform(patch("/student/update")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @WithUserDetails(value = "jorge.melgarejo@utec.edu.pe")
    public void testGetStudentById() throws Exception {
        long studentId = 1L;
        List<Student> students = new ArrayList<>();
        when(studentService.list()).thenReturn(students);

        mockMvc.perform(get("/student/" + studentId)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }

    @Test
    @WithUserDetails(value = "jorge.melgarejo@utec.edu.pe")
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/student/JUJAJAJA")
                        .principal(() -> "jorge.melgarejo@utec.edu.pe")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("JUJAJAJA jorge.melgarejo@utec.edu.pe"));
    }
}