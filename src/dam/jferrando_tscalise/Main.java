package dam.jferrando_tscalise;

/*
 * CASINO CODE
 *
 * V. 0.6.2   LAST UPDATE: 11/11/2020
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
 */
//IMPORTS para el diseño de la interfaz
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

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

        //Gestor de Interfaz
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(160, 160, 160));
        UI.put("Panel.background", new ColorUIResource(160, 160, 160));


        //Solicitados la edad y la guardamos en la variable input, si la edad es "" la volvemos a pedir, si es null cerramos el programa
        do {
            input = JOptionPane.showInputDialog(null, "Inserte su edad:", "Control Edad",
                    JOptionPane.QUESTION_MESSAGE);
            if (input==null)
                System.exit(0);
            else if(!Pattern.matches("[0-9]+", input)){
                JOptionPane.showMessageDialog(null, "La edad insertada no es un número válido..");
                input = "";
            }

        } while (input.equals(""));

        //Una vez insertado
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
                    crearGUIRuleta("1");
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

    public static void crearGUIRuleta(String str) {
        int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO
        int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 34};  //VECTOR DE NUMEROS ROJOS
        int casilla, primeraCasilla = 3, rows= 3, cols = 12 ;                                                //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA
        boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO


        JDialog dialog = new JDialog(new JFrame(), "Ruleta Americana", true);
        JPanel pan_contenedor = new JPanel();                                     //PANEL CONTENEDOR
        JPanel pan_tablero = new JPanel();                                      //PANEL TABLERO
        JPanel pan_inferior = new JPanel();                                     //PANEL INFERIOR

        //Generación del Tablero:
        for (int i = 0; i < rows; i++) {
            casilla = primeraCasilla;
            for (int j = 0; j < cols; j++) {
                tablero[i][j] = casilla;
                casilla += 3;
            }
            primeraCasilla--;
        }
        //END Generación del tablero

        //CONFIG pan_tablero
        pan_tablero.setLayout(new GridLayout(rows,cols));
        //ELEMENTOS pan_tablero
        //SE GENERAN LOS BOTONES SEGÚN LA MATRIZ CREADA ANTERIORMENTE PARA EL TABLERO, LUEGO SI EL VALOR ESTÁ EN EL VECTOR ROJOS PINTA EL FONDO DE ROJO.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flag = false;
                JButton button = new JButton(Integer.toString(tablero[i][j]));
                button.setPreferredSize(new Dimension(65, 65));
                button.setFont(new Font("Courier", Font.BOLD,25));

                for (int k = 0; k < (rojos.length); k++) {
                    if (rojos[k] == tablero[i][j])
                        flag = true;
                }
                if (!flag) {
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.white);
                } else {
                    button.setBackground(Color.RED);
                }
                pan_tablero.add(button);
            }
        }




        //Lista
        /*List list = new List(5);
        list.add("Fichas Disponibles: X");
        list.add("CPU1: X");
        list.add("CPU2: X");
        list.add("CPU3: X");
        list.add("BANCA X");
*/
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.add(new Label("TUS FICHAS: X"));
        list.add(new Label("CPU1 : X"));
        list.add(new Label("CPU2 : X"));
        list.add(new Label("CPU3 : X"));
        list.add(new Label("BANCA : X"));
        list.setBackground(Color.lightGray);
        list.setMaximumSize(new Dimension(180, 300));

        JPanel list_container = new JPanel();
        list_container.setBackground(Color.lightGray);
        list_container.setMaximumSize(new Dimension(180,1800));
        list_container.add(list);



        //CONFIG pan_inferior
        pan_inferior.setSize(10,1);
        pan_inferior.setPreferredSize(new Dimension(600,65));
        //ELEMENTOS pan_inferior
        //pan_inferior.add(new Label("Fichas disponibles : "+str+"\n Banca: Y \n CPU1:"));
        pan_inferior.add(new TextField("Insertar apuesta...")).setFont(new Font("Courier", Font.ITALIC,12));
        pan_inferior.add(new JButton("Confirmar apuesta")).setFont(new Font("Courier", Font.BOLD,15));;
        pan_inferior.add(new JButton("Volver al Menú")).setFont(new Font("Courier", Font.BOLD,15));;




        JPanel pan_superior = new JPanel();
        pan_superior.setLayout(new BoxLayout(pan_superior,2));
        pan_superior.add(list_container);
        pan_superior.add(pan_tablero);


        //CONFIG pan_contenedor
        pan_contenedor.setLayout(new BoxLayout(pan_contenedor,1));
        //ELEMENTOS pan_contenedor
        pan_contenedor.add(pan_superior);
        pan_contenedor.add(pan_inferior);

        //JDialog
        dialog.getContentPane().add(pan_contenedor);
        dialog.getContentPane().add(pan_contenedor);
        pan_contenedor.add(new JButton("aa"));
        dialog.pack();
        dialog.setVisible(true);



        /*dialog.remove(0);
        dialog.getContentPane().add(pan_contenedor);
        dialog.pack();
        dialog.setVisible(true);
        **************************************
        pan_contenedor.add(new JButton("bb"));
        dialog.remove(pan_contenedor);
        dialog.add(pan_contenedor);
        dialog.validate();
        dialog.repaint();
        dialog.setVisible(false);
        dialog.setVisible(true);*/

    }

}
