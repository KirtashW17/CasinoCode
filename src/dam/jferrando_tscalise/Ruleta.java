package dam.jferrando_tscalise;

import java.awt.*;
import java.util.Random;

public class Ruleta {



    static GUI gui = new GUI();  //Importa la GUI y genera la interfaz, pero no la imprime aún.



    public void showInterface() {
        gui.setVisible(); //La interfaz se vuelve visible
    }

    static public void apostar() {

        final int APUESTA=10;

        //Variables de la apuesta
        int enJuego = 0, jugadores = 1;
        int randCPU1, randCPU2, randCPU3, randGanador;
        int[] elegidos = new int[3];

        Random rnd = new Random();


        boolean isCPU1Playing; //cpu1.playing
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
        if (!gui.jugador.puedeJugar(APUESTA)) {
            //NO HAY SUFICIENTES FICHAS o el usuario no ha seleccionado la apuesta
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_error.setText("No tienes suficientes fichas como para seguir apostando.");
            gui.label_error.setFont(new Font("Courier", Font.BOLD, 38));
            gui.label_error.setForeground(Color.RED);
            gui.label_error.setAlignment(Label.CENTER);
            gui.label_error.setVisible(true);

        } else if(gui.jugador.getApuesta() == 0){
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_error.setText("No has seleccionado ninguna casilla.");
            gui.label_error.setFont(new Font("Courier", Font.BOLD, 38));
            gui.label_error.setForeground(Color.RED);
            gui.label_error.setAlignment(Label.CENTER);
            gui.label_error.setVisible(true);
        } else if(!gui.canSomeCpuPlay()){
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_error.setText("Solo quedas tú en la mesa! Has desvalijado a todos.");
            gui.label_error.setFont(new Font("Courier", Font.BOLD, 38));
            gui.label_error.setForeground(Color.BLUE);
            gui.label_error.setAlignment(Label.CENTER);
            gui.label_error.setVisible(true);

        } else {
            if(gui.label_error.isVisible()){
                gui.label_error.setVisible(false);
                gui.pan_informe1.setVisible(true);
                gui.pan_informe2.setVisible(true);
            }
            //SI SON SUFICIENTES,
            enJuego += APUESTA;
            gui.jugador.sumarFichas(-APUESTA);
            elegidos[0] = gui.jugador.getApuesta();
            gui.info1.setText("<html>&nbsp;Has apostado por el: " + gui.jugador.getApuesta() + "</html>");

            randCPU1 = rnd.nextInt(36) + 1;
            if (gui.cpu1.puedeJugar(APUESTA)) {
                gui.cpu1.setJugando();
                enJuego += APUESTA;
                gui.cpu1.setFichas(gui.cpu1.getFichas() - APUESTA);
                while (randCPU1 == elegidos[0]) {
                    randCPU1 = rnd.nextInt(36) + 1;
                }
                elegidos[1] = randCPU1;
                gui.info2.setText("<html>&nbsp;CPU1 ha apostado por el: " + randCPU1 + "</html>");
                gui.info2.setFont(new Font("courier", Font.BOLD, 22));
                gui.info2.setForeground(Color.BLACK);
            }
                randCPU2 = rnd.nextInt(36) + 1;
                if (gui.cpu2.puedeJugar(APUESTA)) {
                    gui.cpu2.setJugando();
                    enJuego += APUESTA;
                    gui.cpu2.setFichas(gui.cpu2.getFichas() - APUESTA);
                    while (randCPU2 == elegidos[0] || randCPU2 == elegidos[1]) {
                        randCPU2 = rnd.nextInt(36) + 1;
                    }
                    elegidos[2] = randCPU2;
                    gui.info3.setText("<html>&nbsp;CPU2 ha apostado por el: " + randCPU2 + "</html>");
                    gui.info3.setFont(new Font("courier", Font.BOLD, 22));
                    gui.info3.setForeground(Color.BLACK);
                }

                randCPU3 = rnd.nextInt(36) + 1;
                if (gui.cpu3.puedeJugar(APUESTA)) {
                    gui.cpu3.setJugando();
                    enJuego += APUESTA;
                    gui.cpu3.setFichas(gui.cpu3.getFichas() - APUESTA);
                    while (randCPU3 == elegidos[0] || randCPU3 == elegidos[1] || randCPU3 == elegidos[2]) {
                        randCPU3 = rnd.nextInt(36) + 1;
                    }
                    gui.info4.setText("<html>&nbsp;CPU3 ha apostado por el: " + randCPU3 + "</html>");
                    gui.info4.setFont(new Font("courier", Font.BOLD, 22));
                    gui.info4.setForeground(Color.BLACK);
                }

                jugadores = enJuego / APUESTA;
                randGanador = rnd.nextInt(36) + 1;
            gui.info5.setText("<html>&nbsp;Ha salido el: " + randGanador + "</html>");


                if (randGanador == gui.jugador.getApuesta()) {
                    gui.jugador.setFichas(gui.jugador.getFichas() + enJuego);
                    gui.info6.setText("<html>Has ganado!</html>");
                } else {
                    if (gui.cpu1.getJugando()) {
                        if (randGanador == randCPU1) {
                            gui.cpu1.setFichas(gui.cpu1.getFichas() + enJuego);
                            ganador = true;
                            gui.info6.setText("<html>Ha ganado CPU1<html>");
                        }
                    }
                    if (gui.cpu2.getJugando()) {
                        if (randGanador == randCPU2) {
                            gui.cpu2.setFichas(gui.cpu2.getFichas() + enJuego);
                            ganador = true;
                            gui.info6.setText("<html>Ha ganado CPU2<html>");
                        }
                    }
                    if (gui.cpu3.getJugando()) {
                        if (randGanador == randCPU3) {
                            gui.cpu3.setFichas(gui.cpu3.getFichas() + enJuego);
                            ganador = true;
                            gui.info6.setText("<html>Ha ganado CPU3<html>");
                        }
                    }


                        for (int k = 0; k < (gui.rojos.length); k++) {
                            if (gui.rojos[k] == gui.jugador.getApuesta() && gui.jugador.getJugando()) {
                                gui.jugador.setEsRojo(true);
                                nRojos++;
                                gui.info1.setText("<html>&nbsp;Has apostado por el: <span color='red'>" + gui.jugador.getApuesta() + "</span></html>");
                            }
                            if (gui.rojos[k] == randCPU1 && gui.cpu1.getJugando()) {
                                gui.cpu1.setEsRojo(true);
                                nRojos++;
                                gui.info2.setText("<html>&nbsp;CPU1 ha apostado por el: <span color='red'>" + randCPU1 + "</span></html>");

                            }
                            if (gui.rojos[k] == randCPU2 && gui.cpu2.getJugando()) {
                                gui.cpu2.setEsRojo(true);
                                nRojos++;
                                gui.info3.setText("<html>&nbsp;CPU2 ha apostado por el: <span color='red'>" + randCPU2 + "</span></html>");
                            }
                            if (gui.rojos[k] == randCPU3 && gui.cpu3.getJugando()) {
                                gui.cpu3.setEsRojo(true);
                                nRojos++;
                                gui.info4.setText("<html>&nbsp;CPU3 ha apostado por el: <span color='red'>" + randCPU3 + "</span></html>");

                            }
                            if (gui.rojos[k] == randGanador) {
                                gui.banca.setEsRojo(true);
                                gui.info5.setText("<html>&nbsp;Ha salido el: <span color='red'>" + randGanador + "</span></html>");

                            }
                        }

                    if (!ganador) {


                        if (gui.banca.getEsRojo() && nRojos > 0) {
                            gui.banca.sumarFichas(enJuego / 2);
                            fraccionRecompensa = enJuego / 2 / nRojos;
                            if (gui.jugador.getEsRojo() && gui.jugador.getJugando())//Según lo hemos planteado el jugador siempre tiene que estar jugando por lo cual es reduntante
                                gui.jugador.sumarFichas(fraccionRecompensa);
                            if (gui.cpu1.getEsRojo() && gui.cpu1.getJugando())
                                gui.cpu1.sumarFichas(fraccionRecompensa);
                            if (gui.cpu2.getEsRojo() && gui.cpu2.getJugando())
                                gui.cpu2.sumarFichas(fraccionRecompensa);
                            if (gui.cpu3.getEsRojo() && gui.cpu3.getJugando())
                                gui.cpu3.sumarFichas(fraccionRecompensa);
                            gui.info6.setText("<html>Repartido <span color='red'>(Rojo)</span></html>");

                        } else if (!gui.banca.getEsRojo() && nRojos < 4) {
                            gui.banca.setFichas(gui.banca.getFichas() + enJuego/2);
                            fraccionRecompensa = enJuego / 2 / (4 - nRojos);
                            if (!gui.jugador.getEsRojo() && gui.jugador.getJugando())
                                gui.jugador.sumarFichas(fraccionRecompensa);
                            if (!gui.cpu1.getEsRojo() && gui.cpu1.getJugando())
                                gui.cpu1.sumarFichas(fraccionRecompensa);
                            if (!gui.cpu2.getEsRojo() && gui.cpu2.getJugando())
                                gui.cpu2.sumarFichas(fraccionRecompensa);
                            if (!gui.cpu3.getEsRojo() && gui.cpu3.getJugando())
                                gui.cpu3.sumarFichas(fraccionRecompensa);
                            gui.info6.setText("<html>Repartido (Negro)</html>");
                        } else {
                            gui.banca.setFichas(gui.banca.getFichas() + enJuego);
                            gui.info6.setText("<html>Ha ganado la banca</html>");
                        }
                    }
                }
            }
        }
    }

