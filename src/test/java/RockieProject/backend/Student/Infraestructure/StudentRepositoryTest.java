package RockieProject.backend.Student.Infraestructure;

import RockieProject.backend.AbstractContainerBaseTest;
import RockieProject.backend.Student.Domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveAndFindUser() {
        Student student = new Student();
        student.setUser_name("testuser");
        student.setEmail("alumno@utec.edu.pe");
        student.setPassword("password");
        studentRepository.save(student);

        Student foundUser = studentRepository.findStudentByEmail("alumno@utec.edu.pe");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("alumno@utec.edu.pe");
    }

}