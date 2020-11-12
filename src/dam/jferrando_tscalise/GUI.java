package dam.jferrando_tscalise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    static final int APUESTA = 10;
    static int apuestaUsuario;
    static int fichasUsuario = 100;
    static int fichasCPU1 = 100;
    static int fichasCPU2 = 100;
    static int fichasCPU3 = 100;
    static int fichasBanca = 1000;

    static JDialog dialog = new JDialog(new JFrame(), "Ruleta Americana", true);





    static Label info1 = new Label();
    static Label info2 = new Label();
    static Label info3 = new Label();
    static Label info4 = new Label();
    static Label info5 = new Label();
    static Label info6 = new Label();

    static int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 34};  //VECTOR DE NUMEROS ROJOS
    int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO

    public void createInterface(){

        JPanel informe = new JPanel();
        JPanel informe2 = new JPanel();


            /*static JPanel informe = new JPanel();
    static JPanel informe2 = new JPanel();*/



        //VARIABLES GUI RULETA
            int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO
            int casilla, primeraCasilla = 3, rows= 3, cols = 12 ;                           //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA, FILAS Y COLUMNAS
            boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO

            //CREACIÓN DIÁLOGO
            //CREACIÓN PANELES
            JPanel pan_contenedor = new JPanel();                               //PANEL CONTENEDOR
            JPanel pan_superior = new JPanel();                                 //PANEL CONTENEDOR SUPERIOR
            JPanel pan_tablero = new JPanel();                                  //PANEL TABLERO
            JPanel pan_inferior = new JPanel();                                 //PANEL INFERIOR
            JPanel list_container = new JPanel();                               //CONTENEDOR DE LISTA (CONTIENE Y LIMITA EL TAMAÑO)
            JPanel list = new JPanel();                                         //PANEL LISTA SUPERIOR IZQUIERDO (SE PUEDE SUSTITUIR CON UN LIST)

            //CREACIÓN ETIQUETAS
            Label label_tuApuesta = new Label("Apuesta: ");
            Label label_fichas = new Label("Fichas: " + fichasUsuario);
            Label label_cpu1 = new Label("CPU1: " + fichasCPU1);
            Label label_cpu2 = new Label("CPU2: " + fichasCPU2);
            Label label_cpu3 = new Label("CPU3: " + fichasCPU3);
            Label label_banca = new Label("Banca: " + fichasBanca);

            //CREACIÓN BOTONES
            JButton botonSalir = new JButton("Volver al Menú");
            JButton botonConfirmar = new JButton("Confirmar Apuesta");

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

                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            //Modificamos la variable apuestaUsuario
                            apuestaUsuario = Integer.parseInt(button.getText());
                            //Modificamos la etiqueta tuApuesta
                            label_tuApuesta.setText("Apuesta: "+apuestaUsuario);
                            dialog.revalidate();
                        }
                    });
                    pan_tablero.add(button);
                }
            }
            /*FIN ELEMENTOS PAN TABLERO*/



            //Lista
        /*List list = new List(5);
        list.add("Fichas Disponibles: X");
        list.add("CPU1: X");
        list.add("CPU2: X");
        list.add("CPU3: X");
        list.add("BANCA X");*/

            list.add(label_tuApuesta);
            list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
            list.add(label_fichas);
            list.add(label_cpu1);
            list.add(label_cpu2);
            list.add(label_cpu3);
            list.add(label_banca);
            list.setBackground(Color.lightGray);
            list.setMaximumSize(new Dimension(180, 300));

            list_container.setBackground(Color.lightGray);
            list_container.setMaximumSize(new Dimension(180,1800));
            list_container.add(list);



            //CONFIG pan_inferior
            pan_inferior.setSize(10,1);
            pan_inferior.setPreferredSize(new Dimension(600,35));

            //ELEMENTOS pan_inferior
            //pan_inferior.add(new Label("Fichas disponibles : "+str+"\n Banca: Y \n CPU1:"));
            //pan_inferior.add(new TextField("Insertar apuesta...")).setFont(new Font("Courier", Font.ITALIC,12));

            botonConfirmar.setFont(new Font("Courier", Font.BOLD, 15));
            botonSalir.setFont((new Font("Courier", Font.BOLD,15)));

            botonConfirmar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Ruleta.apostar();
                    label_fichas.setText("Fichas: " + fichasUsuario);
                    label_cpu1.setText("CPU1: " + fichasCPU1);
                    label_cpu2.setText("CPU2: " + fichasCPU2);
                    label_cpu3.setText("CPU3: " + fichasCPU3);
                    label_banca.setText("Banca: " + fichasBanca);
                    dialog.revalidate();
                }
            });
            botonSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //dialog.setVisible(false);
                    dialog.setVisible(false);
                }
            });

            pan_inferior.add(botonConfirmar);
            pan_inferior.add(botonSalir);




            pan_superior.setLayout(new BoxLayout(pan_superior,2));
            pan_superior.add(list_container);
            pan_superior.add(pan_tablero);


            //INFORME
            info5.setFont(new Font("courier", Font.BOLD, 20));
            info6.setFont(new Font("courier", Font.BOLD, 20));

            informe.add(info1);
            informe.add(info2);
            informe.add(info3);
            informe.add(info4);
            informe2.add(info5);
            informe2.add(info6);
            informe.setLayout(new GridLayout(1,4));
            informe2.setLayout(new GridLayout(1,2));
            //informe.setBackground(Color.lightGray);
            //informe2.setBackground(Color.lightGray);
            informe2.setMaximumSize(new Dimension(500, 50 ));






        //CONFIG pan_contenedor
            pan_contenedor.setLayout(new BoxLayout(pan_contenedor,1));
            //ELEMENTOS pan_contenedor
            pan_contenedor.add(pan_superior);
            pan_contenedor.add(pan_inferior);
            pan_contenedor.add(informe);
             pan_contenedor.add(informe2);


        //JDialog
            dialog.getContentPane().add(pan_contenedor);
            dialog.getContentPane().add(pan_contenedor);
            dialog.pack();



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
        public void revalidate(){
        dialog.revalidate();
    }

    public void setVisible(){
        dialog.setVisible(true);
    }

    }

