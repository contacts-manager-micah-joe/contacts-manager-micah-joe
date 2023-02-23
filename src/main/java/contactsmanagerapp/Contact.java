package contactsmanagerapp;

public class Contact {

    private String name;

    private int phoneNumber;

    public Contact(String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public static Contact fromFileString(String fileString) {
        String[] contactInfo = fileString.split("\\|");
        return new Contact(contactInfo[0], Integer.parseInt(contactInfo[1]));
    }

    @Override
    public String toString() {
        return "NAME: " + name + ", PHONE NUMBER: " + phoneNumber;
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
