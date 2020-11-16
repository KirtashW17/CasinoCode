package dam.jferrando_tscalise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    int DELAY = 3000;
    Jugador jugador = new Jugador(false,1000);
    Jugador banca = new Jugador(true);
    Jugador cpu1 = new Jugador();
    Jugador cpu2 = new Jugador();
    Jugador cpu3 = new Jugador();

    //LA CONSTANTE APUESTA DEBERÍA ESTAR EN RULETA.JAVA
    static final int APUESTA = 10;
    //VARIABLES ESTÁTICAS DE LA GUI, SE PUEDEN ELIMNAR Y ALMACENAR EN INSTANCIAS DE LA CLASE JUGADOR
    //FIXME INSTANCIAS OBJETO
    /*
    static int apuestaUsuario;
    static int fichasUsuario = 100;
    static int fichasCPU1 = 100;
    static int fichasCPU2 = 100;
    static int fichasCPU3 = 100;
    static int fichasBanca = 100;
     */


    //Cremos el JDialog al cual insertaremos contenido posteriormente
    static JDialog dialog = new JDialog(new JFrame(), "Ruleta Americana", true);


    //Creamos las labels estáticas a las cuales asignaremos texto posteriormente desde la clase Ruleta
    static JLabel info1 = new JLabel();
    static JLabel info2 = new JLabel();
    static JLabel info3 = new JLabel();
    static JLabel info4 = new JLabel();
    static JLabel info5 = new JLabel();
    static JLabel info6 = new JLabel();
    //END creación labels estáticas

    JPanel pan_informes = new JPanel();
    Label label_error = new Label("No tienes suficientes fichas como para seguir apostando.");
    JPanel pan_informe1 = new JPanel();                                      //PANEL DE INFORMACION DE LOS RESULTADOS
    JPanel pan_informe2 = new JPanel();                                     //PANEL DEL NUMERO GANADOR


    static int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 27, 34};  //VECTOR DE NUMEROS ROJOS
    int[][] tablero = new int[3][12];                                                       //MATRIZ TABLERO

    public GUI() { //CONSTRUCTOR, SE EJECUTARÁ AL INSTANCIAR GUI


        final ImageIcon iconTitle = new ImageIcon("img/icon.png");

        //VARIABLES GUI RULETA
        int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO
        int casilla, primeraCasilla = 3, rows = 3, cols = 12;                           //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA, FILAS Y COLUMNAS
        boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO

        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setPreferredSize(new Dimension(800,460));
        dialog.pack();

        dialog.setResizable(false);*/

        //CREACIÓN PANELES
        JPanel pan_contenedor = new JPanel();                               //PANEL CONTENEDOR
        JPanel pan_superior = new JPanel();                                 //PANEL CONTENEDOR SUPERIOR
        JPanel pan_list_izquierda = new JPanel();                                         //PANEL LISTA SUPERIOR IZQUIERDO (SE PUEDE SUSTITUIR CON UN LIST)
        JPanel pan_tablero = new JPanel();                                  //PANEL TABLERO
        JPanel pan_list_derecha = new JPanel();                                 //PANEL CON LA INFORMACION DE LA CPU A LA DERECHA
        JPanel pan_separador = new JPanel();                                 //PANEL INFERIOR


        //END CREACIÓN PANELES

        //CREACIÓN ETIQUETAS
        Label label_Title = new Label("Tu tablero:");
        label_Title.setFont(new Font("Courier", Font.BOLD, 32));
        label_Title.setForeground(Color.BLACK);

        JLabel label_tuApuesta = new JLabel("<html>&nbsp;Apuesta:</html> ");
        label_tuApuesta.setFont(new Font("Courier", Font.BOLD, 26));
        label_tuApuesta.setForeground(Color.BLACK);

        JLabel label_fichas = new JLabel(" Fichas: " + jugador.getFichas());
        label_fichas.setFont(new Font("Courier", Font.BOLD, 26));
        label_fichas.setForeground(Color.BLACK);

        JLabel label_cpu1 = new JLabel("<html>&nbsp;CPU1: " + cpu1.getFichas() + "</html>");
        label_cpu1.setFont(new Font("Courier", Font.BOLD, 26));
        label_cpu1.setForeground(Color.BLACK);

        JLabel label_cpu2 = new JLabel("<html>&nbsp;CPU2: " + cpu2.getFichas() + "</html>");
        label_cpu2.setFont(new Font("Courier", Font.BOLD, 26));
        label_cpu2.setForeground(Color.BLACK);

        JLabel label_cpu3 = new JLabel("<html>&nbsp;CPU3: " + cpu3.getFichas() + "</html>");
        label_cpu3.setFont(new Font("Courier", Font.BOLD, 26));
        label_cpu3.setForeground(Color.BLACK);

        JLabel label_banca = new JLabel("<html>&nbsp;Banca: " + banca.getFichas() + "</html>");
        label_banca.setFont(new Font("Courier", Font.BOLD, 26));
        label_banca.setForeground(Color.BLACK);

        label_error.setVisible(false);

        //END CREACIÓN DE ETIQUETAS

        //CREACIÓN BOTONES
        JButton botonSalir = new JButton("Abandonar juego");
        JButton botonConfirmar = new JButton("Confirmar apuesta");
        //CONFIG BOTONES
        botonConfirmar.setFont(new Font("Courier", Font.BOLD, 26));
        botonSalir.setFont((new Font("Courier", Font.BOLD, 26)));

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

        //CONFIG pan_contenedor
        pan_contenedor.setLayout(new BoxLayout(pan_contenedor, 1));
        //ELEMENTOS pan_contenedor
        pan_contenedor.add(pan_superior);
        //pan_contenedor.add(pan_separador);
        pan_contenedor.add(pan_informes);

        //CONFIG pan_superior ---> Subpanel hijo de pan_contenedor
        pan_superior.setLayout(new BoxLayout(pan_superior, 0));
        //ELEMENTOS pan_superior
        pan_superior.add(pan_list_izquierda);
        pan_superior.add(pan_tablero);
        pan_superior.add(pan_list_derecha);

        //LISTAS
        //CONFIG pan_list_izquierda ---> Subpanel hijo de pan_superior
        pan_list_izquierda.setLayout(new GridLayout(5, 1));
        //ELEMENTOS pan_list_izquierda
        pan_list_izquierda.add(label_Title);
        pan_list_izquierda.add(label_tuApuesta);
        pan_list_izquierda.add(label_fichas);
        pan_list_izquierda.add(new Label("  Se apostarán 10 fichas."));
        pan_list_izquierda.add(botonConfirmar);

        //CONFIG pan_list_derecha -> Subpanel hijo de pan_superior
        pan_list_derecha.setLayout(new GridLayout(5, 1));
        pan_list_izquierda.setBackground(Color.lightGray);
        //ELEMENTOS pan_list_derecha
        pan_list_derecha.add(label_cpu1);
        pan_list_derecha.add(label_cpu2);
        pan_list_derecha.add(label_cpu3);
        pan_list_derecha.add(label_banca);
        pan_list_derecha.add(botonSalir);
        pan_list_derecha.setBackground(Color.lightGray);


        //CONFIG pan_tablero
        pan_tablero.setLayout(new GridLayout(rows, cols));

        //ELEMENTOS pan_tablero
        //SE GENERAN LOS BOTONES SEGÚN LA MATRIZ CREADA ANTERIORMENTE PARA EL TABLERO, LUEGO SI EL VALOR ESTÁ EN EL VECTOR ROJOS PINTA EL FONDO DE ROJO.
        //ES DECIR, SE RECORRE LA MATRIZ DE TABLERO; Y SI LA CASILLA FORMA PARTE DEL VECTOR DE NUMEROS ROJOS, SE CREARÁ UN BOTÓN ROJO
        for (int i = 0; i < rows; i++) { //FOR i (filas)
            for (int j = 0; j < cols; j++) { //FOR j (columnas)
                flag = false; //indica si el número en cuestión es rojo
                JButton button = new JButton(Integer.toString(tablero[i][j]));  //Creamos un nuevo botón cuyo contenido será el número contenido en tablero[i][j] pasado a string
                button.setPreferredSize(new Dimension(100, 100));   //Insertamos el tamaño del botón
                button.setFont(new Font("Courier", Font.BOLD, 25));    //Configuramos la fuente del botón
                for (int k = 0; k < (rojos.length); k++) {  //for k, recorre el vector de numeros rojos por cada casilla de la matriz tabelro
                    if (rojos[k] == tablero[i][j])
                        flag = true;                    //Si el valor de la celda es igual a un número del vector rojos, asignamos la bandera a 'true'
                }

                //Si el flag es true el color de botón será rojo y la fuente negra, y en caso contrario el botón será negro y la fuente blanca
                if (!flag) {
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.white);
                } else
                    button.setBackground(Color.RED);

                //Añadimos un evento al presionar el botón
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //Modificamos la variable del numero apostado por el usuario
                        jugador.setApuesta(Integer.parseInt(button.getText()));
                        //Modificamos la etiqueta tuApuesta
                        label_tuApuesta.setText(jugador.getColoredString("Apuesta: "));
                        dialog.revalidate();
                    }
                });
                pan_tablero.add(button);        //Añadimos el botón en el pan_tablero
            }
        }
        /*FIN ELEMENTOS PAN TABLERO*/


        //CONFIG pan_separador
        pan_separador.setSize(800, 55);
        pan_separador.setPreferredSize(new Dimension(800, 55));

        //CONFIG INFORMES
        pan_informes.setLayout(new BoxLayout(pan_informes, 1));
        pan_informe1.setLayout(new GridLayout(1, 4));
        pan_informe2.setLayout(new GridLayout(1, 2));
        pan_informe2.setMaximumSize(new Dimension(550, 125));
        pan_informes.setSize(800, 150);
        pan_informes.setPreferredSize(new Dimension(800, 105));

        //ELEMENTOS INFORME
        pan_informes.add(pan_informe1);
        pan_informes.add(pan_informe2);
        pan_informes.add(label_error);
        pan_informe1.add(info1);
        pan_informe1.add(info2);
        pan_informe1.add(info3);

        pan_informe1.add(info4);
        pan_informe2.add(info5);
        pan_informe2.add(info6);
        info1.setFont(new Font("courier", Font.BOLD, 22));
        info1.setForeground(Color.BLACK);
        info5.setFont(new Font("courier", Font.BOLD, 28));
        info5.setForeground(Color.BLACK);
        info6.setFont(new Font("courier", Font.BOLD, 28));
        info6.setForeground(Color.BLACK);


        //COMPORTAMIENTO BOTONES SALIR Y CONFIRMAR
        Timer timer = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonConfirmar.setEnabled(true);
                Ruleta.apostar();
                label_fichas.setText("<html>&nbsp;Fichas: " + jugador.getFichas() + jugador.diferenciaFichasHtml() + "</html>");
                if (cpu1.puedeJugar(APUESTA))
                    label_cpu1.setText("<html>&nbsp;CPU1: " + cpu1.getFichas() + cpu1.diferenciaFichasHtml() + "</html>");
                else
                    label_cpu1.setText("<html><strike>&nbsp;CPU1: " + cpu1.getFichas() + "</strike></html>");
                if (cpu2.puedeJugar(APUESTA))
                    label_cpu2.setText("<html>&nbsp;CPU2: " + cpu2.getFichas() + cpu2.diferenciaFichasHtml() + "</html>");
                else
                    label_cpu2.setText("<html><strike>&nbsp;CPU2: " + cpu2.getFichas() + "</strike></html>");
                if (cpu3.puedeJugar(APUESTA))
                    label_cpu3.setText("<html>&nbsp;CPU3: " + cpu3.getFichas() + cpu3.diferenciaFichasHtml() + "</html>");
                else
                    label_cpu3.setText("<html><strike>&nbsp;CPU3: " + cpu3.getFichas() + "</strike></html>");
                label_banca.setText("<html>&nbsp;Banca: " + banca.getFichas() + banca.diferenciaFichasHtml() + "</html>");

                nuevaApuesta();
                dialog.revalidate();
            }
        });
        timer.setRepeats(false);
        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                //ESTA GIRANDO LA RULETA (LABEL)
                if (jugador.getApuesta() != 0 && jugador.puedeJugar(APUESTA) && canSomeCpuPlay()) {
                    botonConfirmar.setEnabled(false);
                    pan_informe1.setVisible(false);
                    pan_informe2.setVisible(false);
                    label_error.setText("La ruleta está girando... ¡Buena suerte!");
                    label_error.setFont(new Font("Courier", Font.BOLD, 36));
                    label_error.setForeground(Color.BLACK);
                    label_error.setAlignment(Label.CENTER);
                    label_error.setVisible(true);
                    timer.start();
                    dialog.revalidate();
                } else
                    Ruleta.apostar();

                //ESPERAR (3 Segundos)
                //Calcular canador y reasignar labels, removiendo el label grande que se ha creado anteriormente

            }
        });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dialog.setVisible(false);
                dialog.setVisible(false);
            }
        });
        //JDialog
        dialog.getContentPane().add(pan_contenedor);
        dialog.pack();

    }


    public void setVisible() {
        dialog.setVisible(true);
    }

    public void nuevaApuesta() {
        jugador.nuevaApuesta();
        cpu1.nuevaApuesta();
        cpu2.nuevaApuesta();
        cpu3.nuevaApuesta();
        banca.nuevaApuesta();
    }

    public boolean canSomeCpuPlay() {
        if (cpu1.puedeJugar(APUESTA)||cpu2.puedeJugar(APUESTA)||cpu3.puedeJugar(APUESTA))
            return true;
        else
            return false;
    }

}