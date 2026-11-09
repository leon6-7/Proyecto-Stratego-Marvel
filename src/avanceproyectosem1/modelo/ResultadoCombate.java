/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avanceproyectosem1.modelo;
 
public enum ResultadoCombate {
    GANA, PIERDE, EMPATE;
 
    public ResultadoCombate invertido() {
        switch(this) {
            case GANA:   
                return PIERDE;
            case PIERDE: 
                return GANA;
            default:     
                return EMPATE;
        }
    }
}
 