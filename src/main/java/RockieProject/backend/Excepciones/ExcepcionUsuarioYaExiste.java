package RockieProject.backend.Excepciones;

public class ExcepcionUsuarioYaExiste extends RuntimeException {
    public ExcepcionUsuarioYaExiste(String message) {
        super(message);
    }
}