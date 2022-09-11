import java.util.Scanner;

public class UserInput {
    private final Scanner userInput = new Scanner(System.in);
    private DESCodation dc;
    private EncryptionStatus es;
    private int person;
    private int mtd;
    private Message msg = new Message("", "");

    public UserInput(){}

    //Nachrichten initialisieren
    public void initializeMessageCoder(){
        //Person
        System.out.println("An wen ist die Nachricht gerichtet? [0 - 4]");
        person = userInput.nextInt();

        //Ver- oder Entschlüsseln
        System.out.println("Soll die Nachricht ver- oder entschlüsselt werden? [encrypt / decrypt]");
        String input_ES = userInput.next()+userInput.nextLine();
        if(input_ES.toLowerCase().equals("encrypt")){
            es = EncryptionStatus.ENCRYPT;
        }else if(input_ES.toLowerCase().equals("decrypt")) {
            es = EncryptionStatus.DECRYPT;
        }

        if(es == EncryptionStatus.ENCRYPT){
            System.out.println("Mit welchem DES-System soll zur Verschlüsselung verwendet werden? [simple / eigen]");
            String input_DC = userInput.next() + userInput.nextLine();
            if(input_DC.toLowerCase().equals("eigen")){
                dc = DESCodation.OWN_DES_CODATION;
                System.out.println("Mit welcher Methode soll die Nachricht verschlüsselt werden? [1 - 6]");
                mtd = userInput.nextInt();
            }else if(input_DC.toLowerCase().equals("simple")){
                dc = DESCodation.SIMPLE_DES_CODATION;
            }

            //Nachricht
            System.out.println("Welche Nachricht soll verschlüsselt werden? ");
            msg.setKlartext(userInput.next() + userInput.nextLine());
        }else{
            System.out.println("Mit welchem DES-System muss zur Entschlüsselung verwendet werden? [Simple / Eigen]");
            String input_DC = userInput.next() + userInput.nextLine();
            if(input_DC.toLowerCase().equals("eigen")){
                dc = DESCodation.OWN_DES_CODATION;
            }else if(input_DC.toLowerCase().equals("simple")){
                dc = DESCodation.SIMPLE_DES_CODATION;
            }

            //Nachricht
            System.out.println("Welche Nachricht soll entschlüsselt werden? ");
            msg.setChiffrentext(userInput.next() + userInput.nextLine());
        }
    }

    public DESCodation getDESCodation() {
        return dc;
    }

    public EncryptionStatus getEncryptionStation() {
        return es;
    }

    public int getPerson() {
        return person;
    }

    public int getMtd() {
        return mtd;
    }

    public Message getMsg() {
        return msg;
    }
}
