package Logica;

import excepciones.CredencialesInvalidasException;
import excepciones.PasswordInvalidoException;
import excepciones.UsuarioDuplicadoException;
import avanceproyectosem1.modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

public class GestorJugadores {

    private Jugador[] jugadores = new Jugador[10];
    private int cantidad = 0;
    private int historicos = 0;  
    
    
    public boolean existe(String username) {
        return buscar(username) != null;
    }

    public Jugador buscar(String username) {
        for (int i = 0; i < cantidad; i++) {
            if (jugadores[i].getUsername().equalsIgnoreCase(username)) {
                return jugadores[i];
            }
        }
        return null;
    }

    public Jugador crear(String username, String password)
            throws UsuarioDuplicadoException, PasswordInvalidoException {
        if (existe(username)) {
            throw new UsuarioDuplicadoException(username);
        }
        Jugador nuevo = new Jugador(username, password);  
        if (cantidad == jugadores.length) {
            Jugador[] mayor = new Jugador[jugadores.length * 2];
            System.arraycopy(jugadores, 0, mayor, 0, cantidad);
            jugadores = mayor;
        }
        jugadores[cantidad++] = nuevo;
        historicos++;
        return nuevo;
    }

    public Jugador login(String username, String password) throws CredencialesInvalidasException {
        Jugador j = buscar(username);
        if (j == null || !j.verificarPassword(password)) {
            throw new CredencialesInvalidasException();
        }
        return j;
    }

    public List<Jugador> getRivales(Jugador actual) {
        List<Jugador> rivales = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            if (jugadores[i] != actual) rivales.add(jugadores[i]);
        }
        return rivales;
    }

    public boolean hayRivales(Jugador actual) {
        return cantidad > 1 || (cantidad == 1 && jugadores[0] != actual);
    }

    public List<Jugador> getActivos() {
        List<Jugador> activos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            activos.add(jugadores[i]);
        }
        return activos;
    }

    public void cambiarPassword(Jugador jugador, String actual, String nuevo)
            throws CredencialesInvalidasException, PasswordInvalidoException {
        if (!jugador.verificarPassword(actual)) {
            throw new CredencialesInvalidasException("El password actual es incorrecto.");
        }
        jugador.setPassword(nuevo);  
    }

    public void eliminar(Jugador jugador, String password) throws CredencialesInvalidasException {
        if (!jugador.verificarPassword(password)) {
            throw new CredencialesInvalidasException("El password es incorrecto. La cuenta no fue eliminada.");
        }
        for (int i = 0; i < cantidad; i++) {
            if (jugadores[i] == jugador) {
                for (int k = i; k < cantidad - 1; k++) {
                    jugadores[k] = jugadores[k + 1];     
                }
                jugadores[--cantidad] = null;
                return;
            }
        }
    }

    public int getCantidadActivos()   { return cantidad; }
    public int getCantidadHistoricos(){ return historicos; }
}
