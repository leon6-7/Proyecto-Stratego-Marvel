package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;
import avanceproyectosem1.modelo.TipoPieza;

public class Bomba extends Pieza {

    public Bomba(String nombre, Bando bando, String rutaImagen) {
        super(nombre, RANGO_BOMBA, bando, rutaImagen);
    }

    @Override
    public TipoPieza getTipo() { return TipoPieza.BOMBA; }

    @Override
    public boolean puedeMoverse() { return false; }

    @Override
    public ResultadoCombate atacar(Pieza defensor) { return ResultadoCombate.PIERDE; }
}
