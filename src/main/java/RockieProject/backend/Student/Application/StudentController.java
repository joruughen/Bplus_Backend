package RockieProject.backend.Student.Application;

import RockieProject.backend.Student.DTO.StudentDTO;
import RockieProject.backend.Student.DTO.StudentInfoDTO;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Domain.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<List<Student>> getStudent(@PathVariable Long id) {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/Mizuhara")
    public ResponseEntity<String> getUser(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("No hay principal?");
        }
        return new ResponseEntity<>("JUJAJAJA " + principal.getName(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<StudentInfoDTO> getMe() {
        return ResponseEntity.ok(studentService.getMeInfo());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/update")
    public ResponseEntity<StudentInfoDTO> updateStudentInfo(@RequestBody StudentDTO studentInfo) {
        studentService.updateStudentInfo(studentInfo);
        return ResponseEntity.ok(studentService.getMeInfo());
    }


}
