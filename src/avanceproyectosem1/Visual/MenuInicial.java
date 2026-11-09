package avanceproyectosem1.Visual;

import Logica.GestorJugadores;
import Logica.GestorLogs;
import avanceproyectosem1.modelo.Jugador;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/** Primera ventana del programa: LOG IN y CREAR PLAYER. */
public class MenuInicial extends JFrame {

    private final GestorJugadores gestor;
    private final GestorLogs logs;

    public MenuInicial(GestorJugadores gestor, GestorLogs logs) {
        this.gestor = gestor;
        this.logs = logs;

        setTitle("MARVEL HEROES – STRATEGO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // única ventana que cierra el programa
        setSize(520, 460);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Estilo.FONDO);
        fondo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setContentPane(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(6, 0, 6, 0);

        JLabel titulo = Estilo.etiqueta("MARVEL HEROES", 38, true);
        titulo.setForeground(new Color(235, 60, 60));
        gbc.gridy = 0;
        fondo.add(titulo, gbc);

        JLabel subtitulo = Estilo.etiqueta("STRATEGO GAME", 22, true);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        fondo.add(subtitulo, gbc);

        JButton btnLogin = Estilo.boton("LOG IN");
        btnLogin.addActionListener(e -> new LoginDialog(this, gestor).setVisible(true));
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 0, 8, 0);
        fondo.add(btnLogin, gbc);

        JButton btnCrear = Estilo.boton("CREAR PLAYER");
        btnCrear.addActionListener(e -> new CrearPlayerDialog(this, gestor).setVisible(true));
        gbc.gridy = 3;
        fondo.add(btnCrear, gbc);
    }

    /** Se invoca al iniciar sesión: cierra este menú y abre el Menú Principal. */
    public void iniciarSesion(Jugador jugador) {
        dispose();
        new MenuPrincipal(gestor, logs, jugador).setVisible(true);
    }
}
