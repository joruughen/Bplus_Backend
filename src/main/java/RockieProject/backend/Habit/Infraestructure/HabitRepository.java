package RockieProject.backend.Habit.Infraestructure;

import RockieProject.backend.Habit.Domain.Habit;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
}
