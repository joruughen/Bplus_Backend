package RockieProject.backend.Meditation.Events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MeditationCompleteEvent extends ApplicationEvent {
    private final Long meditationId;

    public MeditationCompleteEvent(Object source, Long meditationId) {
        super(source);
        this.meditationId = meditationId;
    }

}
