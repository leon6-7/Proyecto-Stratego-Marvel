package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ModoJuego;
import avanceproyectosem1.modelo.Actualizable;
import Logica.FormacionAleatoria;
import Logica.GestorJugadores;
import Logica.GestorLogs;
import Logica.MotorJuego;
import avanceproyectosem1.modelo.Jugador;
import avanceproyectosem1.modelo.Tablero;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.Component;
import java.awt.Window;

/** Ventana principal tras iniciar sesión. */
public class MenuPrincipal extends JFrame implements Actualizable {

    private final GestorJugadores gestor;
    private final GestorLogs logs;
    private final Jugador jugador;

    private final JLabel lblBienvenida = Estilo.etiqueta("", 28, true);
    private final JLabel lblPuntos = Estilo.etiqueta("", 16, false);
    private final JLabel lblPartidas = Estilo.etiqueta("", 16, false);
    private final JMenuItem itemPartidaNueva = new JMenuItem("Partida Nueva");

    public MenuPrincipal(GestorJugadores gestor, GestorLogs logs, Jugador jugador) {
        this.gestor = gestor;
        this.logs = logs;
        this.jugador = jugador;

        setTitle("MARVEL HEROES – STRATEGO  |  " + jugador.getUsername());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);

        setJMenuBar(construirBarraMenu());
        setContentPane(construirContenido());
        actualizarVista();
    }

    // ------------------------------------------------------------------
    // Construcción
    // ------------------------------------------------------------------

    private JMenuBar construirBarraMenu() {
        JMenuBar barra = new JMenuBar();

        // STRATEGO – MARVEL HEROES!
        JMenu menuStratego = new JMenu("STRATEGO – MARVEL HEROES!");
        itemPartidaNueva.addActionListener(e -> iniciarPartidaNueva());
        menuStratego.add(itemPartidaNueva);
        // La opción solo está disponible si existe al menos otro jugador registrado
        menuStratego.addMenuListener(new MenuListener() {
            @Override public void menuSelected(MenuEvent e) {
                boolean hay = gestor.hayRivales(jugador);
                itemPartidaNueva.setEnabled(hay);
                itemPartidaNueva.setToolTipText(hay ? null
                        : "Se necesita al menos otro jugador registrado");
            }
            @Override public void menuDeselected(MenuEvent e) { }
            @Override public void menuCanceled(MenuEvent e) { }
        });
        barra.add(menuStratego);

        // MI PERFIL
        JMenu menuPerfil = new JMenu("MI PERFIL");
        JMenuItem itemLog = new JMenuItem("Log de Mis Últimos Juegos");
        itemLog.addActionListener(e -> new PerfilDialog(this, gestor, logs, jugador).setVisible(true));
        JMenuItem itemPassword = new JMenuItem("Cambiar mi Password");
        itemPassword.addActionListener(e -> abrirCambiarPassword(this));
        JMenuItem itemEliminar = new JMenuItem("Eliminar mi Cuenta");
        itemEliminar.addActionListener(e -> abrirEliminarCuenta(this));
        menuPerfil.add(itemLog);
        menuPerfil.add(itemPassword);
        menuPerfil.add(itemEliminar);
        barra.add(menuPerfil);

        // UNIVERSO MARVEL
        JMenu menuUniverso = new JMenu("UNIVERSO MARVEL");
        JMenuItem itemRanking = new JMenuItem("Ranking");
        itemRanking.addActionListener(e -> new RankingDialog(this, gestor).setVisible(true));
        JMenuItem itemBatallas = new JMenuItem("Batallas");
        itemBatallas.addActionListener(e -> new BatallasDialog(this, gestor, logs).setVisible(true));
        menuUniverso.add(itemRanking);
        menuUniverso.add(itemBatallas);
        barra.add(menuUniverso);

        // CERRAR SESIÓN
        JMenu menuCerrar = new JMenu("CERRAR SESIÓN");
        JMenuItem itemCerrar = new JMenuItem("Cerrar sesión...");
        itemCerrar.addActionListener(e -> cerrarSesion());
        menuCerrar.add(itemCerrar);
        barra.add(menuCerrar);

        return barra;
    }

    private JPanel construirContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilo.FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(Box.createVerticalGlue());
        for (JLabel l : new JLabel[] { lblBienvenida, lblPuntos, lblPartidas }) {
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(l);
            panel.add(Box.createVerticalStrut(12));
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // ------------------------------------------------------------------
    // Acciones
    // ------------------------------------------------------------------

    private void iniciarPartidaNueva() {
        if (!gestor.hayRivales(jugador)) {
            JOptionPane.showMessageDialog(this,
                    "Se necesita al menos otro jugador registrado para iniciar una partida.",
                    "Sin rivales", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NuevaPartidaDialog dialogo = new NuevaPartidaDialog(this, gestor.getRivales(jugador));
        dialogo.setVisible(true);
        if (!dialogo.isConfirmado()) {
            return;
        }

        Jugador rival = dialogo.getRival();
        Bando miBando = dialogo.getBando();
        ModoJuego modo = dialogo.getModo();

        Jugador heroes   = (miBando == Bando.HEROES) ? jugador : rival;
        Jugador villanos = (miBando == Bando.HEROES) ? rival : jugador;

        Tablero tablero = new Tablero();
        FormacionAleatoria.generar(tablero);
        MotorJuego motor = new MotorJuego(tablero, modo);

        heroes.registrarPartida(Bando.HEROES);
        villanos.registrarPartida(Bando.VILLANOS);

        setVisible(false);   // se vuelve a mostrar cuando se cierre la partida
        new PartidaFrame(motor, heroes, villanos, logs, () -> {
            actualizarVista();
            setVisible(true);
        }).setVisible(true);
    }

    /** Abre el diálogo de cambio de password; 'owner' es la ventana que lo invoca. */
    public void abrirCambiarPassword(Window owner) {
        new CambiarPasswordDialog(owner, gestor, jugador).setVisible(true);
    }

    /** Abre el diálogo de eliminación; si se elimina, cierra el menú y vuelve al Menú Inicial. */
    public void abrirEliminarCuenta(Window owner) {
        EliminarCuentaDialog dialogo = new EliminarCuentaDialog(owner, gestor, logs, jugador);
        dialogo.setVisible(true);
        if (dialogo.isEliminada()) {
            dispose();
            new MenuInicial(gestor, logs).setVisible(true);
        }
    }

    private void cerrarSesion() {
        int r = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?",
                "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            dispose();
            new MenuInicial(gestor, logs).setVisible(true);
        }
    }

    @Override
    public void actualizarVista() {
        lblBienvenida.setText("Bienvenido, " + jugador.getUsername());
        lblPuntos.setText("Puntos: " + jugador.getPuntos());
        lblPartidas.setText("Partidas con Héroes: " + jugador.getPartidasHeroes()
                + "   |   Partidas con Villanos: " + jugador.getPartidasVillanos());
    }
}
