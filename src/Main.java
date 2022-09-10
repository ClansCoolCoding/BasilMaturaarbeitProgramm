import java.util.Scanner;

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

        //UserInupt wird genommen
        UserInput userInput = new UserInput();
        userInput.initializeMessageCoder();

        //es wird aus userInput ausgelesen
        DESCodation Codation = userInput.getDESCodation();
        EncryptionStatus status = userInput.getEncryptionStation();
        int person = userInput.getPerson();
        int mtd = userInput.getMtd();
        Message msg = userInput.getMsg();

        //Ver-/Entschlüsselungsorganisation
        switch (Codation){
            case SIMPLE_DES_CODATION -> {
                DES des = new DES();
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
            }
            case OWN_DES_CODATION -> {
                Code coder = new Code();
                switch (status) {
                    case ENCRYPT -> {
                        coder.encode(personen[person], mtd, msg);
                        String outputEncrypt = msg.getChiffrentext();
                        System.out.println(outputEncrypt);
                    }
                    case DECRYPT -> {
                        coder.decode(personen[person], msg);
                        String outputDecrypt = msg.getKlartext();
                        System.out.println(outputDecrypt);
                    }
                }
            }
        }
    }


}