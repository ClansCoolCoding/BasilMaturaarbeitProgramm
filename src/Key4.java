public class Key4 extends Key{

    public Key4() {}

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 4);

        for(int i = 0; i < pakete.length; i++){
            if(i % 3 == 1){
                pakete[i] = specWarp(pakete[i]);
            }if(i % 3 == 2){
                pakete[i] = specEndChange(pakete[i]);
            }
        }

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 4);

        for(int i = 0; i < pakete.length; i++){
            if(i % 3 == 1){
                pakete[i] = specWarp(pakete[i]);
            }if(i % 3 == 2){
                pakete[i] = specEndChange(pakete[i]);
            }
        }

        return pakete;
    }
}