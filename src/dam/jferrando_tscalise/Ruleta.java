package dam.jferrando_tscalise;

import java.util.Random;

public class Ruleta {

    static GUI gui = new GUI();  //Importa la GUI y genera la interfaz, pero no la imprime aún.

    //Creamos una función que nos permita mostrar la interfaz.
    public void showInterface() {
        gui.dialog.setVisible(true); //La interfaz se vuelve visible
    }

    //Creamos una función que contenga toda la lógica detrás de cada apuesta
    static public void apostar() {

        //Creamos una nueva instancia de Random, lo usaremos para generar apuestas aleatorias:
        Random rnd = new Random();

        //Se apostarán 10 fichas a la vez (CONSTANTE)
        final int FICHASAPUESTA = GUI.FICHASAPUESTA;

        //Variables de la apuesta
        int fichasEnJuego = 0;                          //Almacenaremos el número de fichas en juego
        int nRojos = 0;                                 //Almacenaremos la cantidad de apuestas para el rojo.
        int fraccionRecompensa;                         //En caso de repartición, almacenaremos la fracción a repartir.
        boolean ganador = false;                         //Almacenaremos un booleanol según la apuesta tenga ganador (true) o no (false)
        int[] elegidos = new int[3];                    //Vector que contendrá los números que ya han sido elegidos

        //Revisamos que las fichas del usuario sean suficientes
        if (!gui.jugador.puedeJugar(FICHASAPUESTA)) {
            //Si no hay suficientes fichas mostramos un mensaje en la zona de informes.
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_informe4.setVisible(false);
            gui.label_informe3.setText("No tienes suficientes fichas como para seguir apostando.");
            gui.label_informe3.setVisible(true);

            //Revisamos también que el usuario haya seleccionado una apuesta, en caso contrario, mostramos otra advertencia.
        } else if (gui.jugador.getApuesta() == 0) {
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_informe4.setVisible(false);
            gui.label_informe3.setText("No has seleccionado ninguna casilla.");
            gui.label_informe3.setVisible(true);

            //Comprobamos que alguna CPU pueda jugar, si no mostraremos un mensaje de victoria.
        } else if (!gui.canSomeCpuPlay()) {
            gui.pan_informe1.setVisible(false);
            gui.pan_informe2.setVisible(false);
            gui.label_informe3.setVisible(false);
            gui.label_informe4.setText("Solo quedas tú en la mesa! Has desvalijado a todos.");
            gui.label_informe4.setVisible(true);

            //En caso que no se haya cumplido ninguna de las condiciones anteriormente mencionadas se prosigue con la ejecución
        } else {
            //Si label_informe3 es visible, lo ocultamos y volvemos a mostrar informe1 y informe2
            if (gui.label_informe3.isVisible() || gui.label_informe4.isVisible()) {
                gui.label_informe3.setVisible(false);
                gui.label_informe4.setVisible(false);
                gui.pan_informe1.setVisible(true);
                gui.pan_informe2.setVisible(true);
            }

            //El jugador está jugando (gui.jugador.jugando=true) ya que ha superado el condicional de control
            gui.jugador.setJugando();
            fichasEnJuego += FICHASAPUESTA;             //Le sumamos la apuesta a la cantidad de fichas en juego.
            gui.jugador.sumarFichas(-FICHASAPUESTA);    //Y se las restamos al jugador
            elegidos[0] = gui.jugador.getApuesta();     //Ponemos el número elegido por el jugador en un vector de elegidos.
            gui.info1.setText("<html>&nbsp;Has apostado por el: " + gui.jugador.getApuesta() + "</html>");  //Asignamos el texto al label info1. (GUI)

            if (gui.cpu1.puedeJugar(FICHASAPUESTA)) {              //Comprobamos si CPU1 puede jugar.
                gui.cpu1.setJugando();                             //CPU1 está jugando.
                gui.cpu1.setApuesta(rnd.nextInt(36) + 1);   //Generamos un número aleatorio (apuesta de la cpu1).
                //NOTA: Se puede mejorar la clase Jugadores implementando una función de apostar que devuelve una apuesta aleatoria, sin tener que generar el
                //número desde fuera de la clase y asignarlo con un setter.
                fichasEnJuego += FICHASAPUESTA;                    //Al igual que antes, sumamos la apuesta a las fichas en juego.
                gui.cpu1.sumarFichas(-FICHASAPUESTA);               //Y se las restamos a la CPU1
                //Si la apuesta ya ha sido seleccionada por el jugador, se vuelve a elegir otro número.
                while (gui.cpu1.getApuesta() == elegidos[0]) {
                    gui.cpu1.setApuesta(rnd.nextInt(36) + 1);
                }
                elegidos[1] = gui.cpu1.getApuesta();                //Ponemos la apuesta de la CPU1 en el vector de números elegidos
                gui.info2.setText("<html>&nbsp;CPU1 ha apostado por el: " + gui.cpu1.getApuesta() + "</html>");     //Asignamos el texto de info2 (GUI)
            }//end if CPU1 puedeJugar

            //Hacemos exactamente lo mismo con las demás CPU.
            gui.cpu2.setApuesta(rnd.nextInt(36) + 1);
            if (gui.cpu2.puedeJugar(FICHASAPUESTA)) {
                gui.cpu2.setJugando();
                fichasEnJuego += FICHASAPUESTA;
                gui.cpu2.setFichas(gui.cpu2.getFichas() - FICHASAPUESTA);
                while (gui.cpu2.getApuesta() == elegidos[0] || gui.cpu2.getApuesta() == elegidos[1]) {
                    gui.cpu2.setApuesta(rnd.nextInt(36) + 1);
                }
                elegidos[2] = gui.cpu2.getApuesta();
                gui.info3.setText("<html>&nbsp;CPU2 ha apostado por el: " + gui.cpu2.getApuesta() + "</html>");

            } //end if CPU2 puedeJugar

            gui.cpu3.setApuesta(rnd.nextInt(36) + 1);
            if (gui.cpu3.puedeJugar(FICHASAPUESTA)) {
                gui.cpu3.setJugando();
                fichasEnJuego += FICHASAPUESTA;
                gui.cpu3.setFichas(gui.cpu3.getFichas() - FICHASAPUESTA);
                while (gui.cpu3.getApuesta() == elegidos[0] || gui.cpu3.getApuesta() == elegidos[1] || gui.cpu3.getApuesta() == elegidos[2]) {
                    gui.cpu3.setApuesta(rnd.nextInt(36) + 1);
                }
                gui.info4.setText("<html>&nbsp;CPU3 ha apostado por el: " + gui.cpu3.getApuesta() + "</html>");
            } //end if cpu3 puedeJugar

            //jugadores = fichasEnJuego / FICHASAPUESTA;  //Si nos hiciera falta podríamos calcular el número de jugadores de esta forma




            gui.banca.setApuesta(rnd.nextInt(36) + 1);                       //Generamos un número ganador y lo almacenamos
            gui.info5.setText("<html>&nbsp;Ha salido el: " + gui.banca.getApuesta() + "</html>");     //Asignamos el texto del label info5 (GUI)

            //Si el número ganador es el número elegido por el usuario le sumamos las fichas en Juego y asignamos texto al label info6
            if (gui.banca.getApuesta() == gui.jugador.getApuesta()) {
                gui.jugador.setFichas(gui.jugador.getFichas() + fichasEnJuego);
                gui.info6.setText("<html>Has ganado!</html>");
            } else { //se ejecuta si el jugador no gana

                //Se ejecuta si la CPU1 está jugando.
                if (gui.cpu1.getJugando()) {
                    //Si la CPU1 ha ganado
                    if (gui.banca.getApuesta() == gui.cpu1.getApuesta()) {
                        gui.cpu1.setFichas(gui.cpu1.getFichas() + fichasEnJuego);   //Sumamos las fichas en juego a sus fichas
                        ganador = true;                                             //Se ha encontrado un ganador, bandera a true.
                        gui.info6.setText("<html>Ha ganado CPU1<html>");            //Asignamos el texto a info6 (GUI)
                    } //endif Ganador
                } //endif Jugando

                //Hacemos lo mismo con las demás CPU's
                if (gui.cpu2.getJugando()) {
                    if (gui.banca.getApuesta() == gui.cpu2.getApuesta()) {
                        gui.cpu2.setFichas(gui.cpu2.getFichas() + fichasEnJuego);
                        ganador = true;
                        gui.info6.setText("<html>Ha ganado CPU2<html>");
                    }
                }
                if (gui.cpu3.getJugando()) {
                    if (gui.banca.getApuesta() == gui.cpu3.getApuesta()) {
                        gui.cpu3.setFichas(gui.cpu3.getFichas() + fichasEnJuego);
                        ganador = true;
                        gui.info6.setText("<html>Ha ganado CPU3<html>");
                    }
                }
                //Hemos repetido lo mismo con CPU2 y CPU3

                //Comprobamos si la apuesta de cada jugador es un número rojo o un número negro iterando sobre los elementos del vector de números rojos.
                for (int k = 0; k < (GUI.rojos.length); k++) {
                    //Apuesta jugador es Rojo
                    if (GUI.rojos[k] == gui.jugador.getApuesta() && gui.jugador.getJugando()) {
                        gui.jugador.setEsRojo(true);
                        nRojos++;
                        gui.info1.setText("<html>&nbsp;Has apostado por el: <span color='red'>" + gui.jugador.getApuesta() + "</span></html>");
                    }
                    //Apuesta CPU1 es Rojo
                    if (GUI.rojos[k] == gui.cpu1.getApuesta() && gui.cpu1.getJugando()) {
                        gui.cpu1.setEsRojo(true);   //Asignamos a true gui.cpu1.esRojo
                        nRojos++;                   //Aumentamos en 1 el número de números rojos
                        //Imprimimos en la GUI (asignando texto a la label info2)
                        gui.info2.setText("<html>&nbsp;CPU1 ha apostado por el: <span color='red'>" + gui.cpu1.getApuesta() + "</span></html>");

                    }
                    //Apuesta CPU2 es Rojo
                    if (GUI.rojos[k] == gui.cpu2.getApuesta() && gui.cpu2.getJugando()) {
                        gui.cpu2.setEsRojo(true);
                        nRojos++;
                        gui.info3.setText("<html>&nbsp;CPU2 ha apostado por el: <span color='red'>" + gui.cpu2.getApuesta() + "</span></html>");
                    }
                    //Apuesta CPU3 es Rojo
                    if (GUI.rojos[k] == gui.cpu3.getApuesta() && gui.cpu3.getJugando()) {
                        gui.cpu3.setEsRojo(true);
                        nRojos++;
                        gui.info4.setText("<html>&nbsp;CPU3 ha apostado por el: <span color='red'>" + gui.cpu3.getApuesta() + "</span></html>");

                    }
                    //El número ganador es rojo
                    if (GUI.rojos[k] == gui.banca.getApuesta()) {
                        gui.banca.setEsRojo(true);
                        gui.info5.setText("<html>&nbsp;Ha salido el: <span color='red'>" + gui.banca.getApuesta() + "</span></html>");
                    }
                }

                //Si ningún jugador ha acertado, es decir, si no hay ningún ganador.
                if (!ganador) {

                    //Si el número ganador es rojo y al menos un jugador ha apostado por el rojo:
                    if (gui.banca.getEsRojo() && nRojos > 0) {
                        gui.banca.sumarFichas(fichasEnJuego / 2);
                        fraccionRecompensa = fichasEnJuego / 2 / nRojos;
                        if (gui.jugador.getEsRojo() && gui.jugador.getJugando())//Según lo hemos planteado el jugador siempre tiene que estar jugando por lo cual es reduntante
                            gui.jugador.sumarFichas(fraccionRecompensa);
                        if (gui.cpu1.getEsRojo() && gui.cpu1.getJugando())
                            gui.cpu1.sumarFichas(fraccionRecompensa);
                        if (gui.cpu2.getEsRojo() && gui.cpu2.getJugando())
                            gui.cpu2.sumarFichas(fraccionRecompensa);
                        if (gui.cpu3.getEsRojo() && gui.cpu3.getJugando())
                            gui.cpu3.sumarFichas(fraccionRecompensa);
                        gui.info6.setText("<html>Repartido <span color='red'>(Rojo)</span></html>");

                        //Si el número ganador es negro y al menos un jugador ha apostado por el negro:
                    } else if (!gui.banca.getEsRojo() && nRojos < 4) {
                        gui.banca.setFichas(gui.banca.getFichas() + fichasEnJuego / 2);
                        fraccionRecompensa = fichasEnJuego / 2 / (4 - nRojos);
                        if (!gui.jugador.getEsRojo() && gui.jugador.getJugando())
                            gui.jugador.sumarFichas(fraccionRecompensa);
                        if (!gui.cpu1.getEsRojo() && gui.cpu1.getJugando())
                            gui.cpu1.sumarFichas(fraccionRecompensa);
                        if (!gui.cpu2.getEsRojo() && gui.cpu2.getJugando())
                            gui.cpu2.sumarFichas(fraccionRecompensa);
                        if (!gui.cpu3.getEsRojo() && gui.cpu3.getJugando())
                            gui.cpu3.sumarFichas(fraccionRecompensa);
                        gui.info6.setText("<html>Repartido (Negro)</html>");

                        //Si no hay ningún jugador ganador, y no hay repartición, significa que ha ganado la banca
                    } else {
                        gui.banca.setFichas(gui.banca.getFichas() + fichasEnJuego); //Sumamos fichas a la banca
                        gui.info6.setText("<html>Ha ganado la banca</html>");       //Modificamos GUI
                    }
                } //end if no hay ningún ganador
                else
                    gui.banca.sumarFichas(0); //le sumamos 0 fichas a la banca (esto es necesario para calcular la diferencia de fichas)
            } //enf else  (no gana el jugador)
        } //end else (no se cumplen condiciones que bloquen la apuesta)
    } //end función apostar
} //end clase Ruleta

