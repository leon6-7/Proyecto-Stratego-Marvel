
package avanceproyectosem1;

import java.util.ArrayList;


public class LogicaMenu {
    public static boolean LOGGED = false;
    public static ArrayList<User> UserList = new ArrayList<>();
    public static void nuevaCuenta(String nombre, String Contra){
        User a= new User(nombre, Contra);
        UserList.add(a);
    }
    public static boolean verificar(String contra, String nombre){
    for(User n: UserList){
    if(n.getUser().equals(nombre)&&n.getContra().equals(contra)){
        return true;
    }
    }
    return false;
    }
    
    

}
