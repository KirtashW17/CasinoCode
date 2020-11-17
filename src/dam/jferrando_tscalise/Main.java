package dam.jferrando_tscalise;

/*
 * CASINO CODE
 *
 * V. 1.1   LAST UPDATE: 16/11/2020
 *
 * Creadores:
 * Joan S. Ferrando
 * Thomas Scalise
 *
 * Solo pueden jugar mayoes de 18 años
 *
 * GIT REPOSITORY: https://github.com/Kycoo17/CasinoCode
 *
 */

//IMPORTS para el diseño de la interfaz


import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        /*Importamos el juego de la ruleta y creamos la interfaz, pero no la mostramos aún*/
        Ruleta ruleta = new Ruleta();

        // Constantes menú
        final int EDADLEGAL = 18;
        final ImageIcon ICON = new ImageIcon("img/icon.png");
        final ImageIcon WORKING = new ImageIcon("img/working.png");

        // Variable INT menú
        int edadInt;

        // Variables Strings Menú
        String title = "Casino Code";
        String[] MenuInicio = {"Juego de la Ruleta Americana", "Juego del Bingo", "Juego de Arcade", "Salir"};
        String input;

        //Gestor de Interfaz y de color
        UIManager.put("OptionPane.background", new ColorUIResource(160, 160, 160));
        UIManager.put("Panel.background", new ColorUIResource(160, 160, 160));


        //Solicitamos la edad y la guardamos en la variable input, si la edad es "" la volvemos a pedir, si es null cerramos el programa.
        do {
            input = JOptionPane.showInputDialog(null, "Inserte su edad:", "Control Edad",
                    JOptionPane.QUESTION_MESSAGE);
            if (input == null) //Si el input es nulo cerramos el programa
                System.exit(0);
                //Si el input no es numérico repetimos el bucle.
            else if (!Pattern.matches("[0-9]+", input)) {   //Si el input no es un número válido mostramos un mensaje con JOPtionPane
                JOptionPane.showMessageDialog(null, "La edad insertada no es un número válido..");
                input = "";
            }
        } while (input.equals("")); //Si el input es "" se vuelve a pedir

        //Una vez insertada la edad la parseamos a Int y comprobamos que no sea inferior a la edad legal
        edadInt = Integer.parseInt(input);
        if (edadInt < EDADLEGAL) {
            JOptionPane.showMessageDialog(null, "Necesitas ser mayor de edad para jugar.");
            System.exit(0);
        }

        //Bucle principal del menú de los juegos.
        while (true) {
            input = (String) JOptionPane.showInputDialog(null, "¿A que deseas jugar?", title,
                    JOptionPane.QUESTION_MESSAGE, ICON, MenuInicio, MenuInicio[0]);
            if (input == null)
                System.exit(0);
            switch (input) {
                case "Juego de la Ruleta Americana":
                    //Si elegimos el Juego de la Ruleta Americana mostramos la interfaz del juego.
                    ruleta.showInterface();
                    break;
                case "Juego del Bingo":
                    //Si elegimos el Juego del Bingo, sale un mensaje de que esta desarollo
                    JOptionPane.showMessageDialog(null, "Estamos trabajando duro para ofrecerte el Juego del Bingo lo antes posible", "Disponible Próximamente", JOptionPane.INFORMATION_MESSAGE,WORKING);
                    break;
                case "Juego de Arcade":
                    //Si elegimos el Juego de Arcade, sale un mensaje de que esta desarollo.
                    JOptionPane.showMessageDialog(null, "Estamos trabajando duro para ofrecerte el Juego de Arcade lo antes posible", "Disponible Próximamente", JOptionPane.INFORMATION_MESSAGE,WORKING);
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