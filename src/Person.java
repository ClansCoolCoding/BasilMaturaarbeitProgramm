public class Person {

    private int publicKey;
    private int privateKey;
    private int moduloKey;
    private String name;

    public Person(String name, int publicKey, int privateKey, int moduloKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.moduloKey = moduloKey;
        this.name = name;
    }

    public int getPublicKey() {
        return publicKey;
    }

    public int getPrivateKey() {
        return privateKey;
    }

    public int getModuloKey() {
        return moduloKey;
    }

    public String getName() {
        return name;
    }

    public static int RSAmtd(Person p, int mtd, EncryptionStatus status){
        int e = 0;
        switch(status){
            case ENCRYPT: e = p.getPublicKey(); break;
            case DECRYPT: e = p.getPrivateKey(); break;
        }
        int m = p.getModuloKey();

        int RSAmtd = RSATools.squareAndMultiply(mtd, e, m);

        return RSAmtd;
        /*
        if(status == EncryptionStatus.ENCRYPT){
            int e = p.getPublicKey();
            int m = p.getModuloKey();

            int RSAmtd = RSATools.squareAndMultiply(mtd, e, m);

            return RSAmtd;

        }else{
            int d = p.getPrivateKey();
            int m = p.getModuloKey();

            int RSAmtd = RSATools.squareAndMultiply(mtd, d, m);

            return RSAmtd;
        }*/
    }
}
