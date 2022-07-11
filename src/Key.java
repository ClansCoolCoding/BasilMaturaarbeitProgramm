abstract class Key {

    protected String klartext;
    protected String chiffrentext;

    public Key(Message msg) {
        this.klartext = msg.getKlartext();
        this.chiffrentext = msg.getChiffrentext();
    }

    //Verschlüsseln
    protected String encode(){
        String code = "";
        char[] characters = split(this.klartext);
        int[] asciiCode = ascii(characters);
        String[] binaryNumbers = bin(asciiCode);
        String binaryNumbersComplete = code(binaryNumbers);

        String[] pakete = encodeMTD(binaryNumbersComplete);

        code = code(pakete);

        return code;
    }

    //entschlüsseln
    protected String decode(){
        String decoded = "";
        String chiffrenText = this.chiffrentext;

        String[] pakete = decodeMTD(chiffrenText);

        String binString = code(pakete);
        String[] asciiStr = paeckchen(binString, 8);
        int[] ascii = ints(asciiStr);
        char[] chars = aChar(ascii);

        for(int i = 0; i < chars.length; i++){
            decoded += ""+chars[i];
        }

        int len = decoded.length();
        return decoded.substring(0, len-1);
    }

    //Verschlüsselungs Methode
    protected abstract String[] encodeMTD(String pakete);

    //Entschlüsselungs Methode
    protected abstract String[] decodeMTD(String pakete);

    //in lettersArray überschreiben
    protected char[] split(String msg){
        char[] characters = new char[msg.length()];
        for(int i = 0; i < msg.length(); i++){
            characters[i] = msg.charAt(i);
        }
        return characters;
    }

    //übersetzung in ASCII-Code (ISO 8859 Erweiterung 1)
    protected int[] ascii(char[] chars){
        int[] asciiNumbers = new int[chars.length];
        for(int i = 0; i < chars.length; i++){
            asciiNumbers[i] = (int)chars[i];
        }
        return asciiNumbers;
    }

    //umwandlung ASCII in Charackter
    protected char[] aChar(int[] ascii){
        char[] chars = new char[ascii.length];
        for(int i = 0; i < ascii.length; i++){
            chars[i] = (char) ascii[i];
        }
        return chars;
    }

    //umwandlung Int in binär
    protected String[] bin(int[] numbers){
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

    //umwandlung binär in Int
    protected int[] ints(String[] bin){
        int[] numbrs = new int[bin.length];

        for(int i = 0; i < bin.length; i++){
            numbrs[i] = Integer.parseInt(bin[i], 2);
        }

        return numbrs;
    }

    //ein langen String machen
    protected String code(String[] bin){
        String s = "";
        for(int i = 0; i < bin.length; i++){
            s += bin[i];
        }
        return s;
    }

    //päckchen machen aus Binärstream
    protected String[] paeckchen(String binstring, int laenge){
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

    //spezifische Päckchen die letzen bits tauschen 0101 -> 1100
    protected String specEndChange(String s){
        String result = "";

        char[] chars = new char[s.length()];

        for(int i = 0; i < s.length(); i++){
            chars[i] = s.charAt(i);
        }

        char temp = chars[0];
        chars[0] = chars[chars.length -1];
        chars[chars.length -1] = temp;

        for(int i = 0; i < chars.length; i++){
            result += chars[i];
        }

        return result;
    }

    //spezifische Päckchen 0 -> 1 und 1 -> 0
    protected String specWarp(String s){
        String result = "";
        int[] changed = new int[s.length()];

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '0'){
                changed[i] = 1;
            }if(s.charAt(i) == '1'){
                changed[i] = 0;
            }

            result += ""+changed[i];
        }

        return result;
    }

    //Zufallszahlgenerierung
    protected int random(int min, int max){
        int result;
        result = min + (int)(Math.random()*(max - min));
        return result;
    }

    //nach spezifischem Muster mischen
    protected String[] shuffleWithPlan(String[] pakete, int[] plan, char restriction){
        String[] result = pakete;

        for(int i = 0; i < result.length; i++){
            String r = "";
            if(pakete[i].charAt(0) != restriction){
                for(int j = 0; j < pakete[i].length(); j++){
                    r += pakete[i].charAt(plan[j]);
                }
            }else{
               r = pakete[i];
            }
            result[i] = r;
        }

        return result;
    }
}