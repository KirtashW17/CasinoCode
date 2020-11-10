package dam.JFerrando_TScalise;


import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class Main {

    public static void main(String[] args) {
        /*
         * CASINO CODE
         *
         * V. 0.3   LAST UPDATE: 09/11/2020
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
         *  -COMENTAR
         *  -ARREGLAR RULETA.JAVA
         *
         */

        // Constantes
        final int Apuesta = 10;
        final int edadLegal = 18;
        final ImageIcon icon = new ImageIcon("img/icon.png");  //FIXME elegir icono


        // Variables INT
        int bancaTotal = 1000;
        int fichasUsuario = 100;
        int edadInt;
        int primeraCasilla=3;
        int casilla;

        //  Variables Strings
        String title = "Casino Code";
        String[] MenuInicio = {"Juego de la Ruleta Americana", "Juego del Bingo", "Juego de arcade", "Salir"};
        String input;

        //Matriz
        int[][] tablero = new int[3][12];

        for(int i = 0; i<3;i++){
            casilla=primeraCasilla;
            for(int j = 0; j<12;j++){
                tablero[i][j]=casilla;
                casilla += 3;
            }
            primeraCasilla--;
        }

        for(int i = 0; i<3;i++){
            for(int j = 0; j<12;j++){
                System.out.print(tablero[i][j] + ",");
            }
            System.out.println();
        }

        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(160, 160, 160));
        UI.put("Panel.background", new ColorUIResource(160, 160, 160));


        input = (String) JOptionPane.showInputDialog(null, "Inserte su edad:", "Control Edad",
                JOptionPane.QUESTION_MESSAGE);
        if (input==null || input.equals(""))
            System.exit(0);
        edadInt = Integer.parseInt(input);
        if (edadInt < edadLegal) {
            JOptionPane.showMessageDialog(null, "Necesitas ser mayor de edad para jugar.");
            System.exit(0);
        }


        while(true) {      //Sorthand
            input = (String) JOptionPane.showInputDialog(null, "¿A que deseas jugar?", title,
                    JOptionPane.QUESTION_MESSAGE, icon, MenuInicio, MenuInicio[0]);
            if (input==null){
                System.exit(0);
            }
            switch (input) {
                case "Juego de la Ruleta Americana":
                    JOptionPane.showMessageDialog(null, "Ruleta Americana", title, 1);
                    Ruleta ruleta = new Ruleta(3,12);
                    ruleta.setVisible(true);
                    ruleta.pack();
                    break;
                case "Juego del Bingo":
                    break;
                case "Juego de arcade":
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
