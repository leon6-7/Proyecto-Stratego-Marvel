package excepciones;

public class CredencialesInvalidasException extends Exception {
    public CredencialesInvalidasException() {
        super("Usuario o password incorrectos. Intenta de nuevo.");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
