package Logica;

import avanceproyectosem1.modelo.LogPartida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestorLogs {

    private LogPartida[] logs = new LogPartida[20];
    private int cantidad = 0;

    public void agregar(LogPartida log) {
        if (cantidad == logs.length) {
            logs = Arrays.copyOf(logs, logs.length * 2);
        }
        logs[cantidad++] = log;
    }

    public int getCantidad() { return cantidad; }

    public LogPartida[] getLogs() {
        return Arrays.copyOf(logs, cantidad);
    }

    public List<LogPartida> getDeUsuario(String username) {
        List<LogPartida> resultado = new ArrayList<>();
        for (int i = cantidad - 1; i >= 0; i--) {
            if (logs[i].involucra(username)) {
                resultado.add(logs[i]);
            }
        }
        return resultado;
    }

    public void marcarEliminado(String username) {
        for (int i = 0; i < cantidad; i++) {
            logs[i].marcarEliminado(username);
        }
    }
}
