package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.Actualizable;
import Logica.MotorJuego;
import Piezas.Pieza;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class PanelVencidas extends JPanel implements Actualizable {

    private final MotorJuego motor;
    private final Bando bando;
    private final JLabel lblTitulo = Estilo.etiqueta("", 14, true);
    private final JPanel lista = new JPanel();

    public PanelVencidas(MotorJuego motor, Bando bando) {
        this.motor = motor;
        this.bando = bando;

        setLayout(new BorderLayout());
        setBackground(Estilo.PANEL);
        setBorder(BorderFactory.createLineBorder(Estilo.colorBando(bando), 3));
        setPreferredSize(new Dimension(250, 600));

        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(lblTitulo, BorderLayout.NORTH);

        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(Estilo.PANEL);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Estilo.PANEL);
        add(scroll, BorderLayout.CENTER);

        actualizarVista();
    }

    @Override
    public void actualizarVista() {
        lista.removeAll();
        int total = 0;
        for (Pieza p : motor.getVencidas(bando)) {
            JLabel l = Estilo.etiqueta("[" + Estilo.etiquetaRango(p) + "]  " + p.getNombre(), 13, false);
            l.setForeground(new Color(220, 224, 235));
            l.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 6));
            lista.add(l);
            total++;
        }
        lblTitulo.setText("VENCIDOS " + Estilo.nombreBando(bando) + " (" + total + ")");
        lista.revalidate();
        lista.repaint();
    }
}
