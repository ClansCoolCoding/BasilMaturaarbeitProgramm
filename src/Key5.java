public class Key5 extends Key{

    public Key5(Message msg) {
        super(msg);
    }

    @Override
    protected String[] encodeMTD(String binaryNumbersComplete){

        String[] pakete = paeckchen(binaryNumbersComplete, 4);
        int[] plan = {3, 0, 2, 1};

        pakete = shuffleWithPlan(pakete, plan, '2');

        return pakete;
    }

    @Override
    protected String[] decodeMTD(String chiffrenText) {

        String[] pakete = paeckchen(chiffrenText, 4);
        int[] plan = {1, 3, 2, 0};

        pakete = shuffleWithPlan(pakete, plan, '2');

        return pakete;
    }
}