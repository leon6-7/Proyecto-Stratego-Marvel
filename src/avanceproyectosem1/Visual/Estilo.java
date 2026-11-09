package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import Piezas.Pieza;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

/** Colores, fuentes y fábricas de componentes para que toda la GUI sea coherente. */
public final class Estilo {

    private Estilo() { }

    public static final Color FONDO   = new Color(18, 22, 38);
    public static final Color PANEL   = new Color(30, 36, 60);
    public static final Color TEXTO   = new Color(235, 238, 245);
    public static final Color ACENTO  = new Color(200, 40, 40);
    public static final Color HEROES  = new Color(30, 90, 200);
    public static final Color VILLANOS = new Color(40, 140, 60);

    public static JButton boton(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(ACENTO);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(240, 48));
        return b;
    }

    public static JLabel etiqueta(String texto, int tamano, boolean negrita) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO);
        l.setFont(new Font("SansSerif", negrita ? Font.BOLD : Font.PLAIN, tamano));
        return l;
    }

    public static String nombreBando(Bando b) {
        switch (b) {
            case HEROES: return "HÉROES";
            default:     return "VILLANOS";
        }
    }

    public static Color colorBando(Bando b) {
        return (b == Bando.HEROES) ? HEROES : VILLANOS;
    }

    /** "B" para bombas, "T" para la tierra y el número para el resto. */
    public static String etiquetaRango(Pieza p) {
        switch (p.getTipo()) {
            case BOMBA:  return "B";
            case TIERRA: return "T";
            default:     return String.valueOf(p.getRango());
        }
    }
}
