package Logica;

import avanceproyectosem1.modelo.Bando;
import Piezas.Agente;
import Piezas.Bomba;
import Piezas.Mistico;
import Piezas.Pieza;
import Piezas.PiezaMovil;
import Piezas.Scout;
import Piezas.Tierra;

import java.util.ArrayList;
import java.util.List;

//40 fichas de Heroes y Villanso
public final class CatalogoPersonajes {

    private CatalogoPersonajes() { }

    private static final Object[][] HEROES = {
        {"Mr. Fantastic", 10}, {"Captain America", 9}, {"Professor X", 8}, {"Nick Fury", 8},
        {"Spider-Man", 7}, {"Wolverine", 7}, {"Namor", 7}, {"Daredevil", 6},
        {"Silver Surfer", 6}, {"Hulk", 6}, {"Iron Man", 6}, {"Thor", 5},
        {"Human Torch", 5}, {"Cyclops", 5}, {"Invisible Woman", 5}, {"Ghost Rider", 4},
        {"Punisher", 4}, {"Blade", 4}, {"Thing", 4}, {"Emma Frost", 3},
        {"She-Hulk", 3}, {"Giant Man", 3}, {"Beast", 3}, {"Colossus", 3},
        {"Gambit", 2}, {"Spider-Girl", 2}, {"Ice Man", 2}, {"Storm", 2},
        {"Phoenix", 2}, {"Dr. Strange", 2}, {"Elektra", 2}, {"Nightcrawler", 2},
        {"Black Widow", 1},
        {"Nova Blast", Pieza.RANGO_BOMBA}, {"Nova Blast", Pieza.RANGO_BOMBA},
        {"Nova Blast", Pieza.RANGO_BOMBA}, {"Nova Blast", Pieza.RANGO_BOMBA},
        {"Nova Blast", Pieza.RANGO_BOMBA}, {"Nova Blast", Pieza.RANGO_BOMBA},
        {"Planet Earth", Pieza.RANGO_TIERRA}
    };

    private static final Object[][] VILLANOS = {
        {"Dr. Doom", 10}, {"Galactus", 9}, {"Kingpin", 8}, {"Magneto", 8},
        {"Apocalypse", 7}, {"Green Goblin", 7}, {"Venom", 7}, {"Bullseye", 6},
        {"Omega Red", 6}, {"Onslaught", 6}, {"Red Skull", 6}, {"Mystique", 5},
        {"Mysterio", 5}, {"Dr. Octopus", 5}, {"Deadpool", 5}, {"Abomination", 4},
        {"Thanos", 4}, {"Black Cat", 4}, {"Sabretooth", 4}, {"Juggernaut", 3},
        {"Rhino", 3}, {"Carnage", 3}, {"Mole Man", 3}, {"Lizard", 3},
        {"Mr. Sinister", 2}, {"Sentinel 1", 2}, {"Ultron", 2}, {"Sandman", 2},
        {"Leader", 2}, {"Viper", 2}, {"Sentinel 2", 2}, {"Electro", 2},
        {"Black Widow", 1},
        {"Pumpkin Bomb", Pieza.RANGO_BOMBA}, {"Pumpkin Bomb", Pieza.RANGO_BOMBA},
        {"Pumpkin Bomb", Pieza.RANGO_BOMBA}, {"Pumpkin Bomb", Pieza.RANGO_BOMBA},
        {"Pumpkin Bomb", Pieza.RANGO_BOMBA}, {"Pumpkin Bomb", Pieza.RANGO_BOMBA},
        {"Planet Earth", Pieza.RANGO_TIERRA}
    };

    //Crea las Piezas de los dos equiposs
    public static List<Pieza> crearEquipo(Bando bando) {
        Object[][] tabla = (bando == Bando.HEROES) ? HEROES : VILLANOS;
        String carpeta = (bando == Bando.HEROES) ? "Heroes" : "Villanos";
        List<Pieza> equipo = new ArrayList<>();
        for (Object[] fila : tabla) {
            String nombre = (String) fila[0];
            int rango = (Integer) fila[1];
            equipo.add(crear(nombre, rango, bando, rutaImagen(carpeta, nombre)));
        }
        return equipo;
    }

    private static Pieza crear(String nombre, int rango, Bando bando, String ruta) {
        switch (rango) {
            case Pieza.RANGO_TIERRA: 
                return new Tierra(nombre, bando, ruta);
            case Pieza.RANGO_BOMBA:  
                return new Bomba(nombre, bando, ruta);
            case 1:                  
                return new Agente(nombre, bando, ruta);
            case 2:                  
                return new Mistico(nombre, bando, ruta);
            case 3:    
                return new Scout(nombre, bando, ruta);
            default:               
                return new PiezaMovil(nombre, rango, bando, ruta);
        }
    }

    private static String rutaImagen(String carpeta, String nombre) {
        String slug = nombre.replace(" ","_");
        return "/Imagenes/" + carpeta + "/" + slug + ".png"; 
    }
}
