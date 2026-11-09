package avanceproyectosem1.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogPartida {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String ganador;
    private final String perdedor;
    private final Bando bandoGanador;
    private final TipoFinPartida tipoFin;
    private final LocalDateTime fecha;

    private boolean ganadorEliminado;
    private boolean perdedorEliminado;

    public LogPartida(String ganador, String perdedor, Bando bandoGanador, TipoFinPartida tipoFin) {
        this.ganador = ganador;
        this.perdedor = perdedor;
        this.bandoGanador = bandoGanador;
        this.tipoFin = tipoFin;
        this.fecha = LocalDateTime.now();
    }

    public String getGanador()          { return ganador; }
    public String getPerdedor()         { return perdedor; }
    public Bando getBandoGanador()      { return bandoGanador; }
    public Bando getBandoPerdedor()     { return bandoGanador.opuesto(); }
    public TipoFinPartida getTipoFin()  { return tipoFin; }
    public LocalDateTime getFecha()     { return fecha; }

    public boolean involucra(String username) {
        return (ganador.equalsIgnoreCase(username) && !ganadorEliminado)
                || (perdedor.equalsIgnoreCase(username) && !perdedorEliminado);
    }

    public void marcarEliminado(String username) {
        if (ganador.equalsIgnoreCase(username)) ganadorEliminado = true;
        if (perdedor.equalsIgnoreCase(username)) perdedorEliminado = true;
    }

    private static String nombre(Bando b) {
        return (b == Bando.HEROES) ? "HÉROES" : "VILLANOS";
    }

    @Override
    public String toString() {
        String f = fecha.format(FORMATO);
        Bando bg = bandoGanador;
        Bando bp = bandoGanador.opuesto();
        switch (tipoFin) {
            case CAPTURA_TIERRA:
                return ganador + " usando " + nombre(bg) + " ha "
                        + (bg == Bando.HEROES ? "SALVADO" : "CAPTURADO")
                        + " la TIERRA! Venciendo a " + perdedor + " — " + f;
            case SIN_MOVIMIENTOS:
                return perdedor + " usando " + nombre(bp)
                        + " ha perdido por no tener movimientos válidos disponibles ante "
                        + ganador + " — " + f;
            default: // RETIRO
                return ganador + " usando " + nombre(bg) + " ha ganado ya que "
                        + perdedor + " usando " + nombre(bp) + " se ha retirado del juego — " + f;
        }
    }
}
