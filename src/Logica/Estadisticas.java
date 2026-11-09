package Logica;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.LogPartida;

public final class Estadisticas {

    private Estadisticas() { }

    public static int partidasJugadas(LogPartida[] logs) {
        return logs.length;
    }

    public static int victorias(LogPartida[] logs, Bando bando) {
        return contarVictorias(logs, 0, bando);
    }

    
    public static int contarVictorias(LogPartida[] logs, int indice, Bando bando) {
        if (indice >= logs.length) {
            return 0;
        }
        int esteLog = (logs[indice].getBandoGanador() == bando) ? 1 : 0;
        return esteLog + contarVictorias(logs, indice + 1, bando);
    }
}
