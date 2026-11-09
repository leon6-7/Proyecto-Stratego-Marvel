
package avanceproyectosem1.Visual;
import avanceproyectosem1.modelo.Bando;
import Piezas.Pieza;
import avanceproyectosem1.modelo.TipoPieza;

import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;


public class CeldaButton extends JButton {

    public enum Resaltado { NINGUNO, SELECCIONADA, DESTINO, OBJETIVO }

    private static final Color COLOR_HEROES   = Color.GREEN;
    private static final Color COLOR_VILLANOS = Color.RED;
    private static final Color COLOR_OCULTA   = Color.BLUE;
    private static final Color COLOR_PROHIBIDA = Color.darkGray;
    private static final Color CASILLA_A = new Color(52, 62, 84);
    private static final Color CASILLA_B = new Color(44, 54, 76);

    /** Caché de imágenes para no leer el disco en cada repintado. */
    private static final Map<String, Image> CACHE = new HashMap<>();

    private final int fila;
    private final int columna;

    private Pieza pieza;
    private boolean piezaVisible;
    private boolean prohibida;
    private Resaltado resaltado = Resaltado.NINGUNO;

    public CeldaButton(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        setPreferredSize(new Dimension(64, 64));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    public int getFila()    { return fila; }
    public int getColumna() { return columna; }

    public void setProhibida(boolean prohibida) {
        this.prohibida = prohibida;
        setEnabled(!prohibida);
    }

    public void mostrar(Pieza pieza, boolean visible) {
        this.pieza = pieza;
        this.piezaVisible = visible;
        if (pieza != null && visible) {
            setToolTipText(pieza.getNombre() + " (Rango " + etiquetaRango(pieza) + ")");
        } else if (pieza != null) {
            setToolTipText("Pieza oculta");
        } else {
            setToolTipText(null);
        }
        repaint();
    }

    public void setResaltado(Resaltado r) {
        this.resaltado = r;
        repaint();
    }

    // Dibujo
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo de la casilla
        if (prohibida) {
            g2.setColor(COLOR_PROHIBIDA);
        } else {
            g2.setColor(((fila + columna) % 2 == 0) ? CASILLA_A : CASILLA_B);
        }
        g2.fillRect(0, 0, w, h);

        if (prohibida) {
            dibujarProhibida(g2, w, h);
        } else if (pieza != null) {
            dibujarPieza(g2, w, h);
        }

        dibujarResaltado(g2, w, h);
        g2.dispose();
    }

    private void dibujarProhibida(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(255, 255, 255, 60));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(10, 10, w - 10, h - 10);
        g2.drawLine(w - 10, 10, 10, h - 10);
    }

    private void dibujarPieza(Graphics2D g2, int w, int h) {
        int m = 4; 
        int cw = w - 2 * m;
        int ch = h - 2 * m;

        Color colorBando = (pieza.getBando() == Bando.HEROES) ? COLOR_HEROES : COLOR_VILLANOS;

        if (!piezaVisible) {
            // Ficha oculta:
            g2.setColor(COLOR_OCULTA);
            g2.fillRoundRect(m, m, cw, ch, 12, 12);
            g2.setColor(colorBando);
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(m, m, cw, ch, 12, 12);
            g2.setFont(new Font("SansSerif", Font.BOLD, 26));
            centrarTexto(g2, "?", w, h, colorBando);
            return;
        }

        // Ficha visible
        g2.setColor(colorBando.darker());
        g2.fillRoundRect(m, m, cw, ch, 12, 12);

        Image img = cargarImagen(pieza.getRutaImagen());
        if (img != null) {
            g2.setClip(new java.awt.geom.RoundRectangle2D.Float(m, m, cw, ch, 12, 12));
            g2.drawImage(img, m, m, cw, ch, null);
            g2.setClip(null);
        } 
        

        g2.setColor(colorBando);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(m, m, cw, ch, 12, 12);

        // Insignia con el rango en la esquina superior izquierda
        String rango = etiquetaRango(pieza);
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        int bw = Math.max(18, fm.stringWidth(rango) + 8);
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(m + 2, m + 2, bw, 18, 8, 8);
        g2.setColor(Color.WHITE);
        g2.drawString(rango, m + 2 + (bw - fm.stringWidth(rango)) / 2, m + 2 + 13);

        // Nombre abreviado en la parte inferior
        String nombre = pieza.getNombre();
        if (nombre != null) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 9));
            fm = g2.getFontMetrics();
            String n = nombre;
            while (n.length() > 3 && fm.stringWidth(n) > cw - 4) {
                n = n.substring(0, n.length() - 1);
            }
            int ny = h - m - 3;
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(m, ny - 10, cw, 13);
            g2.setColor(Color.WHITE);
            g2.drawString(n, m + (cw - fm.stringWidth(n)) / 2, ny);
        }
    }

    private void dibujarResaltado(Graphics2D g2, int w, int h) {
        switch (resaltado) {
            case SELECCIONADA:
                g2.setColor(new Color(255, 215, 0));
                g2.setStroke(new BasicStroke(4f));
                g2.drawRect(2, 2, w - 5, h - 5);
                break;
            case DESTINO:
                g2.setComposite(AlphaComposite.SrcOver.derive(0.45f));
                g2.setColor(new Color(60, 220, 100));
                g2.fillRect(0, 0, w, h);
                break;
            case OBJETIVO:
                g2.setComposite(AlphaComposite.SrcOver.derive(0.45f));
                g2.setColor(new Color(230, 50, 50));
                g2.fillRect(0, 0, w, h);
                break;
            case NINGUNO:
            default:
                break;
        }
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private void centrarTexto(Graphics2D g2, String texto, int w, int h, Color color) {
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(color);
        g2.drawString(texto, (w - fm.stringWidth(texto)) / 2, (h + fm.getAscent()) / 2 - 3);
    }

    private static String etiquetaRango(Pieza p) {
        switch (p.getTipo()) {
            case BOMBA:  return "B";
            case TIERRA: return "T";
            default:     return String.valueOf(p.getRango());
        }
    }

    private static Image cargarImagen(String ruta) {
        if (ruta == null || ruta.isEmpty()) return null;
        if (CACHE.containsKey(ruta)) return CACHE.get(ruta);
        Image img = null;
        java.net.URL url = CeldaButton.class.getResource(ruta);   // busca dentro de src/ (classpath)
        if (url != null) {
            img = new ImageIcon(url).getImage();
        }
        CACHE.put(ruta, img); // se guarda también null para no reintentar
        return img;
    }
}
