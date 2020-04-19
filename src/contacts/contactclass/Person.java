package contacts.contactclass;

import java.time.*;
import java.util.*;

public class Person extends Contact{
    private String  surname;
    private LocalDate birthDate;
    private char gender;
    private ArrayList<String> personFields;
 
    public Person() {
        personFields = new ArrayList<>();
        personFields.add("name");
        personFields.add("surname");
        personFields.add("birth");
        personFields.add("gender");
        personFields.add("number");
    };
 
    public String getSurname() {
        return this.surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }
 
    public LocalDate getBirthDate() {
        return this.birthDate;
    }
 
    public void setBirthDate(String birthDate) {
        this.birthDate = checkBirthDate(birthDate);
    }
 
    private LocalDate checkBirthDate(String birthDate) {
        if (birthDate.length() != 0) {
            return LocalDate.parse(birthDate);
        }
        System.out.println("Bad birth date!");
        return null;
    }
 
    private boolean hasBirthDate() {
        return this.birthDate != null;
    }
 
    public char getGender() {
        return this.gender;
    }
 
    public void setGender(String gender) {
        this.gender = checkGender(gender);
    }
 
    private char checkGender(String gender) {
        if (gender.length() != 0 && (gender.charAt(0) == 'M' || gender.charAt(0) == 'F')) {
            return gender.charAt(0);
        }
        System.out.println("Bad gender!");
        return 0;
    }
 
    private boolean hasGender() {
        return gender != 0;
    }
 
    @Override
    public void build(Scanner scanner) {
        System.out.println("Enter the name:");
        setName(scanner.nextLine());
        System.out.println("Enter the surname:");
        setSurname(scanner.nextLine());
        System.out.println("Enter the birth date:");
        setBirthDate(scanner.nextLine());
        System.out.println("Enter the gender (M, F):");
        setGender(scanner.nextLine());
        System.out.println("Enter the number:");
        setPhoneNumber(scanner.nextLine());
    }
 
    /** previous version
    @Override
    public void edit(Scanner scan) {
        System.out.println("Select a field (name, surname, birth, gender, number):");
        String field = scan.nextLine();
        switch (field) {
            case "name":
                System.out.println("Enter name:");
                field = scan.nextLine();
                setName(field);
                break;
            case "surname":
                System.out.println("Enter surname:");
                field = scan.nextLine();
                setSurname(field);
                break;
            case "birth":
                System.out.println("Enter birth:");
                field = scan.nextLine();
                setBirthDate(field);
                break;
            case "gender":
                System.out.println("Enter gender:");
                field = scan.nextLine();
                setGender(field);
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
        return (ArrayList<String>) personFields.clone();
    }
 
    @Override
    public boolean setFieldVal(String field, Object val) {
        String value = (String)val;
        switch (field) {
            case "name":
                setName(value);
                break;
            case "surname":
                setSurname(value);
                break;
            case "birth":
                setBirthDate(value);
                break;
            case "gender":
                setGender(value);
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
            case "surname":
                return getSurname();
            case "birth":
                return hasBirthDate() ? getBirthDate().toString() : null;
            case "gender":
                if (hasGender()) {
                    return Character.toString(getGender());
                } else {
                    return null;
                }
            case "number":
                return hasNumber() ? getPhoneNumber() : null;
            default:
                break;
        }
        return null;
    }
 
    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "Surname: " + this.surname + "\n"
                + "Birth date: " + (hasBirthDate() ? this.birthDate : "[no data]") + "\n"
                + "Gender: " + (hasGender() ? this.gender : "[no data]") + "\n"
                + "Number: " + (hasNumber() ? this.phoneNumber : "[no number]") + "\n"
                + super.toString();
    }
}
