package Logica;

import avanceproyectosem1.modelo.Bando;
import Piezas.Pieza;
import avanceproyectosem1.modelo.Tablero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public final class FormacionAleatoria {

    private static final Random RND = new Random();

    private FormacionAleatoria() { }

    public static void generar(Tablero tablero) {
        generarBando(tablero, Bando.HEROES);
        generarBando(tablero, Bando.VILLANOS);
    }
    private static void generarBando(Tablero t, Bando bando) {
        final boolean heroes = (bando == Bando.HEROES);
        final int filaTrasera = heroes ? 9 : 0;
        final int filaFrente  = heroes ? 6 : 3;
        final int adelante    = heroes ? -1 : 1;         
        final int segundaFila = filaTrasera + adelante;  
        final int filaMisticoB = filaFrente - adelante;    

        Pieza tierra = null;
        List<Pieza> bombas = new ArrayList<>();
        List<Pieza> misticos = new ArrayList<>();
        List<Pieza> resto = new ArrayList<>();
        for (Pieza p : CatalogoPersonajes.crearEquipo(bando)) {
            switch (p.getTipo()) {
                case TIERRA:  
                    tierra = p;       
                    break;
                case BOMBA:  
                    bombas.add(p);    
                    break;
                case MISTICO: 
                    misticos.add(p);  
                    break;
                default:     
                    resto.add(p);   
                    break;
            }
        }
        Collections.shuffle(resto, RND); 

        int col = 1 + RND.nextInt(8);
        t.colocar(filaTrasera, col, tierra);
        List<int[]> alrededor = new ArrayList<>();
        alrededor.add(new int[] { filaTrasera, col - 1 });
        alrededor.add(new int[] { filaTrasera, col + 1 });
        alrededor.add(new int[] { segundaFila, col });
        List<Pieza> bombasAlrededor = new ArrayList<>(bombas.subList(0, 3));
        bombas.subList(0, 3).clear();
        colocarRecursivo(t, bombasAlrededor, alrededor);
        
        //genera bombas, misticos. y personajes restantes
        colocarRecursivo(t, bombas, celdasLibres(t, filaTrasera, segundaFila));
        colocarRecursivo(t, misticos, celdasLibres(t, filaFrente, filaMisticoB));
        colocarRecursivo(t, resto, celdasLibres(t, filaFrente));
        colocarRecursivo(t, resto,celdasLibres(t, filaTrasera, segundaFila, filaMisticoB, filaFrente));
    }


    private static void colocarRecursivo(Tablero t, List<Pieza> piezas, List<int[]> celdas) {
        if (piezas.isEmpty() || celdas.isEmpty()) {
            return;
        }
        int[] celdaEsp = celdas.remove(RND.nextInt(celdas.size()));
        t.colocar(celdaEsp[0], celdaEsp[1], piezas.remove(0));
        colocarRecursivo(t, piezas, celdas);
    }

    //int... funciona como el parametro int puede ser arreglo o no 
    private static List<int[]> celdasLibres(Tablero t, int... filas) {
        List<int[]> libres = new ArrayList<>();
        
        for (int f : filas) {
            for (int c = 0; c < Tablero.TAMANO; c++) {
                if (t.getPieza(f, c) == null && !t.esZonaProhibida(f, c)) {
                    libres.add(new int[] { f, c });
                }
            }
        }
        return libres;
    }
}
