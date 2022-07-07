import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {

        int mtd = 6;
        Message msg = new Message("Hello There!", "00010100110000101110110001011100110011110010000000101100110010000110010101100110011000100110000100000110");
        Code coder = new Code(msg);
        EncryptionStatus status = EncryptionStatus.DECRYPT;

        Person[] personen = new Person[5];
        personen[0] = new Person("Person 1", 7, 19, 21);
        personen[1] = new Person("Person 2", 5, 17, 21);
        personen[2] = new Person("Person 3", 7, 8, 15);
        personen[3] = new Person("Person 4", 5, 11, 14);
        personen[4] = new Person("Person 5", 3, 7, 10);

        switch (status) {
            case ENCRYPT -> System.out.println(coder.encode(personen[0], mtd));
            case DECRYPT -> System.out.println(coder.decode(personen[0]));
        }

    }
}