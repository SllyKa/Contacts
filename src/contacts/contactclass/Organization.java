package contacts.contactclass;

import java.time.*;
import java.util.*;

public class Organization extends Contact {
    private String address;
    private ArrayList<String> organizationFields;
 
    public Organization() {
        organizationFields = new ArrayList<>();
        organizationFields.add("name");
        organizationFields.add("address");
        organizationFields.add("number");
    };
 
    public String getAddress() {
        return this.address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
    @Override
    public void build(Scanner scanner) {
        System.out.println("Enter the organization name:");
        setName(scanner.nextLine());
        System.out.println("Enter the address:");
        setAddress(scanner.nextLine());
        System.out.println("Enter the number:");
        setPhoneNumber(scanner.nextLine());
    }
 
 
    /** previous version
     @Override
    public void edit(Scanner scan) {
        System.out.println("Select a field (name, address, number):");
        String field = scan.nextLine();
        switch (field) {
            case "name":
                System.out.println("Enter name:");
                field = scan.nextLine();
                setName(field);
                break;
            case "address":
                System.out.println("Enter address:");
                field = scan.nextLine();
                setAddress(field);
                break;
            case "number":
                System.out.println("Enter number: ");
                field = scan.nextLine();
                setPhoneNumber(field);
                break;
            default:
                System.out.println("No such field!");
                return;
        }
        System.out.println("The record updated!");
        setEditTime(LocalDateTime.now());
    }
 
 **/
 
    @Override
    public ArrayList<String> getFields() {
        return (ArrayList<String>) organizationFields.clone();
    }
 
    @Override
    public boolean setFieldVal(String field, Object val) {
        String value = (String)val;
        switch (field) {
            case "name":
                setName(value);
                break;
            case "address":
                setAddress(value);
                break;
            case "number":
                setPhoneNumber(value);
                break;
            default:
                return false;
        }
        setEditTime(LocalDateTime.now());
        return true;
    }
 
    @Override
    public String getFieldVal(String field) {
        switch (field) {
            case "name":
                return getName();
            case "address":
                return getAddress();
            case "number":
                return hasNumber() ? getPhoneNumber() : null;
            default:
                break;
        }
        return null;
    }
 
    @Override
    public String toString() {
        return "Organization name: " + this.name + "\n"
                + "Address: " + this.address + "\n"
                + "Number: " + (hasNumber() ? this.phoneNumber : "[no number]") + "\n"
                + super.toString();
    }
}
