package avanceproyectosem1.Visual;

import Logica.GestorJugadores;
import avanceproyectosem1.modelo.Jugador;
import excepciones.CredencialesInvalidasException;
import excepciones.PasswordInvalidoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

public class CambiarPasswordDialog extends JDialog {

    private final GestorJugadores gestor;
    private final Jugador jugador;
    private final JPasswordField txtActual = new JPasswordField(16);
    private final JPasswordField txtNuevo = new JPasswordField(16);

    public CambiarPasswordDialog(Window owner, GestorJugadores gestor, Jugador jugador) {
        super(owner, "CAMBIAR MI PASSWORD", Dialog.ModalityType.APPLICATION_MODAL);
        this.gestor = gestor;
        this.jugador = jugador;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Estilo.PANEL);
        contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        contenido.add(Estilo.etiqueta("Password actual:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(txtActual, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contenido.add(Estilo.etiqueta("Password nuevo:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(txtNuevo, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        contenido.add(Estilo.etiqueta("(exactamente 5 caracteres)", 11, false), gbc);

        JButton btnCambiar = Estilo.boton("CAMBIAR");
        btnCambiar.setPreferredSize(new Dimension(140, 40));
        btnCambiar.addActionListener(e -> intentarCambiar());
        JButton btnCancelar = Estilo.boton("CANCELAR");
        btnCancelar.setPreferredSize(new Dimension(140, 40));
        btnCancelar.setBackground(new Color(90, 96, 120));
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnCambiar);
        botones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 6, 0, 6);
        contenido.add(botones, gbc);

        setContentPane(contenido);
        getRootPane().setDefaultButton(btnCambiar);
        pack();
        setLocationRelativeTo(owner);
    }

    private void intentarCambiar() {
        String actual = new String(txtActual.getPassword());
        String nuevo = new String(txtNuevo.getPassword());
        try {
            gestor.cambiarPassword(jugador, actual, nuevo);
            JOptionPane.showMessageDialog(this, "Tu password fue actualizado correctamente.",
                    "Password cambiado", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Verificación fallida", JOptionPane.ERROR_MESSAGE);
            txtActual.setText("");
            txtActual.requestFocusInWindow();
        } catch (PasswordInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Password inválido", JOptionPane.ERROR_MESSAGE);
            txtNuevo.setText("");
            txtNuevo.requestFocusInWindow();
        }
    }
}
