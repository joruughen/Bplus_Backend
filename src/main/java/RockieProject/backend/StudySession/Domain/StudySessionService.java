package RockieProject.backend.StudySession.Domain;

import RockieProject.backend.Accessory.DTO.AccessoryDTO;
import RockieProject.backend.Accessory.Domain.Accessory;
import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOPatch;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Infraestructure.StudySessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    ModelMapper mapper;

    public List<StudySessionDTOWITHOUTUSER> mapStudySessionToDto(List<StudySession> studySessions) {
        return studySessions.stream()
                .map(StudySession -> mapper.map(StudySession, StudySessionDTOWITHOUTUSER.class))
                .collect(Collectors.toList());
    }

    public Page<StudySession> getAllStudySessions(int page, int size) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);


        Pageable pageable = PageRequest.of(page, size);

        Page<StudySession> studysessions = studySessionRepository.findAllByStudentId(student.getId(),pageable);
        return studysessions.map(studies -> mapper.map(studies, StudySession.class));
    }


    public StudySessionDTO getStudySession(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);

        Optional<StudySession> studySession = studySessionRepository.findById(id);
        if (studySession.isPresent()) {
            if (Objects.equals(studySession.get().getStudent().getId(), student.getId())){
                return mapper.map(studySession.get(), StudySessionDTO.class);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienes esta sesion de estudio");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesion de estudio no existe");
        }
    }

    public StudySessionDTOWITHOUTUSER createStudySession(StudySessionDTO studySessionDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email); //tmr me acabo de dar cuenta que puedo crear una variable para esto ptmr paca confio en que verás esto y lo harpas para que quede mas limpio
        StudySession studySession = mapper.map(studySessionDTO, StudySession.class);

        studySession.setStatus(Status.TODO);
        studySession.setStudent(student);
        studySessionRepository.save(studySession);
        return mapper.map(studySession,StudySessionDTOWITHOUTUSER.class);
        //falta setear acorde al tipo metodo
    }

    public StudySessionDTOWITHOUTUSER updateStudySession(StudySessionDTOPatch studySessionDTOPatch, Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<StudySession> studySession = studySessionRepository.findById(id);
        if (studySession.isPresent() ) {
            if (!Objects.equals(studySession.get().getStudent().getId(), student.getId())){return new StudySessionDTOWITHOUTUSER();}
            if (studySessionDTOPatch.getName() != null) {studySession.get().setName(studySessionDTOPatch.getName());}
            if (studySessionDTOPatch.getStudy_time() != 0){studySession.get().setStudy_time(studySessionDTOPatch.getStudy_time());}
            if (studySessionDTOPatch.getBreak_time() != 0){studySession.get().setBreak_time(studySessionDTOPatch.getBreak_time());} // != 0 pq por default si no pones es 0
            if (studySessionDTOPatch.getRepetitions() != 0){studySession.get().setRepetitions(studySessionDTOPatch.getRepetitions());}
            if (studySessionDTOPatch.getMethod() != null) {studySession.get().setMethod(studySessionDTOPatch.getMethod());}
            studySessionRepository.save(studySession.get());
            //falta poner que se actualice con el metodo si es diferente de PERSONALIZED adcorde a las hroas que dicta cada tipo de sesion de esti¿udio enum
        }
        return mapper.map(studySession.get(),StudySessionDTOWITHOUTUSER.class);
    }



    public StudySessionDTOWITHOUTUSER deleteStudySession(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<StudySession> studySession = studySessionRepository.findById(id);

        if (studySession.isPresent()) {
            if (!Objects.equals(studySession.get().getStudent().getId(), student.getId())){return new StudySessionDTOWITHOUTUSER();}
            else{
                StudySession studySessionToDelete = studySession.get();
                studySessionRepository.delete(studySessionToDelete);
                return mapper.map(studySessionToDelete,StudySessionDTOWITHOUTUSER.class);}
        }else {
            return new StudySessionDTOWITHOUTUSER();
        }
    }
}
