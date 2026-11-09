
package avanceproyectosem1;
import avanceproyectosem1.Visual.login;
import avanceproyectosem1.Visual.MainMenu;
import javax.swing.JPanel;
import javax.swing.JFrame;
public class AvanceProyectoSem1 {
    public static JPanel menu = new MainMenu();
    public static JFrame frame = new JFrame("Inicio");
    public static int pene = 0;
    public static void cambiarVentana(JPanel nuevaVentana){
        frame.getContentPane().removeAll();
        frame.add(nuevaVentana);
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
       frame.setSize(600, 600);
       frame.add(menu);
       frame.setVisible(true);
    }
    
}
