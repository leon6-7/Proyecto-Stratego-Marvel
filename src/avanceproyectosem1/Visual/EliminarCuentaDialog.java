package avanceproyectosem1.Visual;

import Logica.GestorJugadores;
import Logica.GestorLogs;
import avanceproyectosem1.modelo.Jugador;
import excepciones.CredencialesInvalidasException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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

/** Confirma la eliminación de la cuenta ingresando el password actual. */
public class EliminarCuentaDialog extends JDialog {

    private final GestorJugadores gestor;
    private final GestorLogs logs;
    private final Jugador jugador;
    private final JPasswordField txtPassword = new JPasswordField(16);
    private boolean eliminada = false;

    public EliminarCuentaDialog(Window owner, GestorJugadores gestor, GestorLogs logs, Jugador jugador) {
        super(owner, "ELIMINAR MI CUENTA", Dialog.ModalityType.APPLICATION_MODAL);
        this.gestor = gestor;
        this.logs = logs;
        this.jugador = jugador;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Estilo.PANEL);
        contenido.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel aviso = Estilo.etiqueta("Esta acción borrará tu cuenta y todos tus datos.", 14, true);
        aviso.setForeground(new Color(255, 120, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contenido.add(aviso, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        contenido.add(Estilo.etiqueta("Confirma tu password:", 14, true), gbc);
        gbc.gridx = 1;
        contenido.add(txtPassword, gbc);

        JButton btnEliminar = Estilo.boton("ELIMINAR");
        btnEliminar.setPreferredSize(new Dimension(140, 40));
        btnEliminar.addActionListener(e -> intentarEliminar());
        JButton btnCancelar = Estilo.boton("CANCELAR");
        btnCancelar.setPreferredSize(new Dimension(140, 40));
        btnCancelar.setBackground(new Color(90, 96, 120));
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(btnEliminar);
        botones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 6, 0, 6);
        contenido.add(botones, gbc);

        setContentPane(contenido);
        pack();
        setLocationRelativeTo(owner);
    }

    private void intentarEliminar() {
        String password = new String(txtPassword.getPassword());
        try {
            gestor.eliminar(jugador, password);
            logs.marcarEliminado(jugador.getUsername());   // las partidas se conservan para las estadísticas
            eliminada = true;
            JOptionPane.showMessageDialog(this, "Tu cuenta fue eliminada.",
                    "Cuenta eliminada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Verificación fallida", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
        }
    }

    public boolean isEliminada() { return eliminada; }
}
