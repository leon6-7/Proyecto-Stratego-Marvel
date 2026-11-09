/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import avanceproyectosem1.modelo.*;
import excepciones.MovimientoInvalidoException;
import Piezas.Pieza;

import java.util.ArrayList;
import java.util.List;

public class MotorJuego {
 
    private static final int[][] DIRECCIONES = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
 
    private final Tablero tablero;
    private final ModoJuego modo;
 
    private Bando turno = Bando.HEROES;
    private EstadoPartida estado = EstadoPartida.EN_CURSO;
    private Bando ganador;
    private TipoFinPartida tipoFin;
 
    private final List<Pieza> vencidasHeroes = new ArrayList<>();
    private final List<Pieza> vencidasVillanos = new ArrayList<>();
 
    public MotorJuego(Tablero tablero, ModoJuego modo) {
        this.tablero = tablero;
        this.modo = modo;
    }
 
    public Tablero getTablero()        { return tablero; }
    public ModoJuego getModo()         { return modo; }
    public Bando getTurno()            { return turno; }
    public EstadoPartida getEstado()   { return estado; }
    public boolean isTerminada()       { return estado != EstadoPartida.EN_CURSO; }
    public Bando getGanador()          { return ganador; }
    public Bando getPerdedor()         { return ganador == null ? null : ganador.opuesto(); }
    public TipoFinPartida getTipoFin() { return tipoFin; }
 
    public List<Pieza> getVencidas(Bando bando) {
        return new ArrayList<>(bando == Bando.HEROES ? vencidasHeroes : vencidasVillanos);
    }
 
    //Celdas ded destinos posibles Se highlight en verde
    public List<int[]> getDestinosValidos(int f, int c) {
        List<int[]> destinos = new ArrayList<>();
        Pieza p = tablero.getPieza(f, c);
        if (p == null || !p.puedeMoverse() || !(p instanceof Movible)) {
            return destinos;
        }
        Movible m = (Movible) p;
        for (int[] d : DIRECCIONES) {
            for (int paso = 1; paso <= m.getAlcance(); paso++) {
                int nf = f + d[0] * paso;
                int nc = c + d[1] * paso;
                if (!tablero.enRango(nf, nc) || !m.puedeMoverA(nf, nc, tablero)) {
                    break;
                }
                destinos.add(new int[] { nf, nc });
                if (!tablero.estaVacia(nf, nc)) {
                    break;       
                }
            }
        }
        return destinos;
    }
 
    //Recursiva usada para encontrar movimientos pòsibles
    public boolean hayMovimientos(Bando bando, int indice) {
        if (indice >= Tablero.TAMANO * Tablero.TAMANO) return false;
        int f = indice / Tablero.TAMANO;
        int c = indice % Tablero.TAMANO;
        Pieza p = tablero.getPieza(f, c);
        if (p != null && p.getBando() == bando && p.puedeMoverse()
                && !getDestinosValidos(f, c).isEmpty()) {
            return true;
        }
        return hayMovimientos(bando, indice + 1);
    }
 
 
    //
    public void mover(int fo, int co, int fd, int cd) throws MovimientoInvalidoException {
        if (isTerminada()) {
            throw new MovimientoInvalidoException("La partida ya terminó.");
        }
        Pieza p = tablero.getPieza(fo, co);
        if (p == null) {
            throw new MovimientoInvalidoException("No hay ninguna pieza en esa celda.");
        }
        if (p.getBando() != turno) {
            throw new MovimientoInvalidoException("No es el turno de ese bando.");
        }
        if (!p.puedeMoverse()) {
            throw new MovimientoInvalidoException(p.getNombre() + " no puede moverse.");
        }
        if (!esDestino(fo, co, fd, cd)) {
            throw new MovimientoInvalidoException("Esa celda no es un destino válido.");
        }
 
        Pieza objetivo = tablero.getPieza(fd, cd);
        if (objetivo == null) {
            if (Math.abs(fd - fo) + Math.abs(cd - co) > 1) {
                p.revelar();        // el movimiento múltiple delata al Rango 2
            }
            tablero.mover(fo, co, fd, cd);
        } else {
            resolverCombate(p, objetivo, fd, cd);
        }
        finalizarJugada(objetivo);
    }

    
    public void retirar(Bando quienSeRetira) {
        if (isTerminada()) return;
        estado = EstadoPartida.RETIRADA;
        ganador = quienSeRetira.opuesto();
        tipoFin = TipoFinPartida.RETIRO;
    }
 

    private boolean esDestino(int fo, int co, int fd, int cd) {
        for (int[] d : getDestinosValidos(fo, co)) {
            if (d[0] == fd && d[1] == cd) return true;
        }
        return false;
    }
 
    private void resolverCombate(Pieza atacante, Pieza defensor, int fd, int cd) {
        int fo = atacante.getFila();
        int co = atacante.getColumna();
        atacante.revelar();
        defensor.revelar();
 
        ResultadoCombate res = atacante.atacar(defensor);  
        switch (res) {
            case GANA:
                eliminar(defensor);
                tablero.quitar(fo, co);
                tablero.colocar(fd, cd, atacante);
                break;
            case PIERDE:
                eliminar(atacante);
                break;
            case EMPATE:
            default:
                eliminar(atacante);
                eliminar(defensor);
                break;
        }
    }
 
    private void eliminar(Pieza p) {
        tablero.quitar(p.getFila(), p.getColumna());
        if (p.getBando() == Bando.HEROES) vencidasHeroes.add(p);
        else vencidasVillanos.add(p);
    }
 
    private void finalizarJugada(Pieza objetivo) {
        Bando actual = turno;
        Bando rival = actual.opuesto();
 
        if (objetivo != null && objetivo.getTipo() == TipoPieza.TIERRA
                && objetivo.getBando() == rival
                && (actual == Bando.HEROES ? vencidasVillanos : vencidasHeroes).contains(objetivo)) {
            estado = EstadoPartida.TERMINADA;
            ganador = actual;
            tipoFin = TipoFinPartida.CAPTURA_TIERRA;
            return;
        }
 
        if (!hayMovimientos(rival, 0)) {
            estado = EstadoPartida.TERMINADA;
            ganador = actual;
            tipoFin = TipoFinPartida.SIN_MOVIMIENTOS;
            return;
        }
 
        turno = rival;
    }
}
 
