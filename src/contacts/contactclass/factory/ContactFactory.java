package contacts.contactclass.factory;

import java.util.Scanner;
import contacts.contactclass.*;

public abstract class ContactFactory {
 
    abstract Contact createContact(String type);
 
    public Contact orderContact(String type, Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        Contact contact = createContact(type);
        if (contact == null) {
            System.out.println("Cant make such contact.");
            return null;
        }
        contact.build(scanner);
        System.out.println("The record added.");
        return contact;
    }
}
