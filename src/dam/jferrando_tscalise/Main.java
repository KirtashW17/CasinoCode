package dam.jferrando_tscalise;

/*
 * CASINO CODE
 *
 * V. 0.6.3   LAST UPDATE: 11/11/2020
 *
 * Creadores:
 * Joan S. Ferrando
 * Thomas Scalise
 *
 * Solo pueden jugar mayoes de 18 años
 *
 * GIT REPOSITORY: https://github.com/Kycoo17/CasinoCode
 *
 * TODO
 *  -Funcionalidad Botones
 *  -CPU' Jugadores
 *  -Actualizar JDialog
 *  -Color de Fuente
 *  -Layout
 *  -Tamaño Label
 *  -Cambiar MessageTypes de JOptionPane
 */
//IMPORTS para el diseño de la interfaz

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        // Constantes INT menú
        final int edadLegal = 18;
        final ImageIcon icon = new ImageIcon("img/icon.png");  //FIXME elegir icono


        // Variable INT menú
        int edadInt;

        // Variables Strings Menú
        String title = "Casino Code";
        String[] MenuInicio = {"Juego de la Ruleta Americana", "Juego del Bingo", "Juego de Arcade", "Salir"};
        String input;

        //Gestor de Interfaz
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(160, 160, 160));
        UI.put("Panel.background", new ColorUIResource(160, 160, 160));


        //Solicitamos la edad y la guardamos en la variable input, si la edad es "" la volvemos a pedir, si es null cerramos el programa.
        do {
            input = JOptionPane.showInputDialog(null, "Inserte su edad:", "Control Edad",
                    JOptionPane.QUESTION_MESSAGE);
            if (input == null)
                System.exit(0);
                //Si el input no es numérico repetimos el bucle.
            else if (!Pattern.matches("[0-9]+", input)) {
                JOptionPane.showMessageDialog(null, "La edad insertada no es un número válido..");
                input = "";
            }
        } while (input.equals(""));

        //Una vez insertada la edad la parseamos a Int y comprobamos que no sea inferior a la edad legal
        edadInt = Integer.parseInt(input);
        if (edadInt < edadLegal) {
            JOptionPane.showMessageDialog(null, "Necesitas ser mayor de edad para jugar.");
            System.exit(0);
        }

        //Bucle principal del menú de los juegos.
        while (true) {
            input = (String) JOptionPane.showInputDialog(null, "¿A que deseas jugar?", title,
                    JOptionPane.QUESTION_MESSAGE, icon, MenuInicio, MenuInicio[0]);
            if (input == null)
                System.exit(0);
            switch (input) {
                case "Juego de la Ruleta Americana":
                    //Si elegimos el Juego de la Ruleta Americana generamos la interfaz del juego.
                    new GUI();
                    break;
                case "Juego del Bingo":
                    JOptionPane.showMessageDialog(null, "Disponible Próximamente", title, 1);
                    break;
                case "Juego de Arcade":
                    JOptionPane.showMessageDialog(null, "Disponible Próximamente", title, 1);
                    break;
                case "Salir":
                    System.exit(0);     //0 = NO HAY ERRORES
                    break;
                default:
                    break;
            }

        }
    }
}