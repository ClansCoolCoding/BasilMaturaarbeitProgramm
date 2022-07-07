public class Key3 extends Key{

    public Key3(Message msg) {
        super(msg);
    }

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 7);

        for(int i = 0; i < pakete.length; i++){
            if(i % 2 == 0){
                pakete[i] = specWarp(pakete[i]);
            }if(i % 2 == 1){
                pakete[i] = specEndChange(pakete[i]);
            }
        }

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 7);

        for(int i = 0; i < pakete.length; i++){
            if(i % 2 == 0){
                pakete[i] = specWarp(pakete[i]);
            }if(i % 2 == 1){
                pakete[i] = specEndChange(pakete[i]);
            }
        }

        return pakete;
    }
}