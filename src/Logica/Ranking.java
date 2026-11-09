package Logica;

import avanceproyectosem1.modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

public final class Ranking {

    private Ranking() { }

    public static List<Jugador> construir(List<Jugador> jugadores) {
        List<Jugador> ordenada = new ArrayList<>();
        for (Jugador j : jugadores) {
            insertarOrdenado(ordenada, j, 0);
        }
        return ordenada;
    }

   
    public static void insertarOrdenado(List<Jugador> lista, Jugador nuevo, int indice) {
        if (indice >= lista.size() || lista.get(indice).getPuntos() < nuevo.getPuntos()) {
            lista.add(indice, nuevo);
            return;
        }
        insertarOrdenado(lista, nuevo, indice + 1);
    }

    public static String formatear(int posicion, Jugador j) {
        return posicion + " — " + j.getUsername() + " — " + j.getPuntos();
    }
}
