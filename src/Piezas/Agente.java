package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;

//destruye rango 10
public class Agente extends PiezaMovil {

    public Agente(String nombre, Bando bando, String rutaImagen) {
        super(nombre, 1, bando, rutaImagen);
    }

    @Override
    public ResultadoCombate atacar(Pieza defensor) {
        if (defensor.getRango() == 10) return ResultadoCombate.GANA;
        return super.atacar(defensor);
    }
}
