package RockieProject.backend.ExerciseSession.Infraestructure;

import RockieProject.backend.ExerciseSession.Domain.ExerciseSession;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSessionRepository extends JpaRepository<ExerciseSession, Long> {
    Page<ExerciseSession> findAllByStudentId(Long student_id, Pageable pageable);
}
