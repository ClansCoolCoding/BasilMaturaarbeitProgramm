import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {
        //https://tools.justus-d.de/rsa/ wurde zur Zahlengenerierung benutzt, denn gerade der Modulokey muss grösser als
        //1024 sein um den Key für DES
        Person[] personen = new Person[5];
        personen[0] = new Person("Person 1", 101, 173, 2279);
        personen[1] = new Person("Person 2", 121, 241, 1147);
        personen[2] = new Person("Person 3", 93, 2789, 3127);
        personen[3] = new Person("Person 4", 107, 683, 1333);
        personen[4] = new Person("Person 5", 113, 425, 2813);

        //Die Message wird grob umrissen was gemacht werden muss
        int mtd = 6;
        boolean DESCodation = true;
        EncryptionStatus status = EncryptionStatus.DECRYPT;
        int person = 1;
        Message msg = new Message("Hallo", "1101100111100010001100000011000011100111010000011001");
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