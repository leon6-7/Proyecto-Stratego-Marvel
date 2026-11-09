package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;
import avanceproyectosem1.modelo.Tablero;
import avanceproyectosem1.modelo.TipoPieza;
import avanceproyectosem1.modelo.Movible;

//req de concreta
public class PiezaMovil extends Pieza implements Movible {

    public PiezaMovil(String nombre, int rango, Bando bando, String rutaImagen) {
        super(nombre, rango, bando, rutaImagen);
    }

    @Override
    public TipoPieza getTipo() { return TipoPieza.MOVIL; }

    @Override
    public boolean puedeMoverse() { return true; }

    @Override
    public int getAlcance() { return 1; }

    @Override
    public boolean puedeMoverA(int f, int c, Tablero t) {
        if (!t.enRango(f, c) || t.esZonaProhibida(f, c)) return false;
        int distancia = Math.abs(f - fila) + Math.abs(c - columna);
        if (distancia != 1) return false;
        Pieza destino = t.getPieza(f, c);
        return destino == null || destino.getBando() != bando;
    }

    @Override
    public ResultadoCombate atacar(Pieza defensor) {
        switch (defensor.getTipo()) {
            case BOMBA:  return ResultadoCombate.PIERDE;
            case TIERRA: return ResultadoCombate.GANA;
            default:
                if (rango > defensor.getRango()) return ResultadoCombate.GANA;
                if (rango < defensor.getRango()) return ResultadoCombate.PIERDE;
                return ResultadoCombate.EMPATE;
        }
    }
}
