package RockieProject.backend.Student.Infraestructure;

import RockieProject.backend.Student.Domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByEmail(String email);
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);
}
