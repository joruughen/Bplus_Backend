package RockieProject.backend.Authentication.Events;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogInEmailListener {
    private final EmailService emailService;

    @Autowired
    public LogInEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleEmailEvent(SendLogInMail event) throws MessagingException, IOException {
        emailService.sendEmail(event.getMail());
    }
}
