/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2;

/**
 *
 * @author User
 */
public class Logica_Cuentas {
    private String name;
    private UsuarioContra cuentas[]=new UsuarioContra[20];
    public int eliminadas=0;
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    }
    
    public UsuarioContra buscar(String name){
        for (UsuarioContra cuenta: cuentas){
            if (cuenta!=null && cuenta.getUser().equals(name)){
                return cuenta;
            }
        }
        return null;
    }
    
    public boolean agregarCuenta(String name, String password){
        if (buscar(name)==null){
            for(int pos=0;pos<cuentas.length;pos++){
                if(cuentas[pos]==null){
                    cuentas[pos]=new UsuarioContra(name,password);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean revisarUsuario(String user, String password){
        for(int contar=0;contar<cuentas.length;contar++){
            if (cuentas[contar]!=null){
                if (user.equals(cuentas[contar].username) && password.equals(cuentas[contar].password)){
                    return true;
                }
            }
            
        }
        return false;
    }
    
    public boolean elminarCuenta(String user, String password){
        for(int contar=0;contar<cuentas.length;contar++){
            if (cuentas[contar] != null && user.equals(cuentas[contar].username) && password.equals(cuentas[contar].password)) {
                cuentas[contar] = null; 
                eliminadas++;
                return true;
            }
        }
        return false;
    }
    
    public boolean cambiarPassword(String user, String pass, String newpass){
        for(int contar=0;contar<cuentas.length;contar++){
            if (cuentas[contar] != null && user.equals(cuentas[contar].username) && pass.equals(cuentas[contar].password)) {
                cuentas[contar].password = newpass; 
                return true;
            }
        }
        return false;
    }
    
    public int CantidadCuentas(){
        int contador=0;
        for(int contar=0;contar<cuentas.length;contar++){
            if (cuentas[contar]!= null){
                contador++;
            }
        }
        return contador;
    }
    
    
    public String[] obtenerUsers(String useractual) {
        String users[] = new String[CantidadCuentas()-1];
        int index = 0;
        for (int contar= 0; contar<cuentas.length;contar++) {
            if (cuentas[contar]!= null&& !cuentas[contar].getUser().equals(useractual)) {
                users[index++] = cuentas[contar].getUser();
            }
        }
        return users;
    }
    
    public String[] Ranking() {
        ordenarPorPuntos();
        String[] reportes = new String[cuentas.length];
        int posicion = 1;
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i] != null && cuentas[i].isActivo()) {
                reportes[i] = "              "+posicion + "     -     " + cuentas[i].getUser() + "     -     " + cuentas[i].getPuntos();
                posicion++;
            }
        }
        return reportes;
    }

    private void ordenarPorPuntos() {
        for (int i = 0; i < cuentas.length - 1; i++) {
            for (int j = i + 1; j < cuentas.length; j++) {
                if (cuentas[j] != null && cuentas[i] != null &&
                        cuentas[j].getPuntos() > cuentas[i].getPuntos()) {
                    UsuarioContra temp = cuentas[i];
                    cuentas[i] = cuentas[j];
                    cuentas[j] = temp;
                }
            }
        }
    }
    
    
    
}
