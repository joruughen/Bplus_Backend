package RockieProject.backend.ExerciseSession.Application;

import RockieProject.backend.ExerciseSession.DTO.ExerciseSessionInDTO;
import RockieProject.backend.ExerciseSession.Domain.ExerciseSession;
import RockieProject.backend.ExerciseSession.Domain.ExerciseSessionService;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
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
@RequestMapping("/exercise_session")
public class ExerciseSessionController {

    @Autowired
    private ExerciseSessionService exerciseSessionService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseSession> getExerciseSessions(@PathVariable Long id) {
        ExerciseSession exerciseSession = exerciseSessionService.getExerciseSession(id);
        return ResponseEntity.ok(exerciseSession);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<Page<ExerciseSession>> getAllExerciseSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<ExerciseSession> exerciseSession = exerciseSessionService.getAllExerciseSession(page, size);
        return ResponseEntity.ok(exerciseSession);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<ExerciseSession> addExerciseSession(@RequestBody ExerciseSessionInDTO exerciseSessionInDTO) {
        ExerciseSession exerciseSession = exerciseSessionService.createExcersice(exerciseSessionInDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location","study_session/" + exerciseSession.getId()).
                body(exerciseSession);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ExerciseSession> addExerciseSession(@RequestBody ExerciseSessionInDTO exerciseSessionInDTO, @PathVariable Long id) {
        ExerciseSession exerciseSession = exerciseSessionService.updateExerciseSession(exerciseSessionInDTO,id);
        if (exerciseSession.getId() == null){return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location","study_session/" + exerciseSession.getId()).
                body(exerciseSession);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ExerciseSession> deleteExerciseSession(@PathVariable Long id) {
        ExerciseSession exerciseSession = exerciseSessionService.deleteExerciseSession(id);
        if (exerciseSession.getId() == null){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
