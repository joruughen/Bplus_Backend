package RockieProject.backend.Meditation.Events;

import RockieProject.backend.Meditation.Domain.Meditation;
import RockieProject.backend.Meditation.Infraestructure.MeditationRepository;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MeditationEventListener {

    @Autowired
    private MeditationRepository meditationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @EventListener
    public void handleMeditationCompleteEvent(MeditationCompleteEvent event) {
        Long meditationId = event.getMeditationId();
        Meditation meditation = meditationRepository.findById(meditationId)
                .orElseThrow(() -> new RuntimeException("Meditation not found"));

        Student student = meditation.getStudent();
        student.setRockie_coins(student.getRockie_coins() + 5); // Añade 5 rockie coins

        studentRepository.save(student);
    }
}
