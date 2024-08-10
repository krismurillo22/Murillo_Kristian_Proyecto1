
package proyecto_2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsuarioContra {

    public String username;
    public String password;
    public String fecha;
    public String hora;
    public int puntos;
    public boolean activo;
    int partidasHeroes;
    int partidasVillanos;
    int parJugadas;
    ArrayList<Ganado> partidasJugadas;
    
    
    public UsuarioContra(String user, String pass){
        username=user;
        password=pass;
        fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        puntos=0;
        activo=true;
        this.partidasHeroes=0;
        this.partidasVillanos=0;
        parJugadas=0;
        this.partidasJugadas = new ArrayList<>();
    }
    
    public void setUser(String user){
        username=user;
    }
    public String getUser(){
        return username;
    }
    public void setPassword(String pass){
        password=pass;
    }
    public String getPassword(){
        return password;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public String getHora() {
        return hora;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getPartidasHeroes() {
        return partidasHeroes;
    }

    public int getPartidasVillanos() {
        return partidasVillanos;
    }

    public int getParJugadas() {
        return parJugadas;
    }

    public ArrayList<Ganado> getPartidasJugadas() {
        return partidasJugadas;
    }
    
    public void addPartida(Ganado partida) {
        partidasJugadas.add(partida);
        puntos += partida.puntosGanados;
        parJugadas++;
        if (partida.bandoUsado.equals("HEROES")){
            partidasHeroes++;
        }else{
            partidasVillanos++;
        }
    }
    
    public void ganar(String user) {
        this.puntos += 3;
    }
    
    public String print(String user){
        String mensaje= "Usuario: " +getUser()+ "\nPuntos: "+ getPuntos()+ 
                "\nPartidas jugadas: "+getParJugadas()+"\n Usando Villanos: "
                +getPartidasVillanos()+"\n Usando Heroes: "+getPartidasHeroes();
        return mensaje;
    }
    
    
    
}
