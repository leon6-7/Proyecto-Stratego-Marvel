package avanceproyectosem1.modelo;

import excepciones.PasswordInvalidoException;

public class Jugador {

    public static final int LONGITUD_PASSWORD = 5;

    private final String username;
    private String password;
    private int puntos;
    private int partidasHeroes;
    private int partidasVillanos;

    public Jugador(String username, String password) throws PasswordInvalidoException {
        this.username = username;
        setPassword(password);
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVillanos = 0;
    }

    public void setPassword(String nuevo) throws PasswordInvalidoException {
        if (nuevo == null || nuevo.length() != LONGITUD_PASSWORD) {
            throw new PasswordInvalidoException();
        }
        this.password = nuevo;
    }

    public boolean verificarPassword(String intento) {
        return password.equals(intento);
    }

    public void sumarPuntos(int p) { this.puntos += p; }

    public void registrarPartida(Bando bando) {
        if (bando == Bando.HEROES) partidasHeroes++;
        else partidasVillanos++;
    }

    public String getUsername()     { return username; }
    public int getPuntos()          { return puntos; }
    public int getPartidasHeroes()  { return partidasHeroes; }
    public int getPartidasVillanos(){ return partidasVillanos; }

    @Override
    public String toString() { return username; }
}
