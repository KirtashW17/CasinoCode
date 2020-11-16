package dam.jferrando_tscalise;

public class Jugador {

    int apuesta;
    int fichas ;
    int oldFichas;
    boolean jugando = false;
    boolean esRojo = false;
    boolean esBanca;

    public Jugador(boolean esBanca, int fichas) {
        this.esBanca = esBanca;
        this.fichas = fichas;
        if (esBanca)
            fichas=1000;
        else
            fichas=100;
    }
    public Jugador(boolean esBanca) {
        this(esBanca,100);
    }

    public Jugador() {
        this(false);
    }

    public int getApuesta() {
        return apuesta;
    }

    public int getFichas() {
        return fichas;
    }

    public boolean getEsRojo() {
        return esRojo;
    }

    public boolean getJugando() {
        return jugando;
    }

    public void setFichas(int newFichas) {
        this.oldFichas = this.fichas;
        this.fichas = newFichas;
    }

    public String diferenciaFichasHtml(){
        int diferencia = this.fichas-this.oldFichas;
        if (diferencia!=-10 && !esBanca)
            diferencia-=10;
        if (oldFichas==0)
            return "";
        if(diferencia>=0)
            return "&nbsp;<span color='green'>(+"+diferencia+")</span>";
        else
            return "&nbsp;<span color='red'>("+diferencia+")</span>";
    }

    public void sumarFichas(int sum) {
        this.oldFichas = this.fichas;
        this.fichas += sum;
    }

    public void setApuesta(Integer newApuesta) {
        this.apuesta = newApuesta;

    }

    public void setJugando() {
        this.jugando = true;
    }

    public void setEsRojo(boolean esRojo) {
        this.esRojo = esRojo;
    }

    public void comprobarSiEsRojo() {
        boolean esRojo = false;
        for (int i = 0 ; i<GUI.rojos.length ; i++) {
            if (GUI.rojos[i] == this.apuesta)
                esRojo = true;
        }
        this.esRojo = esRojo;
    }

    public String getColoredString(String str){
        this.comprobarSiEsRojo();
        if (this.esRojo)
            return "<html>&nbsp;" + str + "<span color='red'>" +  apuesta + "</span></html>";
        else
            return "<html>&nbsp;" + str + apuesta + "</html>";
    }

    public void nuevaApuesta() {
        this.esRojo = false;
        this.jugando = false;
    }

    public boolean puedeJugar(int apuesta) {
        if (this.fichas < apuesta)
            return false;
        else
            return true;
    }
}
