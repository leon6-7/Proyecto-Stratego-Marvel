/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.Visual;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
/**
 *
 * @author Abril
 */
public class Board extends JPanel{
    public Board(){
    this.setPreferredSize(new Dimension(1920, 3000));
    BorderLayout Layout = new BorderLayout(10,10);
    this.setLayout(Layout);
    
    
    //Layout center
    GridLayout board = new GridLayout(10,10);
    JPanel center = new JPanel();
    center.setPreferredSize(new Dimension(1000,1000));
    center.setLayout(board);
    JPanel[] s = new JPanel[100];
    for(int j=0;j<100;j++){
    s[j]= new JPanel();
    s[j].setLayout(new BorderLayout());
    s[j].setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
    center.add(s[j]);
    }
    this.add(center, BorderLayout.CENTER);
    
    //Layout north
    JPanel north = new JPanel();
        BoxLayout n = new BoxLayout(north, BoxLayout.X_AXIS);
        
        north.setLayout(n);
        north.setPreferredSize(new Dimension(1920, 60)); 
        north.setBackground(Color.LIGHT_GRAY);
        north.add(Box.createHorizontalStrut(40)); // Espaciado inicial
        
        //Personajes Enemigos
        JPanel pe1 = new JPanel();
        pe1.add(new JLabel("A"));
        JPanel pe2 = new JPanel();
        pe2.add(new JLabel("B"));
        JPanel pe3 = new JPanel();
        pe3.add(new JLabel("C"));
        JPanel pe4 = new JPanel();
        pe4.add(new JLabel("D"));

        north.add(pe1);
        north.add(Box.createHorizontalStrut(10));
        north.add(pe2);
        north.add(Box.createHorizontalStrut(10));
        north.add(pe3);
        north.add(Box.createHorizontalStrut(10));
        north.add(pe4);
        north.add(Box.createHorizontalStrut(40));
       
        this.add(north, BorderLayout.NORTH);
     //Layout South
    JPanel south = new JPanel();
        BoxLayout sur = new BoxLayout(south, BoxLayout.X_AXIS);
        
        south.setLayout(sur);
        south.setPreferredSize(new Dimension(1920, 60)); 
        south.setBackground(Color.LIGHT_GRAY);
        south.add(Box.createHorizontalStrut(40)); // Espaciado inicial
        
        //Personajes Enemigos
        JPanel p1 = new JPanel();
        p1.add(new JLabel("A"));
        JPanel p2 = new JPanel();
        p2.add(new JLabel("B"));
        JPanel p3 = new JPanel();
        p3.add(new JLabel("C"));
        JPanel p4 = new JPanel();
        p4.add(new JLabel("D"));
        JPanel p5 = new JPanel();
        p5.add(new JLabel("E"));
        
        south.add(p1);
        south.add(Box.createHorizontalStrut(10));
        south.add(p2);
        south.add(Box.createHorizontalStrut(10));
        south.add(p3);
        south.add(Box.createHorizontalStrut(10));
        south.add(p4);
        south.add(Box.createHorizontalStrut(10));
        south.add(p5);
        south.add(Box.createHorizontalStrut(40));
 
        this.add(south, BorderLayout.SOUTH);    
      //Layout sides
      JPanel east = new JPanel();
        BoxLayout este = new BoxLayout(east, BoxLayout.Y_AXIS);
      east.setLayout(este);
      east.add(Box.createVerticalStrut(50));
      east.add(new JLabel("PPPPPP"));
      east.add(Box.createVerticalStrut(50));
              this.add(east, BorderLayout.EAST);    

      //Layout sides west
      JPanel west = new JPanel();
        BoxLayout oeste = new BoxLayout(west, BoxLayout.Y_AXIS);
      west.setLayout(oeste);
      west.add(Box.createVerticalStrut(50));
      west.add(new JLabel("PPPPPP"));
      west.add(Box.createVerticalStrut(50));
               this.add(west, BorderLayout.WEST);    

      
        
       
        
       
        
        
        }
    
}
    

