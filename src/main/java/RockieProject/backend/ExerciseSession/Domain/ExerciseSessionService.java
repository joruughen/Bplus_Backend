package RockieProject.backend.ExerciseSession.Domain;

import RockieProject.backend.ExerciseSession.DTO.ExerciseSessionInDTO;
import RockieProject.backend.ExerciseSession.Infraestructure.ExerciseSessionRepository;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Domain.Status;
import RockieProject.backend.StudySession.Domain.StudySession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class ExerciseSessionService {

    @Autowired
    ExerciseSessionRepository exerciseSessionRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ModelMapper mapper;

    public ExerciseSession createExcersice(ExerciseSessionInDTO exerciseSessionInDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        ExerciseSession exerciseSession = mapper.map(exerciseSessionInDTO, ExerciseSession.class);

        exerciseSession.setStudent(student);
        exerciseSessionRepository.save(exerciseSession);
        return exerciseSession;
    }

    public ExerciseSession getExerciseSession(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<ExerciseSession> exerciseSession = exerciseSessionRepository.findById(id);
        if (exerciseSession.isPresent()) {
            return mapper.map(exerciseSession.get(), ExerciseSession.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise session no existe");
        }

    }


    public Page<ExerciseSession> getAllExerciseSession(int page, int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);


        Pageable pageable = PageRequest.of(page, size);

        Page<ExerciseSession> exerciseSessions = exerciseSessionRepository.findAll(pageable);
        return exerciseSessions.map(studies -> mapper.map(studies, ExerciseSession.class));
    }

    public ExerciseSession updateExerciseSession(ExerciseSessionInDTO exerciseSessionInDTO, Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<ExerciseSession> exerciseSession = exerciseSessionRepository.findById(id);

        if (exerciseSession.isPresent() ) {
            if (!Objects.equals(exerciseSession.get().getStudent().getId(), student.getId())){return new ExerciseSession();}
            if (exerciseSessionInDTO.getName() != null) {exerciseSession.get().setName(exerciseSessionInDTO.getName());}
            if (exerciseSessionInDTO.getDescription() != null){exerciseSession.get().setDescription(exerciseSessionInDTO.getDescription());}
            if (exerciseSessionInDTO.getCategory() != null){exerciseSession.get().setCategory(exerciseSessionInDTO.getCategory());}
            if (exerciseSessionInDTO.getUrl() != null){exerciseSession.get().setUrl(exerciseSessionInDTO.getUrl());}
            exerciseSessionRepository.save(exerciseSession.get());
        }
        return mapper.map(exerciseSession.get(),ExerciseSession.class);
    }

    public ExerciseSession deleteExerciseSession(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<ExerciseSession> exerciseSession = exerciseSessionRepository.findById(id);

        if (exerciseSession.isPresent()) {
            if (!Objects.equals(exerciseSession.get().getStudent().getId(), student.getId())){return new ExerciseSession();}
            else{
                ExerciseSession exerciseSessionToDelete = exerciseSession.get();
                exerciseSessionRepository.delete(exerciseSessionToDelete);
                return mapper.map(exerciseSessionToDelete,ExerciseSession.class);}
            }
        else {
            return new ExerciseSession();
            }
        }
}
