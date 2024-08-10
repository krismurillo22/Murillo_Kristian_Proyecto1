/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2;

import Fichas.LogicaFichas;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class Casillas {
    JLabel label;
    LogicaFichas personajeActual;
    int fila;
    int col;
    
    public Casillas(int fila, int col, LogicaFichas personajeActual) {
        this.label = new JLabel();
        this.fila = fila;
        this.col = col;
        this.personajeActual = personajeActual; 
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public int getFila() {
        return fila;
    }

    public int getCol() {
        return col;
    }
    
    public void setPersonaje(LogicaFichas personaje) {
        personajeActual = personaje;
        if (personaje == null) {
            label.setText("");
            label.setIcon(null);
        }
        else {
            if (personajeActual.fichadelante != null) {
                label.setIcon(personajeActual.fichadelante);
            }
            else
                label.setText(personajeActual.nombre);
        }
    }
    
    public void setPersonajeEliminado(LogicaFichas personaje) {
        personajeActual = personaje;
        if (personaje == null) {
            label.setText("");
            label.setIcon(null);
        }
        else {
            if (personajeActual.fichadelanteEliminadas != null) {
                label.setIcon(personajeActual.fichadelanteEliminadas);
            }
            else
                label.setText(personajeActual.nombre);
        }
    }
    
    public void esconderCasilla(boolean esconder) {
        if (esconder) {
            if (personajeActual.fichaatras != null) {
                label.setIcon(personajeActual.fichaatras);
                label.repaint();
            } else {
                label.setIcon(null);
                label.setText("null");
            }
        } else {
            if (personajeActual.fichadelante != null) {
                label.setIcon(personajeActual.fichadelante);
            }
            else
                label.setText(personajeActual.nombre);
        }
    }
    
    public void setSelected(boolean selected) {
        if (selected) {
            label.setBackground(Color.pink);
            label.setOpaque(true);
            label.repaint();
        } else
            label.setOpaque(false);
    }
}
