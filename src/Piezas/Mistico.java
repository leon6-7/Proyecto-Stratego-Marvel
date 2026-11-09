package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.TipoPieza;
import avanceproyectosem1.modelo.Tablero;
import avanceproyectosem1.modelo.Movible;
public class Mistico extends PiezaMovil implements Movible {

    public Mistico(String nombre, Bando bando, String rutaImagen) {
        super(nombre, 2, bando, rutaImagen);
    }

    @Override
    public TipoPieza getTipo() { return TipoPieza.MISTICO; }

    @Override
    public int getAlcance() { return Tablero.TAMANO - 1; }

    @Override
    public boolean puedeMoverA(int f, int c, Tablero t) {
        if (!t.enRango(f, c) || t.esZonaProhibida(f, c)) return false;
        int df = f - fila;
        int dc = c - columna;
        if ((df != 0) == (dc != 0)) return false;  
        int distancia = Math.abs(df) + Math.abs(dc);
        Pieza destino = t.getPieza(f, c);

        if (distancia == 1) {                        
            return destino == null || destino.getBando() != bando;
        }
        if (destino != null) return false;         
        return t.caminoDespejado(fila, columna, Integer.signum(df), Integer.signum(dc), distancia - 1);
    }
}
