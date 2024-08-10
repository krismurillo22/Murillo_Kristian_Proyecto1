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
public class PHEROES extends LogicaFichas{
    
    public ArrayList<PHEROES> personajesH = new ArrayList<>();
    
    public PHEROES(int rango, String nombre) {
        super(rango, "HEROES" , nombre);
        esHeroe=true;
        
        try {
           Image Escondido = resizeImage(ImageIO.read(new File("src/ImgFichasHeroes/Atras_Heroes.png")), 75, 75);
           fichaatras = new ImageIcon(Escondido);
        } catch (Exception e) {
            fichaatras = null;
        }
        
        UrlFichas();
        FichasEliminadas();
    }
    
    public void UrlFichas() {
        String url = "src/ImgFichasHeroes/" + nombre + ".png";
        try {
            Image personaje = resizeImage(ImageIO.read(new File(url)), 75, 75);
            fichadelante = new ImageIcon(personaje);
        } catch (Exception e) {
            fichadelante = null;
        }
    }
    
    public void FichasEliminadas() {
        String url = "src/ImgFichasHeroes/" + nombre + ".png";
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
    
    public ArrayList<PHEROES> getPersonajesHeroes(){
        personajesH.add(new PHEROES(10,"Mr_Fantastic"));
        personajesH.add(new PHEROES(9,"Captain_America"));
        personajesH.add(new PHEROES(8,"Professor_X"));
        personajesH.add(new PHEROES(8,"Nick_Fury"));
        personajesH.add(new PHEROES(7,"Spider_Man"));
        personajesH.add(new PHEROES(7,"Wolverine"));
        personajesH.add(new PHEROES(7,"Namor"));
        personajesH.add(new PHEROES(6,"Daredevil"));
        personajesH.add(new PHEROES(6,"Silver_Surfer"));
        personajesH.add(new PHEROES(6,"Hulk"));
        personajesH.add(new PHEROES(6,"Iron_Man"));
        personajesH.add(new PHEROES(5,"Human_Torch"));
        personajesH.add(new PHEROES(5,"Cyclops"));
        personajesH.add(new PHEROES(5,"Invisible_Woman"));
        personajesH.add(new PHEROES(5,"Thor"));
        personajesH.add(new PHEROES(4,"Ghost_Rider"));
        personajesH.add(new PHEROES(4,"Punisher"));
        personajesH.add(new PHEROES(4,"Blade"));
        personajesH.add(new PHEROES(4,"Thing"));
        personajesH.add(new PHEROES(3,"Emma_Frost"));
        personajesH.add(new PHEROES(3,"She_Hulk"));
        personajesH.add(new PHEROES(3,"Giant_Man"));
        personajesH.add(new PHEROES(3,"Beast"));
        personajesH.add(new PHEROES(3,"Colossus"));
        personajesH.add(new PHEROES(2,"Gambit"));
        personajesH.add(new PHEROES(2,"Spider_Girl"));
        personajesH.add(new PHEROES(2,"Ice_Man"));
        personajesH.add(new PHEROES(2,"Storm"));
        personajesH.add(new PHEROES(2,"Phoenix"));
        personajesH.add(new PHEROES(2,"Dr_Strange"));
        personajesH.add(new PHEROES(2,"Elektra"));
        personajesH.add(new PHEROES(2,"Nightcrawler"));
        personajesH.add(new PHEROES(1,"Black_Widow"));
        personajesH.add(new PHEROES(0,"Nova_Blast"));
        personajesH.add(new PHEROES(0,"Nova_Blast"));
        personajesH.add(new PHEROES(0,"Nova_Blast"));
        personajesH.add(new PHEROES(-1,"Tierra"));
        return personajesH;
    }
}
