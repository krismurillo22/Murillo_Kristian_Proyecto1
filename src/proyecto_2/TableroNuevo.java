/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_2;

import Fichas.LogicaFichas;
import Fichas.PHEROES;
import Fichas.PVILLANOS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author User
 */
public class TableroNuevo extends JPanel{
    //V. Tablero
    private boolean CasSeleccionada = false;
    private Casillas casillaSeleccionada;
    private Casillas[][] casillas;
    public boolean turnoHeroes = true;
    boolean juegoTerminado = false;
    private Image imagenFondo;
    //Pantalla Ataque
    public int ganarronda=0;
    public LogicaFichas atacanteronda;
    public LogicaFichas perdedorronda;
    //Personajes
    PHEROES Heroes = new PHEROES(-1," ");
    PVILLANOS Villanos = new PVILLANOS(-1," ");
    ArrayList<PHEROES> heroesIniciales = Heroes.getPersonajesHeroes();
    ArrayList<PVILLANOS> villanosIniciales = Villanos.getPersonjesVillanos();
    public ArrayList<PHEROES> heroesEliminados = new ArrayList<>();
    public ArrayList<PVILLANOS> villanosEliminados = new ArrayList<>();
    //PClases relacionadas
    Logica_Cuentas Cuentas;
    UsuarioContra playerHeroes, playerVillanos;
    Juego JVisual;
    Menu_Inicio MenuInicio;
    AtaqueVisual ataquevisual;
    Log_In log;
    Inicio inicio;
    Menu_Principal menuprincipal;
    
    private Casillas[][] piezasEliminadasHeroes;
    private Casillas[][] piezasEliminadasVillanos;
    public JPanel panelEliHeroes;
    private JPanel panelEliVillanos;
    private JTextArea infoArea;
    //Tablero
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        
    }
    public TableroNuevo(JTextArea infoArea, JPanel panelEliHeroes, JPanel panelEliVillanos, Logica_Cuentas sistemaUsuarios, UsuarioContra playerHeroes, UsuarioContra playerVillanos, Juego JVisual, Menu_Inicio MeniInicio, Menu_Principal menuprincipal) {
        ImageIcon imagenIcono = new ImageIcon("src/imagenes/FondoTablero.png");
        imagenFondo = imagenIcono.getImage(); 
        this.MenuInicio = MeniInicio;
        this.Cuentas = sistemaUsuarios;
        this.playerHeroes = playerHeroes;
        this.playerVillanos = playerVillanos;
        this.JVisual = JVisual;
        this.infoArea = infoArea;
        this.panelEliHeroes=panelEliHeroes;
        this.inicio = new Inicio();
        this.menuprincipal=menuprincipal;
        this.panelEliVillanos=panelEliVillanos;
        //Tablero pero sin nada
        setLayout(new GridLayout(10, 10));
        casillas = new Casillas[10][10];
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Casillas casilla = new Casillas(row, column, null); 
                casillas[row][column] = casilla;
                add(casillas[row][column].label);
            }
        }        
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Esto es para saber que casilla seleccionaron primero
                JLabel label = (JLabel) e.getSource();
                if (!CasSeleccionada) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (casillas[i][j].label == label) {
                                casillaSeleccionada = casillas[i][j];
                                if (casillaSeleccionada.personajeActual != null && 
                                        casillaSeleccionada.personajeActual.esHeroe == turnoHeroes) {//Si son del equipo y si esta llena
                                    CasSeleccionada = true;
                                    TextAreaInfoPersonaje();
                                    casillaSeleccionada.setSelected(true);
                                    break;
                                } else {
                                    casillaSeleccionada = null;
                                    CasSeleccionada = false;
                                    break;
                                }
                            }
                        }
                    }
                } else { //Ya hay seleccionada, entonces se movera
                    for (int i=0;i < 10; i++)  {
                        for (int j = 0;j<10;j++) {
                            if (casillas[i][j].label == label) {
                                if (casillas[i][j].personajeActual != null) {//Esto es por si apreta la del mismo equipo
                                    if (casillas[i][j].personajeActual.esHeroe == turnoHeroes) {
                                        SombritaFichas();
                                        TextAreaInfoPersonaje();
                                        casillaSeleccionada.setSelected(false);
                                        casillaSeleccionada = casillas[i][j];
                                        casillaSeleccionada.setSelected(true);
                                        break;
                                    }
                                }
                                // Se dio click en una casilla de equipo contrario o casilla vacia
                                if (MovValido(i, j)) {
                                    moverPersonaje(i, j);
                                } else{
                                    JOptionPane.showMessageDialog(null, "ERROR. No es valido ese movimiento.");
                                }
                            }
                            
                        }
                    } 
                }
            }
        };
        // Agregar los clicks
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                casillas[row][column].label.addMouseListener(mouseAdapter);
            }
        }
        //AreaEliminados Heroes
        panelEliHeroes.setLayout(new GridLayout(8, 5));
        piezasEliminadasHeroes = new Casillas[8][5];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 5; column++) {
                Casillas casillata = new Casillas(row, column, null); 
                piezasEliminadasHeroes[row][column] = casillata;
                panelEliHeroes.add(piezasEliminadasHeroes[row][column].label);
            }
        }
        
        //AreaEliminados Villanos
        panelEliVillanos.setLayout(new GridLayout(8, 5));
        piezasEliminadasVillanos = new Casillas[8][5];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 5; column++) {
                Casillas casillata = new Casillas(row, column, null); 
                piezasEliminadasVillanos[row][column] = casillata;
                panelEliVillanos.add(piezasEliminadasVillanos[row][column].label);
            }
        }
        colocarTierras();
        colocarBombas();
        colocarPerRango2();
        colocarPerRestantes();
        fichaAtras();
        setVisible(true);
        repaint();
    }
    
    
    public void colocarPerRango2() {
        Random random = new Random();
        // Heroes
        for (int i =0;i<heroesIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = heroesIniciales.get(i);
            int columnaAleatoria;
            int filaAleatoria;
            if (personajeActual.getRango() == 2) {
                int filas[] = new int[2];
                filas[0] = 6;
                filas[1] = 7;
                filaAleatoria = filas[random.nextInt(0,2)];
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while(casillas[filaAleatoria][columnaAleatoria].personajeActual != null);
                casillas[filaAleatoria][columnaAleatoria].setPersonaje(personajeActual);
                personajeActual.colocado = true;
            }
        }
        //Villanos
        for (int i =0;i<villanosIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = villanosIniciales.get(i);
            int columnaAleatoria;
            int filaAleatoria;
            
            if (personajeActual.getRango() == 2) {
                int filas[] = new int[2];
                filas[0] = 2;
                filas[1] = 3;
                filaAleatoria = filas[random.nextInt(0,2)];
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while(casillas[filaAleatoria][columnaAleatoria].personajeActual != null);
                casillas[filaAleatoria][columnaAleatoria].setPersonaje(personajeActual);
                personajeActual.colocado = true;
            }
        }
        
    }
    
    public void colocarTierras() {
        Random random = new Random();
        // Heroes
        for (int i =0;i<heroesIniciales.toArray().length;i++){
            LogicaFichas personajeActual= heroesIniciales.get(i);
            if (personajeActual.colocado){
                continue;
            }
            int columnaAleatoria;
            if (personajeActual.getRango() == -1) {
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while (columnaAleatoria == 0 || columnaAleatoria == 9);
                casillas[9][columnaAleatoria].setPersonaje(personajeActual);
                //No sabia como poner de otra manera esas nova alrededor de la tierra
                PHEROES nova1 = new PHEROES(0, "Nova_Blast");
                PHEROES nova2 = new PHEROES(0, "Nova_Blast");
                PHEROES nova3 = new PHEROES(0, "Nova_Blast");
                nova1.colocado = true;
                nova2.colocado = true;
                nova3.colocado = true;
                casillas[8][columnaAleatoria].setPersonaje(nova1);
                casillas[9][columnaAleatoria-1].setPersonaje(nova2);
                casillas[9][columnaAleatoria+1].setPersonaje(nova3);
                personajeActual.colocado = true;
            }
        }
        // Villanos
        for (int i =0; i<villanosIniciales.toArray().length;i++){
            LogicaFichas personajeActual = villanosIniciales.get(i);
            if (personajeActual.colocado){
                continue;
            }
            int columnaAleatoria;
            if (personajeActual.getRango() == -1) {//Lo mismo pero con villanos
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while (columnaAleatoria == 0 || columnaAleatoria == 9);
                
                casillas[0][columnaAleatoria].setPersonaje(personajeActual);
                PVILLANOS pumpkin1 = new PVILLANOS(0, "Pumpkin_Bomb");
                PVILLANOS pumpkin2 = new PVILLANOS(0, "Pumpkin_Bomb");
                PVILLANOS pumpkin3 = new PVILLANOS(0, "Pumpkin_Bomb");
                pumpkin1.colocado = true;
                pumpkin2.colocado = true;
                pumpkin3.colocado = true;
                casillas[1][columnaAleatoria].setPersonaje(pumpkin1);
                casillas[0][columnaAleatoria-1].setPersonaje(pumpkin2);
                casillas[0][columnaAleatoria+1].setPersonaje(pumpkin3);
                personajeActual.colocado = true;
            }
        }
    }
    
    public void colocarBombas() {
        // Heroes
        Random random = new Random();
        for (int i =0;i<heroesIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = heroesIniciales.get(i);
            if (personajeActual.colocado){
                continue;
            }
            int columnaAleatoria;
            int filaAleatoria;
            if (personajeActual.getRango() == 0) {//Colocar las otras bombas
                int filas[] = new int[2];
                filas[0] = 8;
                filas[1] = 9;
                filaAleatoria = filas[random.nextInt(0,2)];
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while(casillas[filaAleatoria][columnaAleatoria].personajeActual != null);
                
                casillas[filaAleatoria][columnaAleatoria].setPersonaje(personajeActual);
                personajeActual.colocado = true;
            }
        }
        //Villanos
        for (int i =0;i<villanosIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = villanosIniciales.get(i);
            if (personajeActual.colocado) continue;
            int columnaAleatoria;
            int filaAleatoria;
            if (personajeActual.getRango() == 0) {//Lo mismo pero con villanos
                int filas[] = new int[2];
                filas[0] = 0;
                filas[1] = 1;
                filaAleatoria = filas[random.nextInt(0,2)];
                do {
                    columnaAleatoria = random.nextInt(0, 10);
                } while(casillas[filaAleatoria][columnaAleatoria].personajeActual != null);
                casillas[filaAleatoria][columnaAleatoria].setPersonaje(personajeActual);
                personajeActual.colocado = true;
            }
        }
    }
    
    public void colocarPerRestantes() {
        repaint();
        boolean posicionado = false;
        // Restantes Heroes
        for (int i = 0; i<heroesIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = heroesIniciales.get(i);
            for (int fila = 6;fila<10;fila++){
                for (int columna = 0; columna < 10; columna++){
                    if (!personajeActual.colocado) {
                        posicionado = false;
                        if (casillas[fila][columna].personajeActual == null) {
                            casillas[fila][columna].setPersonaje(personajeActual);
                            personajeActual.colocado = true;
                            posicionado = true;
                            break;
                        }
                    }
                }
                if (posicionado) {
                    break;
                }
            }
        } 
        // Restantes Villanos
        for (int i = 0; i<villanosIniciales.toArray().length;i++) {
            LogicaFichas personajeActual = villanosIniciales.get(i);
            for (int fila = 0;fila<4;fila++){
                for (int columna = 0; columna < 10; columna++){
                    if (!personajeActual.colocado) {
                        posicionado = false;
                        if (casillas[fila][columna].personajeActual == null) {
                            casillas[fila][columna].setPersonaje(personajeActual);
                            personajeActual.colocado = true;
                            posicionado = true;
                            break;
                        }
                    }
                }
                if (posicionado){
                    break;
                }
            }
        } 
    }
    
    private boolean MovValido(int row, int column) {
        int filaActual = casillaSeleccionada.fila;
        int colActual = casillaSeleccionada.col;
        if (casillaSeleccionada.personajeActual.getRango() == 0 || casillaSeleccionada.personajeActual.getRango() == -1) {
            return false;//Porqur no se pueden mover 
        }
        //Rango 2, porque se puede mover varias casillas
        if (casillaSeleccionada.personajeActual.getRango() == 2) {//Buscar
            boolean ortogonal = (row == filaActual && Math.abs(column - colActual) > 0) || (column == colActual && Math.abs(row - colActual) > 0);
            boolean zonasProhi = (row >= 4 && row <= 5 && column >= 2 && column <= 3) || (row >= 4 && row <= 5 && column >= 6 && column <= 7);
            if (ortogonal && !zonasProhi) {
                if (column < colActual) { // Col actual para la izquierda
                    for (int i = colActual-1; i >= column ;i--){
                        if (casillas[filaActual][i].personajeActual != null) {
                            if (casillas[filaActual][i].personajeActual.esHeroe != turnoHeroes) {//No son del mismo equipo
                                if (i == column) {
                                    return true;
                                }else{
                                    return false;
                                }
                            } else {//son del mismo equipo
                                return false;
                            }
                        } else if ((filaActual >= 4 && filaActual <= 5) && ((i>=2 && i<=3) || (i>=6 && i<=7))){
                            return false;
                        }
                    }
                    return true;
                } else if (column > colActual) { // revisar a la derecha
                for (int i = colActual+1; i <= column;i++) {
                        if (casillas[filaActual][i].personajeActual != null) {
                            if (casillas[filaActual][i].personajeActual.esHeroe != turnoHeroes) {
                                if (i == column) {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if ((filaActual >= 4 && filaActual <= 5) && ((i>=3 && i<=4) || (i>=6 && i<=7))) {
                            return false;
                        }
                    }
                    return true;
                } else if (row < filaActual) {
                    for (int i = filaActual-1; i >= row;i--){
                        if (casillas[i][colActual].personajeActual != null) {
                            if (casillas[i][colActual].personajeActual.esHeroe != turnoHeroes) {
                                if (i == row) return true;
                                else return false;
                            } else return false;
                        } else if ((i >= 4 && i<=5) && ((colActual >= 2 && colActual <= 3) || (colActual >= 6 && colActual <= 7))) return false;
                    }
                    return true;
                } else if (row > filaActual) {
                    for (int i = filaActual+1; i <= row;i++){
                        if (casillas[i][colActual].personajeActual != null) {
                            if (casillas[i][colActual].personajeActual.esHeroe != turnoHeroes) {
                                
                                if (i == row) return true;
                                else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if ((i >= 4 && i<=5) && ((colActual >= 2 && colActual <= 3) || (colActual >= 6 && colActual <= 7))) return false;
                    }
                    return true;
                }
            } else return false;
        }
        // Los demas rangos
        if (casillaSeleccionada.personajeActual.getRango() != 2) {
            boolean ortogonalNormal = (row == filaActual && Math.abs(column - colActual) == 1) || (column == colActual && Math.abs(row - filaActual) == 1);
            boolean zonaProhibida = (row >= 4 && row <= 5 && column >= 2 && column <= 3) || (row >= 4 && row <= 5 && column >= 6 && column <= 7);
            boolean hayFicha = (casillas[row][column].personajeActual != null);
            if (hayFicha) { 
                hayFicha=(casillas[row][column].personajeActual.esHeroe == casillaSeleccionada.personajeActual.esHeroe);
            }
            // Es valido si es ortogonal, no está en una zona prohibida y no tiene otra ficha del mismo equipo
            return ortogonalNormal && !zonaProhibida && !hayFicha;
        } 
        return false;
    }
    
    public void actualizarTurno() {
        if (juegoTerminado){
            return;
        }        
        casillaSeleccionada = null;
        CasSeleccionada = false;
        turnoHeroes = !turnoHeroes;
        setVisible(false);
        String mensajeTurno;
        if (!turnoHeroes) {
            mensajeTurno = "TURNO DE: "+playerVillanos.getUser();
        } else {
           mensajeTurno = "TURNO DE: "+playerHeroes.getUser();
        }
        SombritaFichas();
        JVisual.setTurnoLabel(mensajeTurno);
        fichaAtras();
        setVisible(true);
    }
    
    private void moverPersonaje(int newRow, int newColumn) {
        //Revisar si hay ficha para pelear
        if (casillas[newRow][newColumn].personajeActual != null) {
            LogicaFichas ganador = Peleas(casillaSeleccionada.personajeActual, casillas[newRow][newColumn].personajeActual);
            //Esto lo hago para mandar la informacion a AtaqueVisual
            if (ganador == casillaSeleccionada.personajeActual) {
                ataquevisual=new AtaqueVisual(ganador,casillas[newRow][newColumn].personajeActual,1);
            } else if (ganador == null) {
                ataquevisual=new AtaqueVisual(casillaSeleccionada.personajeActual,casillas[newRow][newColumn].personajeActual,-1);
            } else {
                ataquevisual=new AtaqueVisual(casillaSeleccionada.personajeActual,ganador,0);
            }
            ataquevisual.setVisible(true);
            
            if (ganador == null) { // Son del mismo raango
                casillaSeleccionada.setPersonaje(null);
                casillas[newRow][newColumn].setPersonaje(null);
            } else if (casillaSeleccionada.personajeActual == ganador) {
                // Se mueve el ganador a la casilla de la pieza derrotada
                casillaSeleccionada.setPersonaje(null);
                casillas[newRow][newColumn].setPersonaje(ganador);
            } else {
                casillaSeleccionada.setPersonaje(null);
            }
            Gane();
            actualizarTurno();
            return;
        }
        // No habia un personaje en esa casilla
        LogicaFichas personaje = casillaSeleccionada.personajeActual;
        casillaSeleccionada.setPersonaje(null);
        SombritaFichas();
        casillas[newRow][newColumn].setPersonaje(personaje);
        actualizarTurno();
    }
    
    public LogicaFichas Peleas(LogicaFichas atacante, LogicaFichas defensor) {
        // Los personajes rango 3 vencen a NovaBlast y Pumpkin no se
        if ((atacante.getRango()==3) && (defensor.getRango()==0)) {
            if (defensor.esHeroe){
                heroesEliminados.add((PHEROES) defensor);
                HeroesEliminados();
            }else {
                villanosEliminados.add((PVILLANOS) defensor);
                VillanosEliminados();
            }
            return atacante;
            // Rango 1 puede vencer a rango 10 o a tierra
        } else if ((atacante.getRango() == 1) && (defensor.getRango() == 10 || defensor.getRango() == -1)) {
            if (defensor.esHeroe){
                heroesEliminados.add((PHEROES) defensor);
                HeroesEliminados();
            }else{
                villanosEliminados.add((PVILLANOS) defensor);
                VillanosEliminados();
            }
            return atacante;
            //Bombas contra otros rangos
        } else if (defensor.getRango() == 0 && atacante.getRango() != 3) {
            if (atacante.esHeroe){
                heroesEliminados.add((PHEROES) atacante);
                HeroesEliminados();
            }else{
                villanosEliminados.add((PVILLANOS) atacante);
                VillanosEliminados();
            }
            return defensor;
        } else if (atacante.getRango() > defensor.getRango()) {
            if (defensor.esHeroe) {
                heroesEliminados.add((PHEROES) defensor);
                HeroesEliminados();
            }else {
                villanosEliminados.add((PVILLANOS) defensor);
                VillanosEliminados();
            }
            return atacante;
        } else if (atacante.getRango() < defensor.getRango()) {
            if (atacante.esHeroe){
                heroesEliminados.add((PHEROES) atacante);
                HeroesEliminados();
            }else{
                villanosEliminados.add((PVILLANOS) atacante);
                VillanosEliminados();
            }
            return defensor;
        } else {//Empate
            if (atacante.esHeroe) {
                heroesEliminados.add((PHEROES) atacante);
                HeroesEliminados();
                villanosEliminados.add((PVILLANOS) defensor);
                VillanosEliminados();
            } else {
                villanosEliminados.add((PVILLANOS) atacante);
                HeroesEliminados();
                heroesEliminados.add((PHEROES) defensor);
                VillanosEliminados();
            }
            return null;
        }
    }
    
    private boolean tieneMovValidos(boolean bandoHeroes) {
        //Buscando si tiene fichas que se puedan mover
        for (int contar = 0;contar<10;contar++){
            for (int i = 0;i<10;i++) {
                Casillas casillaActual = casillas[contar][i];
                if (casillaActual.personajeActual == null) {
                    continue;
                }
                if (casillaActual.personajeActual.esHeroe == bandoHeroes && casillaActual.personajeActual.getRango() > 0){
                    return true;
                }
            }
        }
        return false;
    }
    
    private void Gane() {
        String mensajeLog = "";
        Date fecha = new Date();
        // Ambos sin movimientos
        if (!tieneMovValidos(turnoHeroes) && !tieneMovValidos(!turnoHeroes)) {
            mensajeLog = playerHeroes.getUser() + " usando HEROES ha empatado con " + playerVillanos.getUser() + " porque ambos se quedaron sin movimientos validos.";
            Ganado partidaHeroes = new Ganado(playerVillanos, false, "HEROES", 1.5, fecha);
            Ganado partidaVillanos = new Ganado(playerHeroes, false, "VILLANOS", 1.5, fecha);
            playerHeroes.addPartida(partidaHeroes);
            playerVillanos.addPartida(partidaVillanos);
            juegoTerminado = true;
            
        } else if (!tieneMovValidos(turnoHeroes)) { // Solo uno se quedo sin movimientos
            UsuarioContra ganador = (!turnoHeroes) ?playerHeroes:playerVillanos;
            UsuarioContra perdedor = (!turnoHeroes) ?playerVillanos:playerHeroes;
            String bandoGanador = (!turnoHeroes) ?"HEROES":"VILLANOS";
            String bandoPerdedor = (!turnoHeroes) ?"VILLANOS":"HEROES";
            Ganado partidaGanador = new Ganado(perdedor, true, bandoGanador, 3, fecha);
            Ganado partidaPerdedor = new Ganado(ganador, false, bandoPerdedor, 0, fecha);
            ganador.addPartida(partidaGanador);
            perdedor.addPartida(partidaPerdedor);
            mensajeLog = perdedor.getUser() + " usando los " + bandoPerdedor + " ha perdido por no tener movimientos validos disponibles ante " + 
                    ganador.getUser() + " - " + fecha;
            juegoTerminado = true;
            
        }  else if(!tieneMovValidos(!turnoHeroes)) { // Verifica lo mismo pero para el otro equipo
            UsuarioContra ganador = (turnoHeroes) ?playerHeroes:playerVillanos;
            UsuarioContra perdedor = (turnoHeroes) ?playerVillanos:playerHeroes;
            String bandoGanador = (turnoHeroes) ?"HEROES":"VILLANOS";
            String bandoPerdedor = (turnoHeroes) ?"VILLANOS":"HEROES";
            Ganado partidaGanador = new Ganado(perdedor, true, bandoGanador, 3, fecha);
            Ganado partidaPerdedor = new Ganado(ganador, false, bandoPerdedor, 0, fecha);
            ganador.addPartida(partidaGanador);
            perdedor.addPartida(partidaPerdedor);
            mensajeLog = perdedor.getUser() + " usando los " + bandoPerdedor + " ha perdido por no tener movimientos validos disponibles ante " + 
                    ganador.getUser() + " - " + fecha;
            juegoTerminado = true;
            
        } else {//Se comen la tierra
            for (int i = 0;i<heroesEliminados.toArray().length;i++) {
                LogicaFichas personajeActual = heroesEliminados.get(i);
                if (personajeActual.getRango()==-1 && personajeActual.esHeroe) {
                    juegoTerminado = true;
                    mensajeLog = playerVillanos.getUser() + " usando los VILLANOS ha CAPTURADO la TIERRA! Venciendo a " + playerHeroes.getUser() + " - " + fecha;
                    Ganado partidaHeroes = new Ganado(playerVillanos, false, "HEROES", 0, fecha);
                    Ganado partidaVillanos = new Ganado(playerHeroes, true, "VILLANOS", 3, fecha);
                    playerHeroes.addPartida(partidaHeroes);
                    playerVillanos.addPartida(partidaVillanos);
                }
            }
            
            for (int i =0;i<villanosEliminados.toArray().length;i++) {
                LogicaFichas personajeActual = villanosEliminados.get(i);
                if (personajeActual.getRango()==-1 && !personajeActual.esHeroe) {
                    juegoTerminado = true;
                    mensajeLog = playerHeroes.getUser() + " usando los HEROES ha SALVADO la TIERRA! Venciendo a " + playerVillanos.getUser() + " - " + new Date().toString();
                    Ganado partidaHeroes = new Ganado(playerVillanos, true, "HEROES", 3, fecha);
                    Ganado partidaVillanos = new Ganado(playerHeroes, false, "VILLANOS", 0, fecha);
                    playerHeroes.addPartida(partidaHeroes);
                    playerVillanos.addPartida(partidaVillanos);
                }
            }
        }
        
        if (juegoTerminado)
        {
            JOptionPane.showMessageDialog(null, mensajeLog);
            JVisual.dispose();
            ataquevisual.dispose();
            menuprincipal.setVisible(true);
        }   
    }
    
    //Estetica del tablero
    private void fichaAtras() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j<10;j++) {
                if (casillas[i][j].personajeActual != null) {
                    casillas[i][j].esconderCasilla(turnoHeroes != casillas[i][j].personajeActual.esHeroe);
                } 
            }
        }
    }
    
    public void SombritaFichas() {
        for (int i = 0; i<10;i++) {
            for (int j = 0; j<10;j++){
                casillas[i][j].label.setOpaque(false);
                casillas[i][j].label.repaint();

            }
        }
    }
    
    public void TextAreaInfoPersonaje() {
        LogicaFichas ficha = casillaSeleccionada.personajeActual;
        String mensaje = "Ficha: " + ficha.nombre + "\n";
        mensaje += "Bando: " + ((ficha.esHeroe) ?"Heroes\n":"Villanos\n");
        if (ficha.getRango() == 0) {
            mensaje += "-> Las bombas no se pueden mover."
                    +"\n-> Solo rango 3 las pueden eliminar.";
        } else if (ficha.getRango() == -1) {
            mensaje += "-> La Tierra no se puede mover.\n"
                    +"-> Si la eliminan, pierde el bando.";
        } else if (ficha.getRango() == 1) {
            mensaje += "Rango: 1 \n -> Todos los rangos la pueden eliminar."+
                    "\n-> Si ataca rango 10, lo puede eliminar.";
        } else if (ficha.getRango() == 10) {
            mensaje += "Rango: 10 \n -> Puede eliminar a todas, \nmenos bombas.";
        }else {
            mensaje += "Rango: " + ficha.getRango() + "\n" +
                    "-> Gana si contricante es de menor rango. \n-> Si ambas son de rango igual, \nambas pierden.";
        }
        infoArea.setText(mensaje);
    }
    
    public void HeroesEliminados(){
        //Panel Piezas Eliminadas Heroes
        boolean posicionado = false;
        if (!heroesEliminados.isEmpty()) {
            for (LogicaFichas personajeActual : heroesEliminados) {
                System.out.println("Colocando personaje: " + personajeActual.getNombre());
                for (int fila = 0;fila<8;fila++){
                    for (int columna = 0; columna < 5; columna++){
                        if (!personajeActual.colocadoEliminado) {
                            posicionado = false;
                            if (piezasEliminadasHeroes[fila][columna].personajeActual == null) {
                                piezasEliminadasHeroes[fila][columna].setPersonajeEliminado(personajeActual);
                                personajeActual.colocadoEliminado = true;
                                posicionado = true;
                                break;
                            }
                        }
                    }
                    if (posicionado) {
                        break;
                    }
                }
            }
        }
        panelEliHeroes.revalidate();
        panelEliHeroes.repaint();
    }
    
    public void VillanosEliminados(){
        //Panel Piezas Eliminadas Villanos
        boolean posicionado = false;
        if (!villanosEliminados.isEmpty()) {
            for (LogicaFichas personajeActual : villanosEliminados) {
                System.out.println("Colocando personaje: " + personajeActual.getNombre());
                for (int fila = 0;fila<8;fila++){
                    for (int columna = 0; columna < 5; columna++){
                        if (!personajeActual.colocadoEliminado) {
                            posicionado = false;
                            if (piezasEliminadasVillanos[fila][columna].personajeActual == null) {
                                piezasEliminadasVillanos[fila][columna].setPersonajeEliminado(personajeActual);
                                personajeActual.colocadoEliminado = true;
                                posicionado = true;
                                break;
                            }
                        }
                    }
                    if (posicionado) {
                        break;
                    }
                }
            }
        }
        panelEliVillanos.revalidate();
        panelEliVillanos.repaint();
    }
    
    
    //Lo hago aqui por las rondas
    public void rendirse() {
        Date fecha = new Date();
        UsuarioContra ganador, perdedor;
        String bandoGanador, bandoPerdedor;
        if (turnoHeroes) {
            ganador = playerVillanos;
            perdedor = playerHeroes;
            bandoGanador = "VILLANOS";
            bandoPerdedor = "HEROES";
        } else {
            ganador = playerHeroes;
            perdedor = playerVillanos;
            bandoGanador = "HEROES";
            bandoPerdedor = "VILLANOS";
        }
        String mensaje = ganador.getUser() + " usando " + bandoGanador + " ha ganado ya que " + perdedor.getUser() + " usando " + bandoPerdedor +
                " se ha retirado del juego. - " + fecha;
        Ganado partidaGanador = new Ganado(perdedor, true, bandoGanador, 3, fecha);
        Ganado partidaPerdedor = new Ganado(ganador, false, bandoPerdedor, 0, fecha);
        ganador.addPartida(partidaGanador);
        perdedor.addPartida(partidaPerdedor);
        juegoTerminado = true;
        JOptionPane.showMessageDialog(null, mensaje);
    }
    
}

