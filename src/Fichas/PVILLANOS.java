/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fichas;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class PVILLANOS extends LogicaFichas{

    ArrayList<PVILLANOS> personajesV = new ArrayList<>();
    
    public PVILLANOS(int rango, String nombre) {
        super(rango, "VILLANOS" , nombre);
        esHeroe=false;
        try {
           Image Escondido = resizeImage(ImageIO.read(new File("src/ImgFichasVillanos/Atras_Villanos.png")), 75, 75);
           fichaatras = new ImageIcon(Escondido);
        } catch (Exception e) {
            fichaatras = null;
        }
        
        UrlFichas();
        FichasEliminadas();
    }
    
    public void UrlFichas() {
        String url;
        url = "src/ImgFichasVillanos/" + nombre + ".png";
        
        try {
            Image personaje = resizeImage(ImageIO.read(new File(url)), 75, 75);
            fichadelante = new ImageIcon(personaje);
        } catch (Exception e) {
            fichadelante = null;
        }
    }
    
    public void FichasEliminadas() {
        String url;
        url = "src/ImgFichasVillanos/" + nombre + ".png";
        
        try {
            Image personaje = resizeImage(ImageIO.read(new File(url)), 54, 54);
            fichadelanteEliminadas = new ImageIcon(personaje);
        } catch (Exception e) {
            fichadelanteEliminadas = null;
        }
    }
    
    private Image resizeImage(Image img, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    public ArrayList<PVILLANOS> getPersonjesVillanos(){
        personajesV.add(new PVILLANOS(10,"Dr_Doom"));
        personajesV.add(new PVILLANOS(9,"Galactus"));
        personajesV.add(new PVILLANOS(8,"Kingpin"));
        personajesV.add(new PVILLANOS(8,"Magneto"));
        personajesV.add(new PVILLANOS(7,"Apocalypse"));
        personajesV.add(new PVILLANOS(7,"Green_Goblin"));
        personajesV.add(new PVILLANOS(7,"Venom"));
        personajesV.add(new PVILLANOS(6,"Bullseye"));
        personajesV.add(new PVILLANOS(6,"Omega_Red"));
        personajesV.add(new PVILLANOS(6,"Onslaught"));
        personajesV.add(new PVILLANOS(6,"Red_Skull"));
        personajesV.add(new PVILLANOS(5,"Mystique"));
        personajesV.add(new PVILLANOS(5,"Mysterio"));
        personajesV.add(new PVILLANOS(5,"Doctor_Octopus"));
        personajesV.add(new PVILLANOS(5,"Deadpool"));
        personajesV.add(new PVILLANOS(4,"Abomination"));
        personajesV.add(new PVILLANOS(4,"Thanos"));
        personajesV.add(new PVILLANOS(4,"Black_Cat"));
        personajesV.add(new PVILLANOS(4,"Sabretooth"));
        personajesV.add(new PVILLANOS(3,"Juggernaut"));
        personajesV.add(new PVILLANOS(3,"Rhino"));
        personajesV.add(new PVILLANOS(3,"Carnage"));
        personajesV.add(new PVILLANOS(3,"Mole_Man"));
        personajesV.add(new PVILLANOS(3,"Lizard"));
        personajesV.add(new PVILLANOS(2,"Mr_Sinister"));
        personajesV.add(new PVILLANOS(2,"Sentinel_1"));
        personajesV.add(new PVILLANOS(2,"Sentinel_2"));
        personajesV.add(new PVILLANOS(2,"Ultron"));
        personajesV.add(new PVILLANOS(2,"Sandman"));
        personajesV.add(new PVILLANOS(2,"Leader"));
        personajesV.add(new PVILLANOS(2,"Viper"));
        personajesV.add(new PVILLANOS(2,"Electro"));
        personajesV.add(new PVILLANOS(1,"Black_Widow_V"));
        personajesV.add(new PVILLANOS(0,"Pumpkin_Bomb"));
        personajesV.add(new PVILLANOS(0,"Pumpkin_Bomb"));
        personajesV.add(new PVILLANOS(0,"Pumpkin_Bomb"));
        personajesV.add(new PVILLANOS(-1,"Tierra_V"));
        return personajesV;
    }
    
    
    
}
