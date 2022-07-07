import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {

        int mtd = 6;
        Message msg = new Message("Hello There!", "00010100110000101110110001011100110011110010000000101100110010000110010101100110011000100110000100000110");
        Code coder = new Code(msg);
        EncryptionStatus e = EncryptionStatus.DECRYPT;

        Person[] personen = new Person[5];
        personen[0] = new Person("Basil", 7, 19, 21);
        personen[1] = new Person("Philip", 5, 17, 21);
        personen[2] = new Person("Raphael", 7, 8, 15);
        personen[3] = new Person("Florian", 5, 11, 14);
        personen[4] = new Person("Marco", 3, 7, 10);

        switch(e){
            case ENCRYPT: System.out.println(coder.encode(personen[0], mtd)); break;
            case DECRYPT: System.out.println(coder.decode(personen[0])); break;
        }

        /*
        if(code){
            System.out.println(coder.encode(personen[0], mtd));
        }else{
            System.out.println(coder.decode(personen[0]));
        }

        Person p1 = new Person("Basil", 157, 17, 2773);
        int rsa = 481;
        int rsas = (int)RSATools.RSAmtd(p1, rsa, 0);
        System.out.println(RSATools.RSAmtd(p1, rsa, 0));
        System.out.println(RSATools.RSAmtd(p1, rsas, 1));*/


    }
}