package RockieProject.backend.Meditation.Infraestructure;

import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeditationRepository extends JpaRepository<Meditation, Long> {
    Page<Meditation> findAllByStudentId(Long student_id, Pageable pageable);

}
