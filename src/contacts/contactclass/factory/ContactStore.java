package contacts.contactclass.factory;

import contacts.contactclass.*;

public class ContactStore extends ContactFactory {
    @Override
    Contact createContact(String type) {
        if ("person".equals(type)) {
            return new Person();
        } else if ("organization".equals(type)) {
            return new Organization();
        } else return null;
    }
}