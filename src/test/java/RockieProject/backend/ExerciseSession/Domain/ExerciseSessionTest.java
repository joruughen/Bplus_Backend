package RockieProject.backend.ExerciseSession.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseSessionTest {
    private ExerciseSession exerciseSession;

    @BeforeEach
    public void setUp() {
        exerciseSession = new ExerciseSession();
        exerciseSession.setName("5 minutes Cardio");
        exerciseSession.setDescription("Rutina de 5 minutos de cardio");
        exerciseSession.setCategory(Category.CARDIO);
        exerciseSession.setUrl("https://youtu.be/nlhz4TCB8_8?si=e54AGaSFdrg1VeOf");
    }
    //test de atributos base de la sesión de ejercicio
    @Test
    public void TestCreateExercise() {
        assertEquals("5 minutes Cardio",exerciseSession.getName());
        assertEquals("Rutina de 5 minutos de cardio",exerciseSession.getDescription());
        assertEquals(Category.CARDIO,exerciseSession.getCategory());
        assertEquals("https://youtu.be/nlhz4TCB8_8?si=e54AGaSFdrg1VeOf",exerciseSession.getUrl());
    }

}
