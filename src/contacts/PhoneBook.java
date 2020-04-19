package contacts;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import contacts.contactclass.*;
import contacts.contactclass.factory.*;

class PhoneBook implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private File serialFile;
    private int                 recordCount;
    private ArrayList<Contact>   records;
    transient public Scanner scan;
 
    public PhoneBook(File file) {
        recordCount = 0;
        records = new ArrayList<>();
        this.scan = new Scanner(System.in);
        this.serialFile = file;
    }
 
    public void add() {
        System.out.println("Enter the type (person, organization):");
        String type = scan.nextLine();
        ContactFactory contactFactory = new ContactStore();
        Contact contact = contactFactory.orderContact(type, scan);
        if (contact != null) {
            records.add(contact);
            recordCount++;
            try {
                SerializationUnit.serialize(this, serialFile);
            } catch (Exception e){
                System.out.println("Add serialize exception");
                System.out.println(e);
            }
        }
    }
 
    public void count() {
        System.out.println("The Phone Book has " + recordCount + " records.");
    }
 
    public void menuRun() {
        String  item;
        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            item = scan.nextLine();
            switch (item) {
                case "add":
                    add();
                    break;
                case "list":
                    new List(scan).exec();
                    break;
                case "search":
                    new Search(scan).exec();
                    break;
                case "count":
                    count();
                    break;
                case "exit":
                    scan.close();
                    return;
                default:
                    System.out.println("Wrong choice.");
                    break;
            }
            System.out.println();
        }
    }
 
    private abstract class MenuExec implements Serializable{
        protected boolean isContinue = false;
        protected String data = null;
        protected Scanner scan;
        protected String menuName;
        protected ArrayList<String> menuFields;
 
        MenuExec(Scanner scan) {
            this.scan = scan;
            menuFields = new ArrayList<>();
        }
 
        public void exec () {
            if (recordsIsEmpty()) {
                return;
            }
            showInfo();
            do {
                showInput();
                readData();
                run();
            } while (isContinue);
        }
 
        protected boolean recordsIsEmpty() {
            if (records.isEmpty()) {
                System.out.println("No records!");
                return true;
            } else {
                return false;
            }
        }
 
        protected void showInfo() {
 
        }
 
        protected void readData() {
            this.data = scan.nextLine();
        }
 
        protected abstract void run();
 
 
        protected void showInput() {
            StringBuilder menu = new StringBuilder("[");
            menu.append(this.menuName).append("] ").append("Enter action (");
            for (String it : menuFields) {
                menu.append(it).append(", ");
            }
            menu.deleteCharAt(menu.length() - 1).deleteCharAt(menu.length() - 1);
            menu.append("):");
            System.out.println(menu.toString());
        }
 
        protected void info(ArrayList<Contact> contacts) {
            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = contacts.get(i);
                if (contact.getClass() == Person.class) {
                    Person person = (Person) contact;
                    System.out.println(i + 1 + ". " + person.getName() + " " + person.getSurname());
                }else {
                    System.out.println(i + 1 + ". " + contact.getName());
                }
            }
        }
 
        protected boolean isNum(String line) {
            try {
                Integer.parseInt(line);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
 
        protected int recordIndxCheck(String num, ArrayList<Contact> contacts) {
            int indx = Integer.parseInt(num);
            if (indx > contacts.size() || indx <= 0) {
                System.out.println("No such record!");
                return -1;
            } else {
                return indx;
            }
        }
    }
 
    private class List extends MenuExec {
 
        List(Scanner scan) {
            super(scan);
            this.menuName = "list";
            this.menuFields.add("[number]");
            this.menuFields.add("back");
        }
 
        @Override
        protected void showInfo() {
            info(records);
            System.out.println();
        }
 
        @Override
        protected void run() {
            if (isNum(data)) {
                int indx;
                if ((indx = recordIndxCheck(data, records)) >= 0) {
                    new Record(scan, records.get(indx -1)).exec();
                    isContinue = false;
                } else {
                    isContinue = true;
                }
            } else {
                if (data.equals("back")) {
                    isContinue = false;
                } else {
                    isContinue = true;
                }
            }
        }
    }
 
    private class Record extends MenuExec {
        Contact contact;
        Record(Scanner scan, Contact contact) {
            super(scan);
            this.menuName = "record";
            this.menuFields.add("edit");
            this.menuFields.add("delete");
            this.menuFields.add("menu");
            this.contact = contact;
        }
 
        @Override
        protected void showInfo() {
            System.out.println(contact);
            //System.out.println();
        }
 
        @Override
        protected void run() {
            switch (data) {
                case "edit":
                    new Edit(scan, contact).exec();
                    break;
                case "delete":
                    new Delete(scan, contact).exec();
                case "menu":
                    isContinue = false;
                    return;
                default:
                    break;
            }
            isContinue = true;
        }
    }
 
    private class Search extends MenuExec {
 
        ArrayList<Contact> sContacts;
        Search(Scanner scan) {
            super(scan);
            this.menuName = "search";
            this.menuFields.add("[number]");
            this.menuFields.add("back");
            this.menuFields.add("again");
        }
 
        @Override
        protected void showInput() {
            sContacts = searchEngine();
            System.out.println("Found " + sContacts.size() + " results:");
            info(sContacts);
            System.out.println();
            super.showInput();
        }
 
        @Override
        protected void run() {
            if (isNum(data)) {
                int indx;
                if ((indx = recordIndxCheck(data, sContacts)) >= 0) {
                    new Record(scan, sContacts.get(indx - 1)).exec();
                    isContinue = false;
                } else {
                    isContinue = true;
                }
            } else {
                if (data.equals("back")) {
                    isContinue = false;
                } else {
                    isContinue = true;
                }
            }
        }
 
        private ArrayList<Contact> searchEngine() {
            ArrayList<Contact> sContacts = new ArrayList<>();
            System.out.println("Enter search query: ");
            Pattern pattern = Pattern.compile(scan.nextLine(), Pattern.CASE_INSENSITIVE);
            for (Contact contact : records) {
                for (String field : contact.getFields()) {
                    if (field == null) {
                        continue;
                    }
                    String match = contact.getFieldVal(field);
                    if (match == null) {
                        continue;
                    }
                    Matcher matcher = pattern.matcher(match);
                    if (matcher.find()) {
                        sContacts.add(contact);
                        break;
                    }
                }
            }
            return sContacts;
        }
    }
 
    private class Edit extends MenuExec {
        Contact contact;
 
        Edit(Scanner scan, Contact contact) {
            super(scan);
            this.contact = contact;
        }
 
        @Override
        protected void showInfo() {
 
        }
 
        @Override
        protected void showInput() {
            StringBuilder sb = new StringBuilder("Select a field (");
            for (String str : contact.getFields()) {
                sb.append(str);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
            sb.append("):");
            System.out.println(sb.toString());
        }
 
        @Override
        protected void run() {
            System.out.println("Enter " + data + ":");
            String editValue = scan.nextLine();
            if (contact.setFieldVal(data, editValue)) {
                try {
                    SerializationUnit.serialize(PhoneBook.this, PhoneBook.this.serialFile);
                } catch (Exception e) {
                    System.out.println("Exception in edit!");
                }
                System.out.println("Saved");
            }
            System.out.println(contact);
            System.out.println();
        }
    }
 
    private class Delete extends MenuExec {
        Contact contact;
 
        Delete(Scanner scan, Contact contact) {
            super(scan);
            this.contact = contact;
        }
 
        @Override
        protected void showInfo() {
 
        }
 
        @Override
        protected void showInput() {
 
        }
 
        @Override
        protected void readData() {
 
        }
 
        @Override
        protected void run() {
            records.remove(contact);
            System.out.println("The record removed!");
            recordCount--;
            try {
                SerializationUnit.serialize(PhoneBook.this, PhoneBook.this.serialFile);
            } catch (Exception e) {
                System.out.println("Exception in delete!");
            }
        }
    }
}
