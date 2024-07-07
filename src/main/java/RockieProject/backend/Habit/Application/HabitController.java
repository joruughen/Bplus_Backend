package RockieProject.backend.Habit.Application;

import RockieProject.backend.ExerciseSession.Domain.ExerciseSession;
import RockieProject.backend.Habit.DTO.HabitDTO;
import RockieProject.backend.Habit.Domain.Habit;
import RockieProject.backend.Habit.Domain.HabitService;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<Page<Habit>> getHabits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<Habit> habits = habitService.getAllHabits(page, size);
        return ResponseEntity.ok(habits);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Habit>getHabit(@PathVariable Long id) {
        Habit habit = habitService.getHabit(id);
        return ResponseEntity.ok(habit);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/new")
    public ResponseEntity<Habit> addHabit(@RequestBody HabitDTO habitDTO) {
        Habit habit = habitService.createHabit(habitDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location","study_session/" + habit.getId()).
                body(habit);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Habit> deleteHabit(@PathVariable Long id) {
        Habit habit = habitService.deleteHabit(id);
        if (habit.getId() == null){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}//o Bad request
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
