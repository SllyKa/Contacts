package contacts.contactclass;

import java.time.*;
import java.util.regex.*;
import java.util.*;
import java.io.Serializable;

public abstract class Contact implements Serializable{
    protected String name;
    protected String phoneNumber;
    protected LocalDateTime createdTime;
    protected LocalDateTime editTime;
 
    public Contact() {
        setCreatedTime(LocalDateTime.now());
        setEditTime(this.createdTime);
    }
 
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = checkPhoneNumber(phoneNumber);
    }
 
    public String getName() {
        return this.name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
 
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = checkPhoneNumber(phoneNumber);
    }
 
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }
 
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
 
    public LocalDateTime getEditTime() {
        return this.editTime;
    }
 
    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }
 
     public abstract void build(Scanner scanner);
 
    /** previous version
     public abstract void edit(Scanner scanner);
     **/
 
    public abstract ArrayList<String> getFields();
 
    public abstract boolean setFieldVal(String field, Object val);
 
    public abstract String getFieldVal(String field);
 
    protected String checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("([+]?\\(?[\\w&&[^_]]+\\)?)" +
                "([\\s-]\\(?[\\w&&[^_]]{2,}\\)?)?" +
                "([\\s-][\\w&&[^_]]{2,})*");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            String brackCheckPattern = ".*\\([\\w&&[^_]]*\\)";
            if (matcher.group(2) == null || !(matcher.group(1).matches(brackCheckPattern)
                    && matcher.group(2).matches(brackCheckPattern))) {
                brackCheckPattern = "[+\\s-]?\\([\\w&&[^_]]*" +
                        "|[+\\s-]?[\\w&&[^_]]*\\)";
                if (!matcher.group(1).matches(brackCheckPattern)
                        && (matcher.group(2) == null
                        || !matcher.group(2).matches(brackCheckPattern))) {
                    return phoneNumber;
                }
            }
        }
        System.out.println("Wrong number format!");
        return "";
    }
 
    public boolean hasNumber() {
        return !"".equals(this.phoneNumber);
    }
 
    @Override
    public String toString() {
        return "Time created: " + this.createdTime.withSecond(0).withNano(0) + "\n"
                + "Time last edit: " + this.editTime.withSecond(0).withNano(0);
    }
}
