package excepciones;
/**
 *
 * @author Abril
 */
public class PasswordInvalidoException extends Exception {
    public PasswordInvalidoException() {
        super("El password debe tener exactamente 5 caracteres.");
    }
}
