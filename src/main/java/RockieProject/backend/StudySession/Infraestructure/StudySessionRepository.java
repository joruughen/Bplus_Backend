package RockieProject.backend.StudySession.Infraestructure;

import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    StudySession findById(long id);

    Page<StudySession> findAllByStudentId(Long student_id, Pageable pageable);
}
