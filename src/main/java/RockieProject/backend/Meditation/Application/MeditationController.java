package RockieProject.backend.Meditation.Application;

import RockieProject.backend.Meditation.DTO.MeditationDTO;
import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.Meditation.Domain.MeditationService;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOPatch;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Domain.StudySession;
import RockieProject.backend.StudySession.Domain.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meditation")
public class MeditationController {

    @Autowired
    private MeditationService meditationService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<Page<Meditation>> getAllMeditations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Meditation> meditations = meditationService.getAllMeditations(page, size);
        return ResponseEntity.ok(meditations);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Meditation> getMeditation(@PathVariable Long id) {
        Meditation meditation = meditationService.getMeditation(id);
        return ResponseEntity.ok(meditation);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/new")
    public ResponseEntity<Meditation> createMeditation(@RequestBody MeditationDTO meditationDTO) {
        Meditation meditation = meditationService.createMeditation(meditationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location","study_session/" + meditation.getId()).
                body(meditation);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Meditation> updateMeditation(@RequestBody MeditationDTO meditationDTO, @PathVariable Long id) {
        Meditation meditation = meditationService.updateMeditation(meditationDTO, id);
        if (meditation.getId() == null){return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();}//confio en las exceptions de paca
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location","study_session/" + meditation.getId()).
                body(meditation);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Meditation> deleteMeditation(@PathVariable Long id) {
        Meditation meditation = meditationService.deleteMeditation(id);
        if (meditation.getId() == null){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}//confio en las exceptions de paca ns si notfound es la que deberia ser filo sodfen cual deberia
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
