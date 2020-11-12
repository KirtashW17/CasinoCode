package dam.jferrando_tscalise;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static dam.jferrando_tscalise.GUI.*;

public class Ruleta {

    static GUI gui = new GUI();


    public Ruleta() {


    }

    public void createInterface(){
        gui.createInterface();
    }

    public void showInterface() { gui.setVisible();}

    static public void apostar(){
        Random rnd = new Random();
        int enJuego = 0, jugadores = 1;
        int randCPU1, randCPU2, randCPU3, randGanador;
        int[] elegidos = new int[3];

        boolean isCPU1Playing;
        boolean isCPU2Playing;
        boolean isCPU3Playing;
        boolean ganador;
                                                       //index 0=user | 1=cpu1 | 2=cpu2 | 3=cpu3 | 4=randResult
        boolean[] esRojo = new boolean[5];            // false=negro | true=rojo
        int nRojos = 0;
        int fraccionRecompensa;
        //double fraccionRecompensa;
        isCPU1Playing = isCPU2Playing = isCPU3Playing = ganador = false;


        /*REVISAMOS QUE LAS FICHAS DEL USUARIO SEAN SUFICIENTES*/
        if (gui.fichasUsuario < APUESTA || gui.apuestaUsuario == 0) {
            //NO HAY SUFICIENTES FICHAS o el usuario no ha seleccionado la apuesta
        } else {
            //SI SON SUFICIENTES,
            enJuego += APUESTA;
            gui.fichasUsuario -= APUESTA;
            elegidos[0] = apuestaUsuario;
            info1.setText("El Usuario ha apostado por el  " + gui.apuestaUsuario);
            gui.revalidate();


            randCPU1 = rnd.nextInt(36) + 1;
            if (fichasCPU1 >= APUESTA) {
                isCPU1Playing = true;
                enJuego += APUESTA;
                fichasCPU1 -= APUESTA;
                while (randCPU1 == elegidos[0]) {
                    randCPU1 = rnd.nextInt(36) + 1;
                }
                elegidos[1] = randCPU1;
                info2.setText("LA CPU1 ha apostado por el  " + randCPU1);

                randCPU2 = rnd.nextInt(36) + 1;
                if (fichasCPU2 >= APUESTA) {
                    isCPU1Playing = true;
                    enJuego += APUESTA;
                    fichasCPU2 -= APUESTA;
                    while (randCPU2 == elegidos[0] || randCPU2 == elegidos[1]) {
                        randCPU2 = rnd.nextInt(36) + 1;
                    }
                    elegidos[2] = randCPU2;
                    info3.setText("LA CPU2 ha apostado por el  " + randCPU2);
                }

                randCPU3 = rnd.nextInt(36) + 1;
                if (fichasCPU3 >= APUESTA) {
                    isCPU3Playing = true;
                    enJuego += APUESTA;
                    fichasCPU3 -= APUESTA;
                    while (randCPU3 == elegidos[0] || randCPU3 == elegidos[1] || randCPU3 == elegidos[2]) {
                        randCPU3 = rnd.nextInt(36) + 1;
                    }
                    info4.setText("LA CPU3 ha apostado por el  " + randCPU3);
                }

                jugadores = enJuego / APUESTA;
                randGanador = rnd.nextInt(36) + 1;
                info5.setText("Ha salido el " + randGanador);

                if (randGanador == apuestaUsuario) {
                    fichasUsuario += enJuego;
                    info6.setText("Ha ganado el usuario");
                } else {
                    if (isCPU1Playing) {
                        if (randGanador == randCPU1) {
                            fichasCPU1 += enJuego;
                            ganador = true;
                            info6.setText("Ha ganado la CPU1");
                        }
                    }
                    if (isCPU2Playing) {
                        if (randGanador == randCPU2) {
                            fichasCPU2 += enJuego;
                            ganador = true;
                            info6.setText("Ha ganado la CPU2");
                        }
                    }
                    if (isCPU3Playing) {
                        if (randGanador == randCPU3) {
                            fichasCPU3 += enJuego;
                            ganador = true;
                            info6.setText("Ha ganado la CPU3");
                        }
                    }

                    if (!ganador) {

                        for (int k = 0; k < (rojos.length); k++) {
                            if (rojos[k] == apuestaUsuario) {
                                esRojo[0] = true;
                                nRojos++;
                            }
                            if (rojos[k] == randCPU1) {
                                esRojo[1] = true;
                                nRojos++;
                            }
                            if (rojos[k] == randCPU2) {
                                esRojo[2] = true;
                                nRojos++;
                            }
                            if (rojos[k] == randCPU3) {
                                esRojo[3] = true;
                                nRojos++;
                            }
                            if (rojos[k] == randGanador) {
                                esRojo[4] = true;
                            }
                        }

                        if (esRojo[4] && nRojos>0){
                            fichasBanca += enJuego/2;
                            fraccionRecompensa = enJuego/2/nRojos;
                            if(esRojo[0])
                                fichasUsuario+=fraccionRecompensa;
                            if(esRojo[1])
                                fichasCPU1+=fraccionRecompensa;
                            if(esRojo[2])
                                fichasCPU2+=fraccionRecompensa;
                            if(esRojo[3])
                                fichasCPU3+=fraccionRecompensa;
                            info6.setText("Repartido (Rojo)");
                        } else if(!esRojo[4] && nRojos<4){
                            fichasBanca += enJuego/2;
                            fraccionRecompensa = enJuego/2/(4-nRojos);
                            if(!esRojo[0])
                                fichasUsuario+=fraccionRecompensa;
                            if(!esRojo[1])
                                fichasCPU1+=fraccionRecompensa;
                            if(!esRojo[2])
                                fichasCPU2+=fraccionRecompensa;
                            if(!esRojo[3])
                                fichasCPU3+=fraccionRecompensa;
                            info6.setText("Repartido (Negro)");
                        } else {
                            fichasBanca += enJuego;
                            info6.setText("Ha ganado la banca");
                        }
                    }
                }
            }
        }
    }
}

