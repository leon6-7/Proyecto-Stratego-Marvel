package excepciones;
/**
 *
 * @author Abril
 */
public class UsuarioDuplicadoException extends Exception {
    public UsuarioDuplicadoException(String username) {
        super("El username \"" + username + "\" ya existe. Ingresa uno nuevo.");
    }
}
