package RockieProject.backend.StudySession.Application;

import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOPatch;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Domain.StudySession;
import RockieProject.backend.StudySession.Domain.StudySessionService;
import RockieProject.backend.StudySession.Infraestructure.StudySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/study_session/")
public class StudySessionController {

    @Autowired
    private StudySessionService studySessionService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<Page<StudySession>> getAllStudySessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<StudySession> studySessions = studySessionService.getAllStudySessions(page, size);
        return ResponseEntity.ok(studySessions);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudySessionDTO> getAllStudySessions(@PathVariable Long id) {
        StudySessionDTO studySession = studySessionService.getStudySession(id);
        return ResponseEntity.ok(studySession);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/new")
    public ResponseEntity<StudySessionDTOWITHOUTUSER> createStudySession(@RequestBody StudySessionDTO studySessionDTO) {
        StudySessionDTOWITHOUTUSER studySession = studySessionService.createStudySession(studySessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location","study_session/" + studySession.getId()).
                body(studySession);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<StudySessionDTOWITHOUTUSER> updateStudySession(@RequestBody StudySessionDTOPatch studySessionDTOPatch, @PathVariable Long id) {
        StudySessionDTOWITHOUTUSER studySession = studySessionService.updateStudySession(studySessionDTOPatch,id);
        if (studySession.getId() == null){return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}//confio en las exceptions de paca
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location","study_session/" + studySession.getId()).
                body(studySession);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudySessionDTOWITHOUTUSER> deleteStudySession(@PathVariable Long id) {
        StudySessionDTOWITHOUTUSER studySession = studySessionService.deleteStudySession(id);
        if (studySession.getId() == null){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}//confio en las exceptions de paca ns si notfound es la que deberia ser filo sodfen cual deberia
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
