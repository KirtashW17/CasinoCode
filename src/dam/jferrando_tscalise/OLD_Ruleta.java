package dam.fjerrando_tscalise;

import java.awt.*;
import javax.swing.*;

public class OLD_Ruleta extends JDialog {

    int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 34};  //VECTOR DE NUMEROS ROJOS
    boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO
    int casilla, primeraCasilla = 3;                                                //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA
    int[][] tablero = new int[3][12];                                               //MATRIZ PARA EL TABLERO

    public OLD_Ruleta(int row, int col){
        super();
        this.setTitle("PRUEBA");
        this.setSize(850,300);
        this.setModal(true);
        JPanel panel=new JPanel();
        panel.add(new JLabel("Hello dialog"));
        this.getContentPane().add(panel);

        JPanel pan_contenedor = new JPanel();                                     //PANEL CONTENEDOR
        JPanel pan_tablero = new JPanel();                                      //PANEL TABLERO
        JPanel pan_inferior = new JPanel();                                     //PAMEL INFERIOR

        //Generación del Tablero:
        for (int i = 0; i < 3; i++) {
            casilla = primeraCasilla;
            for (int j = 0; j < 12; j++) {
                tablero[i][j] = casilla;
                casilla += 3;
            }
            primeraCasilla--;
        }

        //CONFIG pan_tablero
        pan_tablero.setLayout(new GridLayout(row,col));
        //ELEMENTOS pan_tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                flag = false;
                JButton button = new JButton(Integer.toString(tablero[i][j]));
                button.setPreferredSize(new Dimension(65, 65));

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

        //CONFIG pan_inferior
        pan_inferior.setSize(10,1);
        pan_inferior.setPreferredSize(new Dimension(600,65));
        //ELEMENTOS pan_inferior
        pan_inferior.add(new Label("Fichas disponibles : X \n Banca: Y \n CPU1:"));
        pan_inferior.add(new TextField("Insertar Apuesta:"));
        pan_inferior.add(new JButton("Confirmar Apuesta"));
        pan_inferior.add(new JButton("Volver al Menú"));

        //CONFIG pan_contenedor
        pan_contenedor.setLayout(new BoxLayout(pan_contenedor,1));
        //ELEMENTOS pan_contenedor
        pan_contenedor.add(pan_tablero);
        pan_contenedor.add(pan_inferior);


        this.getContentPane().add(pan_contenedor);

    }
}


