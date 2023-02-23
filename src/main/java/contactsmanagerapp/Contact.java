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
        return "NAME: " + name + ", PHONE NUMBER: " + this.phoneNumberStringFormat();
    }



    public String phoneNumberStringFormat() {
        String phoneNumberString = String.valueOf(phoneNumber);
        return phoneNumberString.substring(0, 3) + "-" + phoneNumberString.substring(3, 6) + "-" + phoneNumberString.substring(6, 10);
    }

    public String phoneNumberStringFormat(int phoneNumber) {
        String phoneNumberString = String.valueOf(phoneNumber);
        return phoneNumberString.substring(0, 3) + "-" + phoneNumberString.substring(3, 6) + "-" + phoneNumberString.substring(6, 10);
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
