public class Key2 extends Key{

    public Key2(Message msg) {
        super(msg);
    }

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 3);

        for(int i = 0; i < pakete.length; i++){
            pakete[i] = specWarp(pakete[i]);
        }

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 3);

        for(int i = 0; i < pakete.length; i++){
            pakete[i] = specWarp(pakete[i]);
        }

        return pakete;
    }
}