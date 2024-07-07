package RockieProject.backend.Habit.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HabitTest {
    Habit habit;
    @BeforeEach
    public void setUp() {
        habit = new Habit();
        habit.setName("Lavarse los dientes");
        habit.setDescription("Lávate los dientes 3 veces al día");
        habit.setRelevance(2);
        habit.setCompletion(false);
        habit.setProgression(3);
        habit.setProgression_segment(2);
    }
    @Test
    public void TestCreateHabit(){
        assertEquals("Lavarse los dientes",habit.getName());
        assertEquals("Lávate los dientes 3 veces al día",habit.getDescription());
        assertEquals(2,habit.getRelevance());
        assertEquals(3,habit.getProgression());
        assertEquals(2,habit.getProgression_segment());
        assertFalse(habit.isCompletion());
    }
}
