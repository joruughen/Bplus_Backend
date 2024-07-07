package RockieProject.backend.Excepciones;

public class ExcepcionRecursoNoEncontrado extends RuntimeException{
    public ExcepcionRecursoNoEncontrado(String message) {
        super(message);
    }
}