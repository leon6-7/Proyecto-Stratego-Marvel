/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.Visual;
import avanceproyectosem1.AvanceProyectoSem1;
import avanceproyectosem1.LogicaMenu;

import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Registrar extends JPanel {
    Font def = new Font("Arial", Font.BOLD, 26);

    public Registrar(){
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));    
    JLabel x = new JLabel("Registrar");
    x.setSize(30,40);
    x.setAlignmentX(Component.CENTER_ALIGNMENT);
    x.setFont(def);
    
    
    JLabel ntit = new JLabel("Nombre:");
    ntit.setAlignmentX(Component.CENTER_ALIGNMENT);
    ntit.setMaximumSize(new Dimension(200, 50));
    ntit.setPreferredSize(new Dimension(200,50));
    
    JTextField nombre  = new JTextField();
    nombre.setSize(90, 50);
    nombre.setMaximumSize(new Dimension(200, 50));
    nombre.setPreferredSize(new Dimension(200,50));
    nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JLabel ctitu = new JLabel("Contrasena:");
    ctitu.setAlignmentX(Component.CENTER_ALIGNMENT);
    ctitu.setMaximumSize(new Dimension(200, 50));
    ctitu.setPreferredSize(new Dimension(200,50));
    
    JTextField contra  = new JTextField();
    contra.setAlignmentX(Component.CENTER_ALIGNMENT);
    contra.setMaximumSize(new Dimension(200, 50));
    contra.setPreferredSize(new Dimension(200,50));
    
    JButton enviar = new JButton("Enviar");
    enviar.setAlignmentX(Component.CENTER_ALIGNMENT);
    enviar.setMaximumSize(new Dimension(200, 50));
    enviar.setPreferredSize(new Dimension(200,50));
    enviar.addActionListener(e->{
    if(!nombre.getText().isEmpty()&&!contra.getText().isEmpty()&&contra.getText().toCharArray().length==5){
    LogicaMenu.nuevaCuenta(nombre.getText(), contra.getText());
    AvanceProyectoSem1.cambiarVentana(new MainMenu());
    
    }
   
    });
    JButton salir = new JButton("Salir");
    salir.setAlignmentX(Component.CENTER_ALIGNMENT);
    salir.setMaximumSize(new Dimension(200, 50));
    salir.setPreferredSize(new Dimension(200,50));
    salir.addActionListener(e->{
    AvanceProyectoSem1.cambiarVentana(new MainMenu());
    });
    
    this.add(Box.createVerticalStrut(50));
    this.add(x);
    this.add(Box.createVerticalStrut(30));
    this.add(ntit);
    this.add(Box.createVerticalStrut(10));
    this.add(nombre);
    this.add(Box.createVerticalStrut(25));
    this.add(ctitu);
    this.add(Box.createVerticalStrut(10));
    this.add(contra);
    this.add(Box.createVerticalStrut(25));
    this.add(enviar);
    this.add(Box.createVerticalStrut(25));
    this.add(salir);
    this.add(Box.createVerticalGlue());
    
    }
}
