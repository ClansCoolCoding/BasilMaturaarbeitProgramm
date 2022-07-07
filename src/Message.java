class Message {

    private String klartext;
    private String chiffrentext;


    public Message(String klartext, String chiffrentext) {
        this.klartext = klartext;
        this.chiffrentext = chiffrentext;
    }

    public String getKlartext() {
        return klartext;
    }

    public String getChiffrentext() {
        return chiffrentext;
    }

    public void setKlartext(String klartext) {
        this.klartext = klartext;
    }

    public void setChiffrentext(String chiffrentext) {
        this.chiffrentext = chiffrentext;
    }
}