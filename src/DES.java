import java.util.Random;

public class DES {

    public DES(){}

    public String DEScoder(Person p, Message msg, EncryptionStatus encryptionStatus){
        String result = "";
        switch (encryptionStatus){
            case ENCRYPT -> {
                encrypt(p, msg);
            }
            case DECRYPT -> {
                decrypt(p, msg);
            }
        }

        return result;
    }

    //Verschlüssleung
    private void encrypt(Person p, Message message){
        String result = "";

        //zufälligen Schlüssle generieren
        Random random = new Random();
        int keiInt = random.nextInt(1022)+1;
        String kei = Integer.toBinaryString(keiInt);
        if(kei.length() < 10){
            int bmtdlen = kei.length();
            for(int i = 0; i < 10-bmtdlen; i++){
                kei += "0";
            }
        }

        //DES
        //kei muss eine 8-Bit langer String sein
        char[] characters = split(message.getKlartext());
        int[] asciiCode = ascii(characters);
        String[] binaryNumbers = bin(asciiCode);
        String binaryNumbersComplete = code(binaryNumbers);
        String[] pakete = paeckchen(binaryNumbersComplete, 8);

        for(int i = 0; i < pakete.length; i++) {
            result += DES(pakete[i], kei, EncryptionStatus.ENCRYPT);
        }

        //RSA
        int RSACryptedKey = Person.RSAmtd(p, keiInt, EncryptionStatus.ENCRYPT);
        String RSACryptedKeyBin = Integer.toBinaryString(RSACryptedKey);
        if(RSACryptedKeyBin.length() < 12){
            int bkeilen = RSACryptedKeyBin.length();
            for(int i = 0; i < 12-bkeilen; i++){
                RSACryptedKeyBin = "0" + RSACryptedKeyBin;
            }
        }
        result += RSACryptedKeyBin;

        message.setChiffrentext(result);
    }

    //Entschlüsselung
    private void decrypt(Person p, Message message){
        String result = "";
        String chiffrentext = message.getChiffrentext();

        //RSA-Schlüsselauslesung
        String RSAKeyCryptedBin = chiffrentext.substring(chiffrentext.length()-12);
        message.setChiffrentext(chiffrentext.substring(0, chiffrentext.length()-12));
        int RSAKeiInt = Integer.parseInt(RSAKeyCryptedBin,2);
        int keiInt = Person.RSAmtd(p, RSAKeiInt, EncryptionStatus.DECRYPT);
        String kei = Integer.toBinaryString(keiInt);
        if(kei.length() < 10){
            int bmtdlen = kei.length();
            for(int i = 0; i < 10-bmtdlen; i++){
                kei += "0";
            }
        }

        //DES
        //kei muss eine 8-Bit langer String sein
        chiffrentext = message.getChiffrentext();
        String[] pakete = paeckchen(chiffrentext, 8);

        for(int i = 0; i < pakete.length; i++) {
            result += DES(pakete[i], kei, EncryptionStatus.DECRYPT);
        }

        String[] decryptedPakete = paeckchen(result, 8);
        int[] ascii = ints(decryptedPakete);
        char[] chars = aChar(ascii);

        result = "";

        for(int i = 0; i < chars.length; i++){
            result += ""+chars[i];
        }

        message.setKlartext(result);
    }

    //DES-Mechanismus
    private String DES(String msg, String kei, EncryptionStatus encryptionStatus) {
        //Variabeln
        int[] msgAr = stringToArray(msg);
        int[] keiAr = stringToArray(kei);
        int[] msgCod = new int[8];

        //Schlüssel
        int[][] k = keiGeneration(keiAr);

        //Simple-DES
        //Verschlüsseln
        switch (encryptionStatus){
            case ENCRYPT -> {
            int[] msgArR = permutation(IP, msgAr);
            int[] round1 = DESRound(msgArR, k[0]);
            int[] swap = permutation(SW, round1);
            int[] round2 = DESRound(swap, k[1]);
            msgCod = permutation(IP_1, round2);
            }
            case DECRYPT -> {
            int[] msgArR = permutation(IP, msgAr);
            int[] round1 = DESRound(msgArR, k[1]);
            int[] swap = permutation(SW, round1);
            int[] round2 = DESRound(swap, k[0]);
            msgCod = permutation(IP_1, round2);
            }
        }

        String result = arrayToString(msgCod);
        return result;
    }

    //DES Runde
    private int[] DESRound(int[] msg, int[] kei) {
        int[][] p = arSplit(msg);
        int[] p2 = p[1];
        int[] step1 = permutation(EP, p2);
        int[] step2 = xor(step1, kei);
        int[][] step3 = arSplit(step2);
        int[][] step3a = arSplit(step3[0]);
        int[][] step3b = arSplit(step3[1]);
        int[] step4a = S0[binInt(step3a[0])][binInt(step3a[1])];
        int[] step4b = S1[binInt(step3b[0])][binInt(step3b[1])];
        int[] step4 = arMerge(step4a, step4b);
        int[] step5 = permutation(P4, step4);
        int[] step6 = xor(step5, p[0]);
        int[] step7 = arMerge(step6, p[1]);
        return step7;
    }

    //Schlüsselgenerierung
    private int[][] keiGeneration(int[] keiAr){
        int[][] k = new int[2][8];

        k[0] = permutation(P8, permutation(R1, permutation(P10, keiAr)));
        k[1] = permutation(P8, permutation(R2, permutation(R1, permutation(P10, keiAr))));

        return k;
    }

    //aus String ein IntegerArray machen
    private int[] stringToArray(String s) {
        int[] result = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            result[i] = Integer.parseInt(""+s.charAt(i));
        }

        return result;
    }

    //aus IntegerArray String machen
    private String arrayToString(int[] ar) {
        String res = "";

        for (int i = 0; i < ar.length; i++) {
            res += ""+ar[i];
        }

        return res;
    }


    //Permutationenen
    private int[] permutation(int[] pattern, int[] template) {
        int[] result = new int[pattern.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = template[pattern[i]];
        }
        return result;
    }

    //XOR
    private int[] xor(int[] p, int[] k) {
        int[] result = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            if (p[i] == k[i]) {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }
        return result;
    }

    //Exponierung
    private int exponent(int b, int e) {
        int res = 1;
        for (int i = 0; i < e; i++) {
            res = res*b;
        }
        return res;
    }

    //binärarray zu integer
    private int binInt(int[] bin) {
        int res = 0;
        for (int i = 0; i < bin.length; i++) {
            int zwRes = bin[bin.length-1-i]*exponent(2, i);
            res = res+zwRes;
        }
        return res;
    }

    //Binärarray in 2 Teile Teilen
    private int[][] arSplit(int[] ar) {
        int[][] res = new int[2][ar.length / 2];

        for (int i = 0; i < ar.length; i++) {
            if (i < ar.length/2) {
                res[0][i] = ar[i];
            } else {
                res[1][i-ar.length/2] = ar[i];
            }
        }
        return res;
    }

    //2 Binärarrays zu einem machen
    private int[] arMerge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];

        for (int i = 0; i < res.length; i++) {
            if (i < a.length) {
                res[i] = a[i];
            } else {
                res[i] = b[i-a.length];
            }
        }

        return res;
    }


    //Die nachfolgenden Funktionen wurden so aus der abstract Class Key kopiert denn sie werden hier auch verwendet
    //---------------------------------------------------------------------------
    //in lettersArray überschreiben
    private char[] split(String msg){
        char[] characters = new char[msg.length()];
        for(int i = 0; i < msg.length(); i++){
            characters[i] = msg.charAt(i);
        }
        return characters;
    }

    //übersetzung in ASCII-Code (ISO 8859 Erweiterung 1)
    private int[] ascii(char[] chars){
        int[] asciiNumbers = new int[chars.length];
        for(int i = 0; i < chars.length; i++){
            asciiNumbers[i] = (int)chars[i];
        }
        return asciiNumbers;
    }

    //umwandlung Int in binär
    private String[] bin(int[] numbers){
        String[] result = new String[numbers.length];

        for(int i = 0; i < numbers.length; i++){
            result[i] = Integer.toBinaryString(numbers[i]);
            if(result[i].length() < 8){
                String a = "";
                String s = result[i];
                for(int j = 0; j < 8- result[i].length(); j++){
                    a += "0";
                }
                result[i] = a + s;
            }
        }

        return result;
    }

    //ein langen String machen
    private String code(String[] bin){
        String s = "";
        for(int i = 0; i < bin.length; i++){
            s += bin[i];
        }
        return s;
    }

    //päckchen machen aus Binärstream
    private String[] paeckchen(String binstring, int laenge){
        //Anzahl Päckchen
        int anzPack;
        if(binstring.length()%laenge == 0){
            anzPack = binstring.length()/laenge;
        }else{
            anzPack = (binstring.length()/laenge)+1;
        }

        String[] result = new String[anzPack];

        for(int i = 0; i < anzPack; i++){
            result[i] = "";
        }

        for(int i = 0; i < binstring.length(); i++){
            result[i / laenge] += binstring.charAt(i);
        }

        if(result[anzPack-1].length() < laenge){
            int diff = laenge-result[anzPack-1].length();
            for(int i = 0; i < diff; i++){
                result[anzPack-1] += 0;
            }
        }

        return result;
    }

    //umwandlung binär in Int
    private int[] ints(String[] bin){
        int[] numbrs = new int[bin.length];

        for(int i = 0; i < bin.length; i++){
            numbrs[i] = Integer.parseInt(bin[i], 2);
        }

        return numbrs;
    }

    //umwandlung ASCII in Charackter
    private char[] aChar(int[] ascii){
        char[] chars = new char[ascii.length];
        for(int i = 0; i < ascii.length; i++){
            chars[i] = (char) ascii[i];
        }
        return chars;
    }
    //----------------------------------------------------------------------------

    //Permutationsvariabeln
    private final int[] IP = new int[]{1, 5, 2, 0, 3, 7, 4, 6};
    private final int[] IP_1 = new int[]{3, 0, 2, 4, 6, 1, 7, 5};
    private final int[] SW = new int[]{4, 5, 6, 7, 0, 1, 2, 3};
    private final int[] EP = new int[]{3, 0, 1, 2, 1, 2, 3, 0};
    private final int[] P4 = new int[]{1, 3, 2, 0};
    private final int[] P10 = new int[]{2, 4, 1, 6, 3, 9, 0, 8, 7, 5};
    private final int[] P8 = new int[]{5, 2, 6, 3, 7, 4, 9, 8};
    private final int[] R1 = new int[]{1, 2, 3, 4, 0, 6, 7, 8, 9, 5};
    private final int[] R2 = new int[]{2, 3, 4, 0, 1, 7, 8, 9, 5, 6};

    //Ablesetabellen
    private final int[][][] S0 = new int[][][]{{{0, 1}, {0, 0}, {1, 1}, {1, 0}}
            , {{1, 1}, {1, 0}, {0, 1}, {0, 0}}
            , {{0, 0}, {1, 0}, {0, 1}, {1, 1}}
            , {{1, 1}, {0, 1}, {1, 1}, {1, 0}}};

    private final int[][][] S1 = new int[][][]{{{0, 0}, {0, 1}, {1, 0}, {1, 1}}
            , {{1, 0}, {0, 0}, {0, 1}, {1, 1}}
            , {{1, 1}, {0, 0}, {0, 1}, {0, 0}}
            , {{1, 0}, {0, 1}, {0, 0}, {1, 1}}};
}
