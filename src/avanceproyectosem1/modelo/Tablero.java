/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.modelo;

import excepciones.MovimientoInvalidoException;
import Piezas.Pieza;
public class Tablero {

    public static final int TAMANO = 10;

    private final Pieza[][] casillas = new Pieza[TAMANO][TAMANO];

    public boolean enRango(int f, int c) {
        return f >= 0 && f < TAMANO && c >= 0 && c < TAMANO;
    }

    public boolean esZonaProhibida(int f, int c) {
        return (f == 4 || f == 5) && ((c == 2 || c == 3) || (c == 6 || c == 7));
    }

    public Pieza getPieza(int f, int c) {
        return enRango(f, c) ? casillas[f][c] : null;
    }

    public boolean estaVacia(int f, int c) {
        return getPieza(f, c) == null;
    }

    public void colocar(int f, int c, Pieza p) {
        if (!enRango(f, c) || esZonaProhibida(f, c)) {
            throw new IllegalArgumentException("Celda no válida: " + f + "," + c);
        }
        casillas[f][c] = p;
        if (p != null) p.setPosicion(f, c);
    }

    public void quitar(int f, int c) {
        if (enRango(f, c)) casillas[f][c] = null;
    }

    public void mover(int fo, int co, int fd, int cd) throws MovimientoInvalidoException {
        Pieza p = getPieza(fo, co);
        if (p == null) {
            throw new MovimientoInvalidoException("No hay ninguna pieza en la celda de origen.");
        }
        if (!enRango(fd, cd) || esZonaProhibida(fd, cd)) {
            throw new MovimientoInvalidoException("La celda de destino no está permitida.");
        }
        if (!estaVacia(fd, cd)) {
            throw new MovimientoInvalidoException("La celda de destino está ocupada.");
        }
        casillas[fo][co] = null;
        casillas[fd][cd] = p;
        p.setPosicion(fd, cd);
    }

  
    public boolean caminoDespejado(int f, int c, int df, int dc, int pasos) {
        if (pasos == 0) return true;
        int nf = f + df;
        int nc = c + dc;
        if (!enRango(nf, nc) || esZonaProhibida(nf, nc) || !estaVacia(nf, nc)) return false;
        return caminoDespejado(nf, nc, df, dc, pasos - 1);
    }
}

