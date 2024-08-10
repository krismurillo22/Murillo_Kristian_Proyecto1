/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fichas;

import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public abstract class LogicaFichas {
    public ImageIcon fichaatras;
    public ImageIcon fichadelante;
    public ImageIcon fichadelanteEliminadas;
    protected int rango;
    protected String equipo;
    public String nombre;
    public boolean esHeroe;
    public boolean colocado = false;
    public boolean colocadoEliminado=false;
    
    public LogicaFichas(int rango, String equipo, String nombre) {
        this.rango = rango;
        this.equipo = equipo;
        this.nombre = nombre;
    }

    public int getRango() {
        return rango;
    }

    public String getEquipo() {
        return equipo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public abstract void UrlFichas();
    
    public abstract void FichasEliminadas();
}
