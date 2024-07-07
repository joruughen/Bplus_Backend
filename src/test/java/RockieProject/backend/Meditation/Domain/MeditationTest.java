package RockieProject.backend.Meditation.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeditationTest {
    Meditation meditation;

    @BeforeEach
    void setUp() {
        meditation = new Meditation();
        meditation.setMeditation_time(20);
    }

    @Test
    void testCreateMeditation() {
        assertEquals(20, meditation.getMeditation_time());
    }
}
