package avanceproyectosem1.Visual;

import Logica.GestorJugadores;
import Logica.GestorLogs;
import avanceproyectosem1.modelo.Jugador;
import avanceproyectosem1.modelo.LogPartida;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

public class PerfilDialog extends JDialog {

    private final MenuPrincipal menu;
    private final GestorJugadores gestor;
    private final Jugador jugador;

    public PerfilDialog(MenuPrincipal menu, GestorJugadores gestor, GestorLogs logs, Jugador jugador) {
        super(menu, "MI PERFIL", Dialog.ModalityType.APPLICATION_MODAL);
        this.menu = menu;
        this.gestor = gestor;
        this.jugador = jugador;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBackground(Estilo.PANEL);
        raiz.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        JPanel datos = new JPanel();
        datos.setLayout(new javax.swing.BoxLayout(datos, javax.swing.BoxLayout.Y_AXIS));
        datos.setOpaque(false);
        datos.add(Estilo.etiqueta("Username: " + jugador.getUsername(), 20, true));
        datos.add(Estilo.etiqueta("Puntos: " + jugador.getPuntos(), 15, false));
        datos.add(Estilo.etiqueta("Partidas con Héroes: " + jugador.getPartidasHeroes(), 15, false));
        datos.add(Estilo.etiqueta("Partidas con Villanos: " + jugador.getPartidasVillanos(), 15, false));
        datos.add(javax.swing.Box.createVerticalStrut(10));
        datos.add(Estilo.etiqueta("HISTORIAL (más reciente primero):", 14, true));

        JTextArea area = new JTextArea(construirHistorial(logs.getDeUsuario(jugador.getUsername())));
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setBackground(Estilo.FONDO);
        area.setForeground(Estilo.TEXTO);
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(720, 260));

        JButton btnPassword = Estilo.boton("CAMBIAR PASSWORD");
        btnPassword.setPreferredSize(new Dimension(200, 40));
        btnPassword.addActionListener(e -> menu.abrirCambiarPassword(this));
        JButton btnEliminar = Estilo.boton("ELIMINAR MI CUENTA");
        btnEliminar.setPreferredSize(new Dimension(210, 40));
        btnEliminar.addActionListener(e -> {
            menu.abrirEliminarCuenta(this);
            if (!gestor.existe(jugador.getUsername())) {
                dispose();      
            }
        });
        JButton btnCerrar = Estilo.boton("CERRAR");
        btnCerrar.setPreferredSize(new Dimension(140, 40));
        btnCerrar.setBackground(new Color(90, 96, 120));
        btnCerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnPassword);
        botones.add(btnEliminar);
        botones.add(btnCerrar);

        raiz.add(datos, BorderLayout.NORTH);
        raiz.add(scroll, BorderLayout.CENTER);
        raiz.add(botones, BorderLayout.SOUTH);
        setContentPane(raiz);
        pack();
        setLocationRelativeTo(menu);
    }

    private static String construirHistorial(List<LogPartida> logs) {
        if (logs.isEmpty()) {
            return "Aún no has jugado ninguna partida.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < logs.size(); i++) {
            sb.append(i + 1).append(". ").append(logs.get(i)).append("\n\n");
        }
        return sb.toString().trim();
    }
}
