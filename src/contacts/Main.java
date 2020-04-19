package contacts;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;
import java.time.*;
import java.io.*;
 
public class Main {
    public static void main(String[] args) {
        File file = null;
        if (args.length != 0) {
            file = new File(args[0]);
        }
        PhoneBook phonebook;
        if (file != null) {
            System.out.println("open " + args[0]);
            System.out.println();
        }
        if (file == null || !file.exists()) {
            phonebook = new PhoneBook(file);
        } else {
            try {
                phonebook = (PhoneBook) SerializationUnit.deserialize(file);
                phonebook.scan = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("Exception!");
                System.out.println(e.getMessage());
                return;
            }
        }
        phonebook.menuRun();
    }
}
