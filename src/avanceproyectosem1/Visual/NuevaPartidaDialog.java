package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.ModoJuego;
import avanceproyectosem1.modelo.Jugador;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/** Pide el rival, el bando del jugador logueado y la modalidad de la partida. */
public class NuevaPartidaDialog extends JDialog {

    private final JComboBox<Jugador> cmbRival;
    private final JRadioButton rbHeroes = new JRadioButton("HÉROES", true);
    private final JRadioButton rbVillanos = new JRadioButton("VILLANOS");
    private final JRadioButton rbTutorial = new JRadioButton("TUTORIAL (fichas visibles)");
    private final JRadioButton rbClasico = new JRadioButton("CLÁSICO (fichas ocultas)", true);

    private boolean confirmado = false;

    public NuevaPartidaDialog(JFrame owner, List<Jugador> rivales) {
        super(owner, "PARTIDA NUEVA", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cmbRival = new JComboBox<>(rivales.toArray(new Jugador[0]));

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Estilo.PANEL);
        contenido.setBorder(BorderFactory.createEmptyBorder(22, 28, 20, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        contenido.add(Estilo.etiqueta("Rival:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(cmbRival, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contenido.add(Estilo.etiqueta("Tu bando:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(agrupar(rbHeroes, rbVillanos), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        contenido.add(Estilo.etiqueta("Modalidad:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(agrupar(rbTutorial, rbClasico), gbc);

        JButton btnIniciar = Estilo.boton("INICIAR");
        btnIniciar.setPreferredSize(new java.awt.Dimension(140, 40));
        btnIniciar.addActionListener(e -> { confirmado = true; dispose(); });
        JButton btnCancelar = Estilo.boton("CANCELAR");
        btnCancelar.setPreferredSize(new java.awt.Dimension(140, 40));
        btnCancelar.setBackground(new java.awt.Color(90, 96, 120));
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnIniciar);
        botones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 6, 0, 6);
        contenido.add(botones, gbc);

        setContentPane(contenido);
        pack();
        setLocationRelativeTo(owner);
    }

    private JPanel agrupar(JRadioButton a, JRadioButton b) {
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(a);
        grupo.add(b);
        JPanel p = new JPanel();
        p.setLayout(new javax.swing.BoxLayout(p, javax.swing.BoxLayout.Y_AXIS));
        p.setOpaque(false);
        for (JRadioButton rb : new JRadioButton[] { a, b }) {
            rb.setOpaque(false);
            rb.setForeground(Estilo.TEXTO);
            rb.setFocusPainted(false);
            p.add(rb);
        }
        return p;
    }

    public boolean isConfirmado() { return confirmado; }

    public Jugador getRival() { return (Jugador) cmbRival.getSelectedItem(); }

    /** Bando elegido por el jugador logueado. */
    public Bando getBando() { return rbHeroes.isSelected() ? Bando.HEROES : Bando.VILLANOS; }

    public ModoJuego getModo() { return rbTutorial.isSelected() ? ModoJuego.TUTORIAL : ModoJuego.CLASICO; }
}
