
package avanceproyectosem1;

public class User {
    
     
    private String contra;
    private String user;
    private int puntos;
    public User(String contra, String user){
    this.contra= contra;
    this.user=user;
    puntos = 0;
    }
    public void sumarPunto(){
    puntos++;
    }
    
    public int getPuntos(){
    return puntos;
    }
    
    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
   
        
    
    }

