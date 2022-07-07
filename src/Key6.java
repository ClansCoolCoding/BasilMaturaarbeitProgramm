public class Key6 extends Key{

    public Key6(Message msg) {
        super(msg);
    }

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 6);
        int[] plan = {0, 2, 3, 4, 5, 1};

        pakete = shuffleWithPlan(pakete, plan, '1');

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 6);
        int[] plan = {0, 5, 1, 2, 3, 4};

        pakete = shuffleWithPlan(pakete, plan, '1');

        return pakete;
    }
}