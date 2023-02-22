package contactsmanagerapp;

public class Contact {

    private String name;

    private int phoneNumber;

    public Contact() {
    }
    public Contact(String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public static Contact fromFileString(String fileString) {
        String[] contactInfo = fileString.split("\\|");
        Contact contact = new Contact();
        contact.setName(contactInfo[0]);
        contact.setPhoneNumber(Integer.parseInt(contactInfo[1]));
        return contact;
    }

    @Override
    public String toString() {
        return "name: " + name + ", phone number: " + phoneNumber;
    }

    // accessors

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
