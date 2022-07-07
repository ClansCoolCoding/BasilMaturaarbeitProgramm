public class RSATools {

    //Exponierung
    public static int exp(int base, int exponent){
        int factor = base;
        for(int i = 1; i < exponent; i++) {
            base = base*factor;
        }
        return base;
    }

    //Binärarray aus Zahl generieren
    private static int[] bin(int number) {
        String result = "";

        result = Integer.toBinaryString(number);
        int[] bres = new int[result.length()];
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '1') {
                bres[i] = 1;
            }
        }
        return bres;
    }

    /*Square and Multiply Verfahren wie in
    Unterlagen GUT und
    https://www.youtube.com/watch?v=cbGB__V8MNk*/
    public static int squareAndMultiply(int m, int e, int n) {
        int[] eBin = bin(e);
        int result = 1;
        for (int i = 0; i < eBin.length; i++) {
            if (eBin[i] == 1) {
                result = ((exp(result, 2)%n) * (eBin[i]*m))% n;
            }else{
                result = (exp(result, 2) % n);
            }
        }
        return result;
    }
}
