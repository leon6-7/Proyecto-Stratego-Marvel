package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.Bando;
import avanceproyectosem1.modelo.Actualizable;
import Logica.MotorJuego;
import avanceproyectosem1.modelo.Jugador;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PanelTurno extends JPanel implements Actualizable {

    private final MotorJuego motor;
    private final Jugador heroes;
    private final Jugador villanos;
    private final JLabel lblTurno = Estilo.etiqueta("", 22, true);

    public PanelTurno(MotorJuego motor, Jugador heroes, Jugador villanos) {
        this.motor = motor;
        this.heroes = heroes;
        this.villanos = villanos;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblTurno.setHorizontalAlignment(JLabel.CENTER);
        add(lblTurno, BorderLayout.CENTER);
        actualizarVista();
    }

    @Override
    public void actualizarVista() {
        Bando turno = motor.getTurno();
        Jugador j = (turno == Bando.HEROES) ? heroes : villanos;
        setBackground(Estilo.colorBando(turno));
        lblTurno.setText("TURNO: " + Estilo.nombreBando(turno) + "  —  " + j.getUsername());
    }
}
