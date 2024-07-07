package RockieProject.backend.Habit.Domain;

import RockieProject.backend.Habit.DTO.HabitDTO;
import RockieProject.backend.Habit.Infraestructure.HabitRepository;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import RockieProject.backend.StudySession.DTO.StudySessionDTO;
import RockieProject.backend.StudySession.DTO.StudySessionDTOWITHOUTUSER;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    ModelMapper mapper;

    public Page<Habit> getAllHabits(int page, int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        List<Habit> habits = student.getHabits();

        Pageable pageable = PageRequest.of(page, size);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), habits.size());

        List<Habit> paginatedHabits = habits.subList(start, end);

        return new PageImpl<>(paginatedHabits, pageable, habits.size());
    }

    public Habit getHabit(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);

        Optional<Habit> habit = habitRepository.findById(id);
        if (habit.isPresent()) {
            if (student.getHabits().contains(habit.get())){
                return mapper.map(habit.get(), Habit.class);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienes esta sesion de estudio");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesion de estudio no existe");
        }
    }

    public Habit createHabit(HabitDTO habitDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Habit habit = mapper.map(habitDTO, Habit.class);

        habit.setCompletion(false);
        habit.setProgression(100);
        habit.setProgression_segment(0);
        habit.setStudent(student);
        habitRepository.save(habit);
        return mapper.map(habit,Habit.class);
    }

    public Habit deleteHabit(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findStudentByEmail(email);
        Optional<Habit> habit = habitRepository.findById(id);

        if (habit.isPresent()) {
            if (!Objects.equals(habit.get().getStudent().getId(), student.getId())){return new Habit();}
            else{
                Habit habitToDelete = habit.get();
                habitRepository.delete(habitToDelete);
                return mapper.map(habitToDelete,Habit.class);}
        }else {
            return new Habit();
        }
    }
}
