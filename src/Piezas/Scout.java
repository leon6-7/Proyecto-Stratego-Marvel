package Piezas;
import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;
import avanceproyectosem1.modelo.TipoPieza;

//puede destruir bombitas
public class Scout extends PiezaMovil {

    public Scout(String nombre, Bando bando, String rutaImagen) {
        super(nombre, 3, bando, rutaImagen);
    }

    @Override
    public ResultadoCombate atacar(Pieza defensor) {
        if (defensor.getTipo() == TipoPieza.BOMBA) return ResultadoCombate.GANA;
        return super.atacar(defensor);
    }
}
