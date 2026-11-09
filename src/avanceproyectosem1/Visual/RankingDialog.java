package avanceproyectosem1.Visual;

import Logica.GestorJugadores;
import Logica.Ranking;
import avanceproyectosem1.modelo.Jugador;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.List;

public class RankingDialog extends JDialog {

    public RankingDialog(Window owner, GestorJugadores gestor) {
        super(owner, "RANKING", Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<Jugador> ordenados = Ranking.construir(gestor.getActivos());
        StringBuilder sb = new StringBuilder();
        if (ordenados.isEmpty()) {
            sb.append("No hay jugadores registrados.");
        }
        for (int i = 0; i < ordenados.size(); i++) {
            sb.append(Ranking.formatear(i + 1, ordenados.get(i))).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString().trim());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.BOLD, 18));
        area.setBackground(Estilo.FONDO);
        area.setForeground(Estilo.TEXTO);
        area.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(460, 320));

        JButton btnCerrar = Estilo.boton("CERRAR");
        btnCerrar.setPreferredSize(new Dimension(140, 40));
        btnCerrar.addActionListener(e -> dispose());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sur.setOpaque(false);
        sur.add(btnCerrar);

        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBackground(Estilo.PANEL);
        raiz.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        raiz.add(Estilo.etiqueta("RANKING", 22, true), BorderLayout.NORTH);
        raiz.add(scroll, BorderLayout.CENTER);
        raiz.add(sur, BorderLayout.SOUTH);
        setContentPane(raiz);
        pack();
        setLocationRelativeTo(owner);
    }
}
