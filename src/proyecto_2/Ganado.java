/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author User
 */
public class Ganado {
    UsuarioContra contrincante;
    boolean victoria;
    String bandoUsado;
    double puntosGanados;
    String fecha;
    String win;
    
    public Ganado(UsuarioContra contrincante, boolean victoria, String bandoUsado, double puntosGanados, String fechaObj) {
        this.contrincante = contrincante;
        this.victoria = victoria;
        this.bandoUsado = bandoUsado;
        this.puntosGanados = puntosGanados;
        this.fecha = fechaObj;
    
    }

    public String toString() {
        if (victoria) {
            win= "GANASTE" ;
        }else {
            win= "PERDISTE";
        }
        return "Contrincante: " + contrincante.getUser() + 
                "\nResultados: " + win + "\nBando usado: " + bandoUsado + 
                "\nFecha:" + fecha+
                "\n***********************\n";
    }
    
    
}
