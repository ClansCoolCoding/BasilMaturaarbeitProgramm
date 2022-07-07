public class Key1 extends Key{

    public Key1(Message msg) {
        super(msg);
    }

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 5);
        for(int i = 0; i < pakete.length; i++){
            pakete[i] = specEndChange(pakete[i]);
        }

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 5);
        for(int i = 0; i < pakete.length; i++){
            pakete[i] = specEndChange(pakete[i]);
        }

        return pakete;
    }
}