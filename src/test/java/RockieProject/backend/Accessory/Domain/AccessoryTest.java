package RockieProject.backend.Accessory.Domain;

import RockieProject.backend.ExerciseSession.Domain.Category;
import RockieProject.backend.ExerciseSession.Domain.ExerciseSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessoryTest {
    private Accessory accessory;

    @BeforeEach
    public void setUp() {
        accessory = new Accessory();
        accessory.setName("uwuface");
        accessory.setPrice(4.0);
        accessory.setUrl("https://youtu.be/nlhz4TCB8_8?si=e54AGaSFdrg1VeOf");
        accessory.setType(Type_Accessory.FACE);
    }
    //test de atributos base de la sesión de ejercicio
    @Test
    public void TestCreateExercise() {
        assertEquals("uwuface",accessory.getName());
        assertEquals(4.0,accessory.getPrice());
        assertEquals("https://youtu.be/nlhz4TCB8_8?si=e54AGaSFdrg1VeOf",accessory.getUrl());
        assertEquals(Type_Accessory.FACE,accessory.getType());
    }
}
