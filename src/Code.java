public class Code {
    private Key1 k1;
    private Key2 k2;
    private Key3 k3;
    private Key4 k4;
    private Key5 k5;
    private Key6 k6;

    public Code(){
        this.k1 = new Key1();
        this.k2 = new Key2();
        this.k3 = new Key3();
        this.k4 = new Key4();
        this.k5 = new Key5();
        this.k6 = new Key6();
    }

    public String encode(Person p, int mtd, Message msg){
        String s = "";

        int RSACryptedMtd = p.RSAmtd(mtd, EncryptionStatus.ENCRYPT);

        switch (mtd){
            case 1: k1.encode(msg);break;
            case 2: k2.encode(msg);break;
            case 3: k3.encode(msg);break;
            case 4: k4.encode(msg);break;
            case 5: k5.encode(msg);break;
            case 6: k6.encode(msg);break;
        }

        s = mtdExchange(msg.getChiffrentext(), RSACryptedMtd);

        return s;
    }

    public void decode(Person p, Message msg){

        int method = Integer.parseInt(mtdExchange(msg.getChiffrentext(), 0), 2);

        int RSAmethod = p.RSAmtd(method, EncryptionStatus.DECRYPT);

        switch(RSAmethod){
            case 1: k1.decode(msg);break;
            case 2: k2.decode(msg);break;
            case 3: k3.decode(msg);break;
            case 4: k4.decode(msg);break;
            case 5: k5.decode(msg);break;
            case 6: k6.decode(msg);break;
        }
    }

    //Am Schluss noch Methode als 8-Bit Nummer anhängen oder ablesen
    private String mtdExchange(String msg, int mtd){
        String s = msg;

        if(mtd == 0){
            s = msg.substring(msg.length()-8);
        }else{
            String bmtd = Integer.toBinaryString(mtd);
            if(bmtd.length() < 8){
                int bmtdlen = bmtd.length();
                for(int i = 0; i < 8-bmtdlen; i++){
                    s += "0";
                }
            }
            s += bmtd;
        };

        return s;
    }
}