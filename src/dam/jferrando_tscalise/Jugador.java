package dam.jferrando_tscalise;

public class Jugador {

    //FIXME fichas banca y usuario

    int apuesta;
    int fichas;
    int oldFichas;
    boolean jugando = false;
    boolean esRojo = false;
    boolean esBanca;

    public Jugador(boolean esBanca) {
        this.esBanca = esBanca;
        if (esBanca)
            fichas = 1000;
        else
            fichas = 100;
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

    public String diferenciaFichasHtml() {
        int diferencia = this.fichas - this.oldFichas;
        if (diferencia != -10 && !esBanca)
            diferencia -= 10;
        if (oldFichas == 0)
            return "";
        if (diferencia >= 0)
            return "&nbsp;<span color='green'>(+" + diferencia + ")</span>";
        else
            return "&nbsp;<span color='red'>(" + diferencia + ")</span>";
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

    private void comprobarSiEsRojo() {
        boolean esRojo = false;
        for (int i = 0; i < GUI.rojos.length; i++) {
            if (GUI.rojos[i] == this.apuesta) {
                esRojo = true;
                break;
            }
        }
        this.esRojo = esRojo;
    }

    public String getColoredString(String str) {
        this.comprobarSiEsRojo();
        if (this.esRojo)
            return "<html>&nbsp;" + str + "<span color='red'>" + apuesta + "</span></html>";
        else
            return "<html>&nbsp;" + str + apuesta + "</html>";
    }

    public void nuevaApuesta() {
        this.esRojo = false;
        this.jugando = false;
    }

    public boolean puedeJugar(int apuesta) {
        return this.fichas >= apuesta;
    }
}
