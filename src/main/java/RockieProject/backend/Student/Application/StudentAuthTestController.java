package RockieProject.backend.Student.Application;

import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Domain.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class StudentAuthTestController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Student user) {
        studentService.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> list() {
        return ResponseEntity.ok(studentService.list());
    }

}
