package dam.jferrando_tscalise;

import java.awt.*;
import java.util.Random;

import static dam.jferrando_tscalise.GUI.*;

public class Apuesta {


    public Apuesta() {

        int enJuego = 0, jugadores = 1;
        int randCPU1, randCPU2, randCPU3, randGanador;
        int[] elegidos = new int[3];

        boolean isCPU1Playing;
        boolean isCPU2Playing;
        boolean isCPU3Playing;
        boolean ganador;

        isCPU1Playing = isCPU2Playing = isCPU3Playing = ganador = false;

        Random rnd = new Random();

        /*REVISAMOS QUE LAS FICHAS DEL USUARIO SEAN SUFICIENTES*/
        if (fichasUsuario < APUESTA || apuestaUsuario == 0) {
            //NO HAY SUFICIENTES FICHAS o el usuario no ha seleccionado la apuesta
        } else {
            //SI SON SUFICIENTES,
            enJuego += APUESTA;
            fichasUsuario -= APUESTA;
            elegidos[0] = apuestaUsuario;
            info1.setText("El Usuario ha apostado por el  " + apuestaUsuario);


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

            }

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
                        ganador=true;
                        info6.setText("Ha ganado la CPU1");
                    }
                } if (isCPU2Playing) {
                    if (randGanador == randCPU2) {
                        fichasCPU2 += enJuego;
                        ganador=true;
                        info6.setText("Ha ganado la CPU2");
                    }
                } if (isCPU3Playing) {
                    if (randGanador == randCPU3) {
                        fichasCPU3 += enJuego;
                        ganador=true;
                        info6.setText("Ha ganado la CPU3");
                    }
                }
                if (!ganador){
                    fichasBanca += enJuego;
                    info6.setText("Ha ganado la banca");

                }
            }

        }
    }


}
