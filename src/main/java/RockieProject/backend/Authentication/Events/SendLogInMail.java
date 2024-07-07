package RockieProject.backend.Authentication.Events;

import RockieProject.backend.Authentication.Events.Model.Mail;
import RockieProject.backend.Student.Domain.Student;
import RockieProject.backend.Student.Infraestructure.StudentRepository;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SendLogInMail extends ApplicationEvent {
    StudentRepository studentRepository;

    private final Mail mail;

    public SendLogInMail(Student student, String email) {
        super(student.getEmail());
        Map<String, Object> properties = new HashMap<>();
        properties.put("userName", student.getUser_name());

        this.mail = Mail.builder()
                .from(email)
                .to(student.getEmail())
                .htmlTemplate(new Mail.HtmlTemplate("LogInTemplate", properties))
                .subject("Se ha realizado un inicio de sesión en tu cuenta")
                .build();
    }

}
