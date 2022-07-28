import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {
        //https://tools.justus-d.de/rsa/ wurde zur Zahlengenerierung benutzt, denn gerade der Modulokey muss grösser als
        //1024 sein um den Key für DES richtig zu Entschlüsseln
        Person[] personen = new Person[5];
        personen[0] = new Person("Person 1", 101, 173, 2279);
        personen[1] = new Person("Person 2", 121, 241, 1147);
        personen[2] = new Person("Person 3", 93, 2789, 3127);
        personen[3] = new Person("Person 4", 107, 683, 1333);
        personen[4] = new Person("Person 5", 113, 425, 2183);

        //Die Message wird festgelegt was wie gemacht werden muss
        int mtd = 6;
        boolean DESCodation = true;
        EncryptionStatus status = EncryptionStatus.DECRYPT;
        int person = 4;
        Message msg = new Message("Hello There*", "111111100000000010110111101101110111111101001000111001100101001000000000000000110000000001101101011000001011");
        Code coder = new Code(msg);
        DES des = new DES();


        //Ver-/Entschlüsselungsorganisation
        if(DESCodation){
            switch (status){
                case ENCRYPT -> {
                    des.DEScoder(personen[person], msg, EncryptionStatus.ENCRYPT);
                    String outputEncrypt = msg.getChiffrentext();
                    System.out.println(outputEncrypt);
                }
                case DECRYPT -> {
                    des.DEScoder(personen[person], msg, EncryptionStatus.DECRYPT);
                    String outputDecrypt = msg.getKlartext();
                    System.out.println(outputDecrypt);
                }
            }
        }else{
            switch (status) {
                case ENCRYPT -> System.out.println(coder.encode(personen[person], mtd));
                case DECRYPT -> System.out.println(coder.decode(personen[person]));
            }
        }
    }
}