public class Code {
    private Key1 k1;
    private Key2 k2;
    private Key3 k3;
    private Key4 k4;
    private Key5 k5;
    private Key6 k6;
    private Message msg;

    public Code(Message msg){
        this.k1 = new Key1(msg);
        this.k2 = new Key2(msg);
        this.k3 = new Key3(msg);
        this.k4 = new Key4(msg);
        this.k5 = new Key5(msg);
        this.k6 = new Key6(msg);
        this.msg = msg;
    }

    public String encode(Person p, int mtd){
        String s = "";

        int RSACryptedMtd = Person.RSAmtd(p, mtd, EncryptionStatus.ENCRYPT);

        switch (mtd){
            case 1: s = mtdExchange(k1.encode(), RSACryptedMtd);break;
            case 2: s = mtdExchange(k2.encode(), RSACryptedMtd);break;
            case 3: s = mtdExchange(k3.encode(), RSACryptedMtd);break;
            case 4: s = mtdExchange(k4.encode(), RSACryptedMtd);break;
            case 5: s = mtdExchange(k5.encode(), RSACryptedMtd);break;
            case 6: s = mtdExchange(k6.encode(), RSACryptedMtd);break;
        }
        /*
        if(mtd == 1){s = mtdExchange(k1.encode(), RSACryptedMtd);}
        if(mtd == 2){s = mtdExchange(k2.encode(), RSACryptedMtd);}
        if(mtd == 3){s = mtdExchange(k3.encode(), RSACryptedMtd);}
        if(mtd == 4){s = mtdExchange(k4.encode(), RSACryptedMtd);}
        if(mtd == 5){s = mtdExchange(k5.encode(), RSACryptedMtd);}
        if(mtd == 6){s = mtdExchange(k6.encode(), RSACryptedMtd);}
        */
        return s;
    }

    public String decode(Person p){
        String s = "";

        int method = Integer.parseInt(mtdExchange(msg.getChiffrentext(), 0), 2);

        int RSAmethod = Person.RSAmtd(p, method, EncryptionStatus.DECRYPT);

        switch(RSAmethod){
            case 1: s = k1.decode();break;
            case 2: s = k2.decode();break;
            case 3: s = k3.decode();break;
            case 4: s = k4.decode();break;
            case 5: s = k5.decode();break;
            case 6: s = k6.decode();break;
        }
        /*
        if(RSAmethod == 1){s = k1.decode();}
        if(RSAmethod == 2){s = k2.decode();}
        if(RSAmethod == 3){s = k3.decode();}
        if(RSAmethod == 4){s = k4.decode();}
        if(RSAmethod == 5){s = k5.decode();}
        if(RSAmethod == 6){s = k6.decode();}
        */
        return s;
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