/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.Visual;

import avanceproyectosem1.modelo.*;
import excepciones.MovimientoInvalidoException;
import Logica.MotorJuego;
import Piezas.Pieza;
        
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

//Panel 10x10
public class TableroPanel extends JPanel implements Actualizable {

    private static final int TAM = Tablero.TAMANO; // 10

    private final MotorJuego motor;
    private final CeldaButton[][] celdas = new CeldaButton[TAM][TAM];

    // Selección actual (-1 = ninguna)
    private int selFila = -1;
    private int selCol = -1;
    private List<int[]> destinos = new ArrayList<>();

    private Runnable alJugar;

    public TableroPanel(MotorJuego motor) {
        this.motor = motor;
        setLayout(new GridLayout(TAM, TAM, 1, 1));
        construirCeldas();
        actualizarVista();
    }

    public void setAlJugar(Runnable alJugar) {
        this.alJugar = alJugar;
    }

    private void construirCeldas() {
        Tablero tablero = motor.getTablero();
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                CeldaButton celda = new CeldaButton(f, c);
                celda.setProhibida(tablero.esZonaProhibida(f, c));
                if (!tablero.esZonaProhibida(f, c)) {
                    final int ff = f;
                    final int cc = c;
                    celda.addActionListener(e -> alHacerClic(ff, cc));
                }
                celdas[f][c] = celda;
                add(celda);
            }
        }
    }

    private void alHacerClic(int f, int c) {
        if (motor.isTerminada()) {
            return;
        }

        Pieza clicada = motor.getTablero().getPieza(f, c);

        if (selFila != -1) {
            if (f == selFila && c == selCol) {
                limpiarSeleccion();                    
            } else if (esDestino(f, c)) {
                intentarMover(f, c);                     
                return;
            } else if (clicada != null && clicada.getBando() == motor.getTurno()) {
                seleccionar(f, c);                        
            } else {
                limpiarSeleccion();
            }
            actualizarVista();
            return;
        }

        if (clicada != null && clicada.getBando() == motor.getTurno()) {
            seleccionar(f, c);
            if (destinos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Esta pieza no tiene movimientos disponibles.",
                        "Sin movimientos", JOptionPane.INFORMATION_MESSAGE);
                limpiarSeleccion();
            }
        }
        actualizarVista();
    }

    private void seleccionar(int f, int c) {
        selFila = f;
        selCol = c;
        destinos = motor.getDestinosValidos(f, c);
    }

    private void limpiarSeleccion() {
        selFila = -1;
        selCol = -1;
        destinos = new ArrayList<>();
    }

    private boolean esDestino(int f, int c) {
        for (int[] d : destinos) {
            if (d[0] == f && d[1] == c) return true;
        }
        return false;
    }

    private void intentarMover(int f, int c) {
        try {
            motor.mover(selFila, selCol, f, c);  
        } catch (MovimientoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Movimiento inválido", JOptionPane.WARNING_MESSAGE);
        } finally {
            limpiarSeleccion();
            actualizarVista();
        }
        if (alJugar != null) {
            alJugar.run();
        }
    }

    
    @Override
    public void actualizarVista() {
        Tablero tablero = motor.getTablero();
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                CeldaButton celda = celdas[f][c];
                if (tablero.esZonaProhibida(f, c)) {
                    continue;
                }
                Pieza p = tablero.getPieza(f, c);  
                celda.mostrar(p, esVisible(p));
                celda.setResaltado(calcularResaltado(f, c, p));
            }
        }
        repaint();
    }

    private boolean esVisible(Pieza p) {
        if (p == null) return false;
        if (motor.getModo() == ModoJuego.TUTORIAL) return true;
        return p.getBando() == motor.getTurno() || p.isRevelada();
    }

    private CeldaButton.Resaltado calcularResaltado(int f, int c, Pieza p) {
        if (f == selFila && c == selCol) {
            return CeldaButton.Resaltado.SELECCIONADA;
        }
        if (esDestino(f, c)) {
            return (p != null) ? CeldaButton.Resaltado.OBJETIVO : CeldaButton.Resaltado.DESTINO;
        }
        return CeldaButton.Resaltado.NINGUNO;
    }
}
