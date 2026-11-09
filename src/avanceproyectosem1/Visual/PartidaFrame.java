package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ModoJuego;
import Logica.GestorLogs;
import Logica.MotorJuego;
import avanceproyectosem1.modelo.Jugador;
import avanceproyectosem1.modelo.LogPartida;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//
public class PartidaFrame extends JFrame {

    //Puntos que recibe el oponente en retiro y victoria normal
    public static final int PUNTOS_RETIRO = 3;
    public static final int PUNTOS_VICTORIA = 3;

    private static final String CARTA_TABLERO = "tablero";
    private static final String CARTA_CORTINA = "cortina";

    private final MotorJuego motor;
    private final Jugador heroes;
    private final Jugador villanos;
    private final GestorLogs logs;

    private final TableroPanel tableroPanel;
    private final PanelTurno panelTurno;
    private final PanelVencidas vencidasHeroes;
    private final PanelVencidas vencidasVillanos;

    private final CardLayout cartas = new CardLayout();
    private final JPanel centro = new JPanel(cartas);
    private final JLabel lblCortina = Estilo.etiqueta("", 30, true);

    public PartidaFrame(MotorJuego motor, Jugador heroes, Jugador villanos, GestorLogs logs, Runnable alCerrar) {
        this.motor = motor;
        this.heroes = heroes;
        this.villanos = villanos;
        this.logs = logs;

        setTitle("STRATEGO – " + heroes.getUsername() + " (HÉROES) vs "
                + villanos.getUsername() + " (VILLANOS)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                if (alCerrar != null) alCerrar.run();
            }
        });

        tableroPanel = new TableroPanel(motor);
        tableroPanel.setAlJugar(this::alJugar);
        panelTurno = new PanelTurno(motor, heroes, villanos);
        vencidasHeroes = new PanelVencidas(motor, Bando.HEROES);
        vencidasVillanos = new PanelVencidas(motor, Bando.VILLANOS);

        centro.add(tableroPanel, CARTA_TABLERO);
        centro.add(construirCortina(), CARTA_CORTINA);

        JButton btnRetiro = Estilo.boton("RETIRO");
        btnRetiro.addActionListener(e -> retirar());
        JPanel sur = new JPanel();
        sur.setBackground(Estilo.FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        sur.add(btnRetiro);

        JPanel raiz = new JPanel(new BorderLayout(8, 8));
        raiz.setBackground(Estilo.FONDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        raiz.add(panelTurno, BorderLayout.NORTH);
        raiz.add(vencidasHeroes, BorderLayout.WEST);
        raiz.add(centro, BorderLayout.CENTER);
        raiz.add(vencidasVillanos, BorderLayout.EAST);
        raiz.add(sur, BorderLayout.SOUTH);
        setContentPane(raiz);

        pack();
        setLocationRelativeTo(null);

        if (motor.getModo() == ModoJuego.CLASICO) {
            mostrarCortina();
        } else {
            cartas.show(centro, CARTA_TABLERO);
        }
    }
    private JPanel construirCortina() {
        JPanel cortina = new JPanel(new GridBagLayout());
        cortina.setBackground(Estilo.PANEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        cortina.add(lblCortina, gbc);
        gbc.gridy = 1;
        cortina.add(Estilo.etiqueta("Pasa el control a este jugador y presiona el botón.", 15, false), gbc);

        JButton btnMostrar = Estilo.boton("MOSTRAR MIS FICHAS");
        btnMostrar.addActionListener(e -> cartas.show(centro, CARTA_TABLERO));
        gbc.gridy = 2;
        cortina.add(btnMostrar, gbc);
        return cortina;
    }

    private void mostrarCortina() {
        Bando turno = motor.getTurno();
        lblCortina.setText("Turno de " + jugadorDe(turno).getUsername()
                + " (" + Estilo.nombreBando(turno) + ")");
        lblCortina.setForeground(Estilo.colorBando(turno).brighter());
        cartas.show(centro, CARTA_CORTINA);
    }

    private void alJugar() {
        panelTurno.actualizarVista();
        vencidasHeroes.actualizarVista();
        vencidasVillanos.actualizarVista();

        if (motor.isTerminada()) {
            finalizarPartida(PUNTOS_VICTORIA);
        } else if (motor.getModo() == ModoJuego.CLASICO) {
            mostrarCortina();
        }
    }

    private void retirar() {
        if (motor.isTerminada()) return;
        Bando quien = motor.getTurno();         
        Jugador quienSeRetira = jugadorDe(quien);
        Jugador oponente = jugadorDe(quien.opuesto());

        int r = JOptionPane.showConfirmDialog(this,
                quienSeRetira.getUsername() + " (" + Estilo.nombreBando(quien) + "), ¿seguro que deseas retirarte?\n"
                        + oponente.getUsername() + " recibirá " + PUNTOS_RETIRO + " puntos.",
                "Confirmar retiro", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;

        motor.retirar(quien);
        finalizarPartida(PUNTOS_RETIRO);
    }

    private void finalizarPartida(int puntosGanador) {
        Jugador ganador = jugadorDe(motor.getGanador());
        Jugador perdedor = jugadorDe(motor.getPerdedor());
        ganador.sumarPuntos(puntosGanador);

        LogPartida log = new LogPartida(ganador.getUsername(), perdedor.getUsername(),
                motor.getGanador(), motor.getTipoFin());
        logs.agregar(log);

        JOptionPane.showMessageDialog(this,
                log.toString().replace(" — ", "\n") + "\n\n" + ganador.getUsername()
                        + " recibe " + puntosGanador + " puntos.",
                "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private Jugador jugadorDe(Bando b) {
        return (b == Bando.HEROES) ? heroes : villanos;
    }
}
