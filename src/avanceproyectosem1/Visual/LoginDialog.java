package avanceproyectosem1.Visual;

import excepciones.CredencialesInvalidasException;
import Logica.GestorJugadores;
import avanceproyectosem1.modelo.Jugador;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/** Formulario de LOG IN (username y password). */
public class LoginDialog extends JDialog {

    private final MenuInicial menuInicial;
    private final GestorJugadores gestor;
    private final JTextField txtUsuario = new JTextField(16);
    private final JPasswordField txtPassword = new JPasswordField(16);

    public LoginDialog(MenuInicial owner, GestorJugadores gestor) {
        super(owner, "LOG IN", true);
        this.menuInicial = owner;
        this.gestor = gestor;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Estilo.PANEL);
        contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        contenido.add(Estilo.etiqueta("Username:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contenido.add(Estilo.etiqueta("Password:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(txtPassword, gbc);

        JButton btnEntrar = Estilo.boton("ENTRAR");
        btnEntrar.setPreferredSize(new java.awt.Dimension(140, 40));
        btnEntrar.addActionListener(e -> intentarLogin());
        JButton btnCancelar = Estilo.boton("CANCELAR");
        btnCancelar.setPreferredSize(new java.awt.Dimension(140, 40));
        btnCancelar.setBackground(new java.awt.Color(90, 96, 120));
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnEntrar);
        botones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 6, 0, 6);
        contenido.add(botones, gbc);

        setContentPane(contenido);
        getRootPane().setDefaultButton(btnEntrar);
        pack();
        setLocationRelativeTo(owner);
    }

    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        try {
            Jugador jugador = gestor.login(usuario, password);
            dispose();
            menuInicial.iniciarSesion(jugador);
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
        }
    }
}
