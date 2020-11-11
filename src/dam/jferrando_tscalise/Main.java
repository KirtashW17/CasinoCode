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
 */
//IMPORTS para el diseño de la interfaz
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Main {

    final int Apuesta = 10;
    int bancaTotal = 1000;
    int fichasUsuario = 100;

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
            if (input==null)
                System.exit(0);
            //Si el input no es numérico repetimos el bucle.
            else if(!Pattern.matches("[0-9]+", input)){
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
        while(true) {
            input = (String) JOptionPane.showInputDialog(null, "¿A que deseas jugar?", title,
                    JOptionPane.QUESTION_MESSAGE, icon, MenuInicio, MenuInicio[0]);
            if (input==null)
                System.exit(0);
            switch (input) {
                case "Juego de la Ruleta Americana":
                    //Si elegimos el Juego de la Ruleta Americana generamos la interfaz del juego.
                    crearGUIRuleta("1");
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

    public static void crearGUIRuleta(String str) {
        //VARIABLES GUI RULETA
        int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO
        int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 34};  //VECTOR DE NUMEROS ROJOS
        int casilla, primeraCasilla = 3, rows= 3, cols = 12 ;                           //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA, FILAS Y COLUMNAS
        boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO
        final int[] prueba = new int[10];                                               //ARRAY FINAL (datos botones)

        //CREACIÓN DIÁLOGO
        JDialog dialog = new JDialog(new JFrame(), "Ruleta Americana", true);
        //CREACIÓN PANELES
        JPanel pan_contenedor = new JPanel();                               //PANEL CONTENEDOR
        JPanel pan_tablero = new JPanel();                                  //PANEL TABLERO
        JPanel pan_inferior = new JPanel();                                  //PANEL INFERIOR
        JPanel list = new JPanel();
        JPanel pan_superior = new JPanel();
        JPanel list_container = new JPanel();
        //CREACIÓN ETIQUETAS
        Label cpu1 = new Label("CPU1: X");
        Label label_tuApuesta = new Label("No has seleccionado ningún numer");



        //Generación del Tablero (rellenando la matriz):
        for (int i = 0; i < rows; i++) {
            casilla = primeraCasilla;
            for (int j = 0; j < cols; j++) {
                tablero[i][j] = casilla;
                //Se suma de 3 en 3
                casilla += 3;
            }
            //Cada fila disminuye en 1 la primera casilla
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

                //Variables finales solicitadas dentro del actionPerformed
                int finalCasilla = tablero[i][j];
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        label_tuApuesta.setText("Vas a apostar por el "+finalCasilla);
                        prueba[0]=finalCasilla;
                        dialog.revalidate();
                    }
                });
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


        list.add(label_tuApuesta);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.add(new Label("TUS FICHAS: X"));
        list.add(cpu1);
        list.add(new Label("CPU2 : X"));
        list.add(new Label("CPU3 : X"));
        list.add(new Label("BANCA : X"));
        list.setBackground(Color.lightGray);
        list.setMaximumSize(new Dimension(180, 300));

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

        JButton botonSalir = new JButton("Volver al Menú");
        botonSalir.setFont((new Font("Courier", Font.BOLD,15)));
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dialog.setVisible(false);
                System.out.println(prueba[0]);
                dialog.dispose();
            }
        });

        pan_inferior.add(botonSalir);




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
