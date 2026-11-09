package avanceproyectosem1.Visual;

import Logica.Estadisticas;
import Logica.GestorJugadores;
import Logica.GestorLogs;
import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.LogPartida;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;

/** Estadísticas globales del sistema. */
public class BatallasDialog extends JDialog {

    public BatallasDialog(Window owner, GestorJugadores gestor, GestorLogs logs) {
        super(owner, "BATALLAS", Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        LogPartida[] todos = logs.getLogs();
        int total = Estadisticas.partidasJugadas(todos);
        int heroes = Estadisticas.victorias(todos, Bando.HEROES);       // R3 (recursiva)
        int villanos = Estadisticas.victorias(todos, Bando.VILLANOS);

        JPanel datos = new JPanel();
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
        datos.setOpaque(false);
        agregar(datos, "Usuarios activos: " + gestor.getCantidadActivos(), 16, 0);
        agregar(datos, "Usuarios históricos (incluyendo eliminados): " + gestor.getCantidadHistoricos(), 16, 0);
        agregar(datos, "Partidas jugadas: " + total, 16, 0);
        agregar(datos, "Victorias de los HÉROES: " + heroes, 15, 28);
        agregar(datos, "Victorias de los VILLANOS: " + villanos, 15, 28);

        JButton btnCerrar = Estilo.boton("CERRAR");
        btnCerrar.setPreferredSize(new Dimension(140, 40));
        btnCerrar.addActionListener(e -> dispose());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sur.setOpaque(false);
        sur.add(btnCerrar);

        JPanel raiz = new JPanel(new BorderLayout(10, 14));
        raiz.setBackground(Estilo.PANEL);
        raiz.setBorder(BorderFactory.createEmptyBorder(20, 28, 18, 28));
        raiz.add(Estilo.etiqueta("BATALLAS", 22, true), BorderLayout.NORTH);
        raiz.add(datos, BorderLayout.CENTER);
        raiz.add(sur, BorderLayout.SOUTH);
        setContentPane(raiz);
        pack();
        setLocationRelativeTo(owner);
    }

    private static void agregar(JPanel panel, String texto, int tamano, int sangria) {
        JLabel l = Estilo.etiqueta(texto, tamano, false);
        l.setBorder(BorderFactory.createEmptyBorder(5, sangria, 5, 0));
        panel.add(l);
        panel.add(Box.createVerticalStrut(2));
    }
}
