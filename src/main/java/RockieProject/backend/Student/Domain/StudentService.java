package RockieProject.backend.Student.Domain;


import RockieProject.backend.Student.DTO.StudentDTO;
import RockieProject.backend.Student.DTO.StudentInfoDTO;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    private static final String NOT_FOUND_MESSAGE = "Student not found";

    public List<Student> list() {return studentRepository.findAll();}

    public void save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
    }

    public void updateStudentInfo(StudentDTO studentInfo) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findStudentByEmail(email);

        if (studentInfo.getUser_name() != null) {
            student.setUser_name(studentInfo.getUser_name());
        }
        if (studentInfo.getPassword() != null) {
            student.setPassword(passwordEncoder.encode(studentInfo.getPassword()));
        }
        if (studentInfo.getUser_description() != null) {
            student.setUser_description(studentInfo.getUser_description());
        }

        studentRepository.save(student);
    }

    public StudentInfoDTO getMeInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findStudentByEmail(email);

        return mapper.map(student, StudentInfoDTO.class);
    }
}
