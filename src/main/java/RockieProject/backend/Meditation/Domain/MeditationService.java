package RockieProject.backend.Meditation.Domain;

import RockieProject.backend.Meditation.DTO.MeditationDTO;
import RockieProject.backend.Meditation.Infraestructure.MeditationRepository;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOPatch;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
import RockieProject.backend.StudySession.Domain.Status;
import RockieProject.backend.StudySession.Domain.StudySession;
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
public class MeditationService {
    @Autowired
    private MeditationRepository meditationRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    ModelMapper mapper;


    public Page<Meditation> getAllMeditations(int page, int size) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);


        Pageable pageable = PageRequest.of(page, size);

        Page<Meditation> meditations = meditationRepository.findAllByStudentId(student.getId(),pageable);
        return meditations.map(meditation -> mapper.map(meditation, Meditation.class));
    }

    public Meditation getMeditation(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);

        Optional<Meditation> meditation = meditationRepository.findById(id);
        if (meditation.isPresent()) {
            if (Objects.equals(meditation.get().getStudent().getId(), student.getId())){
                return meditation.get();
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienes esta meditacion");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meditacion no existe");
        }
    }

    public Meditation createMeditation(MeditationDTO meditationDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email); //tmr me acabo de dar cuenta que puedo crear una variable para esto ptmr paca confio en que verás esto y lo harpas para que quede mas limpio
        Meditation meditation = mapper.map(meditationDTO, Meditation.class);

        meditation.setStatus(Status.TODO);
        meditation.setStudent(student);
        meditationRepository.save(meditation);
        return mapper.map(meditation,Meditation.class);
    }

    public Meditation updateMeditation(MeditationDTO meditationDTO, Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<Meditation> meditation = meditationRepository.findById(id);
        if (meditation.isPresent() ) {
            if (!Objects.equals(meditation.get().getStudent().getId(), student.getId())){return new Meditation();}
            if (meditationDTO.getMeditation_time() != 0){meditation.get().setMeditation_time(meditationDTO.getMeditation_time());}
            //falta poner que se actualice con el metodo si es diferente de PERSONALIZED adcorde a las hroas que dicta cada tipo de sesion de esti¿udio enum
            meditationRepository.save(meditation.get());
        }
        return mapper.map(meditation.get(),Meditation.class);
    }

    public Meditation deleteMeditation(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<Meditation> meditation = meditationRepository.findById(id);

        if (meditation.isPresent()) {
            if (!Objects.equals(meditation.get().getStudent().getId(), student.getId())){return new Meditation();}
            else{
                Meditation meditationToDelete = meditation.get();
                meditationRepository.delete(meditationToDelete);
                return mapper.map(meditationToDelete,Meditation.class);}
        }else {
            return new Meditation();
        }
    }
}
