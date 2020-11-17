package dam.jferrando_tscalise;

import javax.swing.*;
import java.awt.*;

public class GUI {

    /*NOTA: Los elementos declarados aqui, fuera de los metodos de la clase, son los que tendrán que ser accesibles desde otras clases*/


    //Creamos los jugadores y la banca como instancias de la clase jugador.
    Jugador jugador = new Jugador();
    Jugador banca = new Jugador(true);
    Jugador cpu1 = new Jugador();
    Jugador cpu2 = new Jugador();
    Jugador cpu3 = new Jugador();

    int apuestaAlConfirmar;     //Esta variable es necesaria para corregir el comportamiento al cambiar la apuesta mientras corre el timer.
    static final int FICHASAPUESTA = 10;    //Esta constante se usa tanto en GUI como en Ruleta. En Ruleta se referencia el valor de GUI.FICHASAPUESTA

    //Creamos el JDialog al cual insertaremos contenido posteriormente (Es el padre de nuestra GUI)
    JDialog dialog = new JDialog(new JFrame(), "Ruleta Americana", true);

    //Creamos las labels de la clase a las cuales asignaremos texto posteriormente desde la clase Ruleta (por ello están fuera del método principal.) (necesitan ser accesibles desde Ruleta.java)
    //NOTA: Usaremols una JLabel si tenemos la necesidad de insertar HTML dentro.
    JLabel info1 = new JLabel();
    JLabel info2 = new JLabel();
    JLabel info3 = new JLabel();
    JLabel info4 = new JLabel();
    JLabel info5 = new JLabel();
    JLabel info6 = new JLabel();
    Label label_informe3 = new Label("No tienes suficientes fichas como para seguir apostando.");  //Informe de errores y conficiones
    Label label_informe4 = new Label("Solo quedas tú en la mesa. ¡Has desvalijado a todos.!");  //Informe de errores y conficiones
    //END creación labels de la clase

    //Creación paneles de la clase (necesitan ser accesibles desde Ruleta.java)
    JPanel pan_informe1 = new JPanel();                                      //PANEL DE INFORMACION DE LOS RESULTADOS
    JPanel pan_informe2 = new JPanel();                                     //PANEL DEL NUMERO GANADOR

    static int[] rojos = {3, 9, 12, 18, 21, 30, 36, 5, 14, 23, 32, 1, 7, 16, 19, 25, 27, 34};  //VECTOR DE NUMEROS ROJOS

    public void nuevaApuesta() {
        jugador.nuevaApuesta();
        cpu1.nuevaApuesta();
        cpu2.nuevaApuesta();
        cpu3.nuevaApuesta();
        banca.nuevaApuesta();
    }

    public boolean canSomeCpuPlay() {
        return cpu1.puedeJugar(FICHASAPUESTA) || cpu2.puedeJugar(FICHASAPUESTA) || cpu3.puedeJugar(FICHASAPUESTA);
    }

    public GUI() { //CONSTRUCTOR, SE EJECUTARÁ AL INSTANCIAR GUI

        int DELAY = 3000;  //El timer tardará 3 segundos (3000ms) en girar la ruleta.

        JPanel pan_informes = new JPanel();

        //VARIABLES GUI RULETA
        int[][] tablero = new int[3][12];                                               //MATRIZ TABLERO
        int casilla, primeraCasilla = 3, rows = 3, cols = 12;                           //VARIABLES NUMERICAS PARA EL NUMERO DE LA CASILLA, FILAS Y COLUMNAS
        boolean flag;                                                                   //BOOLEANO QUE INDICA SI UN NUMERO ES ROJO O NO

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
        label_tuApuesta.setFont(new Font("Courier", Font.BOLD, 24));
        label_tuApuesta.setForeground(Color.BLACK);

        JLabel label_fichas = new JLabel(" Fichas: " + jugador.getFichas());
        label_fichas.setFont(new Font("Courier", Font.BOLD, 24));
        label_fichas.setForeground(Color.BLACK);

        JLabel label_cpu1 = new JLabel("<html>&nbsp;CPU1: " + cpu1.getFichas() + "</html>");
        label_cpu1.setFont(new Font("Courier", Font.BOLD, 24));
        label_cpu1.setForeground(Color.BLACK);

        JLabel label_cpu2 = new JLabel("<html>&nbsp;CPU2: " + cpu2.getFichas() + "</html>");
        label_cpu2.setFont(new Font("Courier", Font.BOLD, 24));
        label_cpu2.setForeground(Color.BLACK);

        JLabel label_cpu3 = new JLabel("<html>&nbsp;CPU3: " + cpu3.getFichas() + "</html>");
        label_cpu3.setFont(new Font("Courier", Font.BOLD, 24));
        label_cpu3.setForeground(Color.BLACK);

        JLabel label_banca = new JLabel("<html>&nbsp;Banca: " + banca.getFichas() + "</html>");
        label_banca.setFont(new Font("Courier", Font.BOLD, 24));
        label_banca.setForeground(Color.BLACK);

        label_informe3.setVisible(false);

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
        pan_list_izquierda.add(new Label("  Se apostarán " + FICHASAPUESTA + " fichas."));
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

                for (int rojo : rojos) {  //for k, recorre el vector de numeros rojos por cada casilla de la matriz tabelro
                    if (rojo == tablero[i][j]) {
                        flag = true;                    //Si el valor de la celda es igual a un número del vector rojos, asignamos la bandera a 'true'
                        break;
                    }
                }

                /*NOTA: ENHACED FOR RECOMENDADO POR EL IDE, FOR ORIGINAL:
                for (int k = 0; k < (rojos.length); k++) {  //for k, recorre el vector de numeros rojos por cada casilla de la matriz tabelro
                    if (rojos[k] == tablero[i][j])
                        flag = true;                    //Si el valor de la celda es igual a un número del vector rojos, asignamos la bandera a 'true'
                }
                 */

                //Si el flag es true el color de botón será rojo y la fuente negra, y en caso contrario el botón será negro y la fuente blanca
                if (!flag) {
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.white);
                } else
                    button.setBackground(Color.RED);

                //Añadimos un evento al presionar el botón
                button.addActionListener(e -> {

                    //Modificamos la variable del numero apostado por el usuario
                    jugador.setApuesta(Integer.parseInt(button.getText()));
                    //Modificamos la etiqueta tuApuesta
                    label_tuApuesta.setText(jugador.getColoredString("Apuesta: "));
                    dialog.revalidate();
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
        pan_informe2.setMaximumSize(new Dimension(600, 125));
        pan_informes.setSize(800, 150);
        pan_informes.setPreferredSize(new Dimension(800, 105));

        //ELEMENTOS INFORME
        pan_informes.add(pan_informe1);
        pan_informes.add(pan_informe2);
        pan_informes.add(label_informe3);
        pan_informes.add(label_informe4);

        pan_informe1.add(info1);
        pan_informe1.add(info2);
        pan_informe1.add(info3);
        pan_informe1.add(info4);
        pan_informe2.add(info5);
        pan_informe2.add(info6);

        info1.setFont(new Font("courier", Font.BOLD, 22));
        info1.setForeground(Color.BLACK);
        info2.setFont(new Font("courier", Font.BOLD, 22));
        info2.setForeground(Color.BLACK);
        info3.setFont(new Font("courier", Font.BOLD, 22));
        info3.setForeground(Color.BLACK);
        info4.setFont(new Font("courier", Font.BOLD, 22));
        info4.setForeground(Color.BLACK);
        info5.setFont(new Font("courier", Font.BOLD, 25));
        info5.setForeground(Color.BLACK);
        info6.setFont(new Font("courier", Font.BOLD, 25));
        info6.setForeground(Color.BLACK);

        label_informe3.setFont(new Font("Courier", Font.BOLD, 36));
        label_informe3.setForeground(Color.RED);
        label_informe3.setAlignment(Label.CENTER);
        label_informe4.setFont(new Font("Courier", Font.BOLD, 36));
        label_informe4.setForeground(Color.BLUE);
        label_informe4.setAlignment(Label.CENTER);
        label_informe4.setVisible(false);
        //END INFORMES

        //COMPORTAMIENTO BOTONES SALIR Y CONFIRMAR:

        //Creamos un nuevo Timer que se ejecutará al confirmar la apuesta mientras gira la ruleta. El timer tendrá un retraso de DELAY (CONSTANTE)
        Timer timer = new Timer(DELAY, evt -> {
            //Una vez completado el tiempo de espera del timer:
            jugador.setApuesta(apuestaAlConfirmar);         //La apuesta del jugador se asigna a la variable apuestaAlConfirmar
            Ruleta.apostar();                               //Ejecutamos la función apostar() de la clase Rulera
            botonConfirmar.setEnabled(true);                //Habilitamos de nuevo el botón de confirmar

            //Modificamos la GUI (si una CPU está sin fichas la tachamos y dejará de jugar
            label_fichas.setText("<html>&nbsp;Fichas: " + jugador.getFichas() + jugador.diferenciaFichasHtml() + "</html>");
            if (cpu1.puedeJugar(FICHASAPUESTA))
                label_cpu1.setText("<html>&nbsp;CPU1: " + cpu1.getFichas() + cpu1.diferenciaFichasHtml() + "</html>");
            else
                label_cpu1.setText("<html><strike>&nbsp;CPU1: " + cpu1.getFichas() + "</strike></html>");
            if (cpu2.puedeJugar(FICHASAPUESTA))
                label_cpu2.setText("<html>&nbsp;CPU2: " + cpu2.getFichas() + cpu2.diferenciaFichasHtml() + "</html>");
            else
                label_cpu2.setText("<html><strike>&nbsp;CPU2: " + cpu2.getFichas() + "</strike></html>");
            if (cpu3.puedeJugar(FICHASAPUESTA))
                label_cpu3.setText("<html>&nbsp;CPU3: " + cpu3.getFichas() + cpu3.diferenciaFichasHtml() + "</html>");
            else
                label_cpu3.setText("<html><strike>&nbsp;CPU3: " + cpu3.getFichas() + "</strike></html>");
            label_banca.setText("<html>&nbsp;Banca: " + banca.getFichas() + banca.diferenciaFichasHtml() + "</html>");

            label_informe3.setForeground(Color.RED);

            //Ejecutamos la función nuevaApuesta que restablece los valores por defecto a esRojo y jugando de los jugadores
            nuevaApuesta();

            //Revalidamos la GUI para que esta se actualice
            dialog.revalidate();

        });
        timer.setRepeats(false);  //El temporizador se repite hasta pulsar de nuevo el botón de confirmar.

        //Al hacer clic en el botón de confirmar se ejecuta lo siguiente:
        botonConfirmar.addActionListener(e -> {
            if (jugador.getApuesta() != 0 && jugador.puedeJugar(FICHASAPUESTA) && canSomeCpuPlay()) {
                //En condiciones normales (al menos una GUI en juego, una apuesta seleccionada y tenemos suficientes fichas) se ejecutará lo siguiente:
                apuestaAlConfirmar = jugador.getApuesta();  //Asignamos la variable apuestaAlConfirmar al valor de jugador.apuesta
                botonConfirmar.setEnabled(false);           //Deshabilitamos el boton de confirmar hasta después de la ejecución del timer
                pan_informe1.setVisible(false);             //Hacemos informe1 y informe2 invisibles
                pan_informe2.setVisible(false);
                label_informe3.setText("La ruleta está girando... ¡Buena suerte!"); //Insertamos un texto que indica que la ruleta está girado
                label_informe3.setForeground(Color.BLACK);  //El color del texto se asigna a negro
                label_informe3.setVisible(true);            //Hacemos visible el texto creado anteriormente
                timer.start();                              //Comenzamos el temporizador
                dialog.revalidate();                        //Revalidamos la GUI.
            } else
                //En el caso de una condición que de error en el if de apostar() omitimos el temporizador para que se muestre enseguida.
                Ruleta.apostar();

            //ESPERAR (3 Segundos)
            //Calcular canador y reasignar labels, removiendo el label grande que se ha creado anteriormente

        });

        //Funcionalidad del Botón de Salir -> Ocultará el JDialog.
        botonSalir.addActionListener(e -> dialog.setVisible(false));


        dialog.getContentPane().add(pan_contenedor);  //Añadimos pan_contenedor al JDialolg (contiene todos los subpaneles
        dialog.pack();                                //Redimensiona los elementos al tamaño favorito (setPreferredSize)

    }

}