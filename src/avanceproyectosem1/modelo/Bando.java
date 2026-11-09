/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.modelo;

/**
 *
 * @author Abril
 */
public enum Bando {
    HEROES, VILLANOS;
    
    
    public Bando opuesto(){
    if(this==Bando.HEROES){
    return Bando.VILLANOS;
    }
    else
        return Bando.HEROES;
    }
}
