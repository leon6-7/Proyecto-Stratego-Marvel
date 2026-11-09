package avanceproyectosem1;

import Logica.GestorJugadores;
import Logica.GestorLogs;
import avanceproyectosem1.Visual.MenuInicial;

public class AvanceProyectoSem1 {

    public static void main(String[] args) {
        new MenuInicial(new GestorJugadores(), new GestorLogs()).setVisible(true);
    }
}
