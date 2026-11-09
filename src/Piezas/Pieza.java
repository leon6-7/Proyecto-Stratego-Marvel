package Piezas;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ResultadoCombate;
import avanceproyectosem1.modelo.TipoPieza;
import avanceproyectosem1.modelo.Atacable;

// Esta es la clase Abstracta.

public abstract class Pieza implements Atacable {

    public static final int RANGO_TIERRA = 0;
    public static final int RANGO_BOMBA  = 11;

    protected final String nombre;
    protected final int rango;
    protected final Bando bando;
    protected final String rutaImagen;
    protected boolean revelada;
    protected int fila = -1;
    protected int columna = -1;

    protected Pieza(String nombre, int rango, Bando bando, String rutaImagen) {
        this.nombre = nombre;
        this.rango = rango;
        this.bando = bando;
        this.rutaImagen = rutaImagen;
    }

    // ---- Métodos abstractos: cada subclase los implementa distinto ----

    public abstract TipoPieza getTipo();

    public abstract boolean puedeMoverse();

    /** Resultado del combate visto desde esta pieza cuando ATACA al defensor. */
    public abstract ResultadoCombate atacar(Pieza defensor);

    // ---- Atacable: se resuelve invirtiendo el ataque (polimórfico) ----

    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante) {
        return atacante.atacar(this).invertido();
    }

    // ---- Comportamiento común ----

    public void revelar() { this.revelada = true; }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre()     { return nombre; }
    public int getRango()         { return rango; }
    public Bando getBando()       { return bando; }
    public String getRutaImagen() { return rutaImagen; }
    public boolean isRevelada()   { return revelada; }
    public int getFila()          { return fila; }
    public int getColumna()       { return columna; }

    @Override
    public String toString() {
        return nombre + " (" + bando + ", rango " + rango + ")";
    }
}
