package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;
import avanceproyectosem1.modelo.TipoPieza;

//Al capturarla ganas
public class Tierra extends Pieza {

    public Tierra(String nombre, Bando bando, String rutaImagen) {
        super(nombre, RANGO_TIERRA, bando, rutaImagen);
    }

    @Override
    public TipoPieza getTipo() { return TipoPieza.TIERRA; }

    @Override
    public boolean puedeMoverse() { return false; }

    @Override
    public ResultadoCombate atacar(Pieza defensor) { return ResultadoCombate.PIERDE; }
}
