package avanceproyectosem1.Visual;

import excepciones.PasswordInvalidoException;
import excepciones.UsuarioDuplicadoException;
import Logica.GestorJugadores;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/** Formulario para registrar un nuevo jugador. */
public class CrearPlayerDialog extends JDialog {

    private final GestorJugadores gestor;
    private final JTextField txtUsuario = new JTextField(16);
    private final JPasswordField txtPassword = new JPasswordField(16);

    public CrearPlayerDialog(JFrame owner, GestorJugadores gestor) {
        super(owner, "CREAR PLAYER", true);
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

        gbc.gridx = 1; gbc.gridy = 2;
        contenido.add(Estilo.etiqueta("(exactamente 5 caracteres)", 11, false), gbc);

        JButton btnCrear = Estilo.boton("CREAR");
        btnCrear.setPreferredSize(new java.awt.Dimension(140, 40));
        btnCrear.addActionListener(e -> intentarCrear());
        JButton btnCancelar = Estilo.boton("CANCELAR");
        btnCancelar.setPreferredSize(new java.awt.Dimension(140, 40));
        btnCancelar.setBackground(new java.awt.Color(90, 96, 120));
        btnCancelar.addActionListener(e -> dispose());   // vuelve al Menú Inicial

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnCrear);
        botones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 6, 0, 6);
        contenido.add(botones, gbc);

        setContentPane(contenido);
        getRootPane().setDefaultButton(btnCrear);
        pack();
        setLocationRelativeTo(owner);
    }

    private void intentarCrear() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El username no puede estar vacío.",
                    "Dato requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            gestor.crear(usuario, password);
            JOptionPane.showMessageDialog(this,
                    "Jugador \"" + usuario + "\" creado correctamente.",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (UsuarioDuplicadoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Username en uso", JOptionPane.ERROR_MESSAGE);
            txtUsuario.selectAll();
            txtUsuario.requestFocusInWindow();
        } catch (PasswordInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Password inválido", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
        }
    }
}
