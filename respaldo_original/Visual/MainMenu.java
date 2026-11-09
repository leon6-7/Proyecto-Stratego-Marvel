package avanceproyectosem1.Visual;
import avanceproyectosem1.AvanceProyectoSem1;
import avanceproyectosem1.LogicaMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class MainMenu extends JPanel {
    Font def = new Font("Arial", Font.BOLD, 26);
    public MainMenu(){
    if(LogicaMenu.LOGGED){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));    
        JLabel x = new JLabel("Stratego-Marvel");
        x.setSize(30,40);
        x.setAlignmentX(Component.CENTER_ALIGNMENT);
        x.setFont(def);


        JButton p  = new JButton("Play");
        p.setSize(90, 50);
        p.setMaximumSize(new Dimension(200, 50));
        p.setPreferredSize(new Dimension(200,50));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.addActionListener(e->{
        AvanceProyectoSem1.cambiarVentana(new Board());

        });

        JButton perfil = new JButton("Mi Perfil");
        perfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        perfil.setMaximumSize(new Dimension(200, 50));
        perfil.setPreferredSize(new Dimension(200,50));

        
        JButton ranking = new JButton("UNIVERSO MARVEL");
        ranking.setAlignmentX(Component.CENTER_ALIGNMENT);
        ranking.setMaximumSize(new Dimension(200, 50));
        ranking.setPreferredSize(new Dimension(200,50));

        
        JButton unlog  = new JButton("Log Out");
        unlog.setSize(90, 50);
        unlog.setMaximumSize(new Dimension(200, 50));
        unlog.setPreferredSize(new Dimension(200,50));
        unlog.setAlignmentX(Component.CENTER_ALIGNMENT);
        unlog.addActionListener(e->{
            LogicaMenu.LOGGED=false;
            AvanceProyectoSem1.cambiarVentana(new MainMenu());
        });

        this.add(Box.createVerticalStrut(50));
        this.add(x);
        this.add(Box.createVerticalStrut(50));
        this.add(p);
        this.add(Box.createVerticalStrut(50));
        this.add(ranking);
        this.add(Box.createVerticalStrut(50));
        this.add(ranking);
        this.add(Box.createVerticalStrut(50));
        this.add(unlog);
        this.add(Box.createVerticalGlue());
    }
    else if(LogicaMenu.LOGGED==false){
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));    
        JLabel x = new JLabel("Stratego-Marvel");
        x.setSize(30,40);
        x.setAlignmentX(Component.CENTER_ALIGNMENT);
        x.setFont(def);


        JButton p  = new JButton("Log In");
        p.setSize(90, 50);
        p.setMaximumSize(new Dimension(200, 50));
        p.setPreferredSize(new Dimension(200,50));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.addActionListener(e->{
        AvanceProyectoSem1.cambiarVentana(new login());

        });



        JButton reg  = new JButton("Registrar");
        reg.setSize(90, 50);
        reg.setMaximumSize(new Dimension(200, 50));
        reg.setPreferredSize(new Dimension(200,50));
        reg.setAlignmentX(Component.CENTER_ALIGNMENT);
        reg.addActionListener(e->{AvanceProyectoSem1.cambiarVentana(new Registrar());

        });

        JButton opciones = new JButton("Opciones");
        opciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        opciones.setMaximumSize(new Dimension(200, 50));
        opciones.setPreferredSize(new Dimension(200,50));


        JButton salir = new JButton("Salir");
        salir.setAlignmentX(Component.CENTER_ALIGNMENT);
        salir.setMaximumSize(new Dimension(200, 50));
        salir.setPreferredSize(new Dimension(200,50));

        this.add(Box.createVerticalStrut(50));
        this.add(x);
        this.add(Box.createVerticalStrut(50));
        this.add(p);
        this.add(Box.createVerticalStrut(50));
        this.add(reg);
        this.add(Box.createVerticalStrut(50));
        this.add(opciones);
        this.add(Box.createVerticalStrut(50));
        this.add(salir);
        this.add(Box.createVerticalGlue());
    }
}  
  



}
    
