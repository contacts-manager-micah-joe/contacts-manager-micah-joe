package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import util.Input;

public class ContactsManagerMain {

    private static final Path contactsFile = Path.of("./src/main/java/contactsmanagerapp/contacts.txt");
    private static final Input input = new Input();
    private static final ArrayList<Contact> contactList = new ArrayList<>();

    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    private static ArrayList<String> contactFileList;

    public static void main(String[] args) {
        startProgram();
        displayContacts();
        boolean runProgram = true;

        while (runProgram) {
            contactMenu();
            int userChoice = input.getInt(1, 5);
//to filter through list
            switch (userChoice) {
                case 1 -> displayContacts();
                case 2 -> addContact();
                case 3 -> searchContact();
                case 4 -> deleteContact();
                case 5 -> runProgram = false;
            }
        }
        submitContactList();
        System.out.println("\n\nGoodbye!");
    }

    public static void startProgram() {
        checkForContactsTextFile();
        initializeContactList();
    }

    public static void checkForContactsTextFile() {
        if (Files.notExists(contactsFile)) {
            try {
                Files.createFile(contactsFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void initializeContactList() {
        try {
            contactFileList = new ArrayList<>(Files.readAllLines(contactsFile));
            if (contactFileList.size() == 0) {
                System.out.println("There are no contacts currently");
            } else {
//                System.out.println(ANSI_BLUE);
                System.out.println(ANSI_YELLOW + "List of Contacts:");
                for (String element : contactFileList) {
                    if (element.equals("")) {
                        continue;
                    }
                    contactList.add(Contact.fromFileString(element));
                }
                System.out.print(ANSI_RESET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayContacts() {
        System.out.println(ANSI_YELLOW);
        System.out.printf("%-20s| %s |%n------------------------------------%n", "Name", "Phone Number");
        for (Contact contact : contactList) {
            System.out.printf("%-20s| %s |%n", contact.getName(), contact.phoneNumberStringFormat());
        }
        System.out.println(ANSI_RESET);
    }

    //menu
    public static void contactMenu() {
        System.out.println();
        System.out.print("""
                1. View contacts.
                2. Add a new contact.
                3. Search a contact by name or number.
                4. Delete an existing contact.
                5. Exit.
                Enter an option (1, 2, 3, 4 or 5):\040""");
    }

    //adds new contacts and phone numbers
    public static void addContact() {
        System.out.println(ANSI_CYAN);

        boolean wouldYouLikeToOverride = false;
        String newContactName = addName();
        while (doesContactExist(newContactName) && !wouldYouLikeToOverride) {
            System.out.println("\n" + newContactName + " already exists.\nWould you like to ovverride this contact's phone number?");

            if (input.yesNo(input.getString())) {
                wouldYouLikeToOverride = true;
                continue;
            }

            newContactName = addName();
        }

        if (doesContactExist(newContactName)) {
            override(newContactName);
            return;
        }

        int newContactNumber = addPhoneNumber();
        while (doesContactExist(newContactNumber)) {
            System.out.println("\n" + newContactNumber + " already exists.");
            newContactNumber = addPhoneNumber();
        }

        Contact newContact = new Contact(newContactName, newContactNumber);
        contactList.add(newContact);
        contactFileList.add(newContact.getName() + "|" + newContact.getPhoneNumber());
        System.out.print("\n" + newContact.getName() + " has been added.");

        System.out.println(ANSI_RESET);
    }

    public static String addName() {
        return input.getString("\nPlease enter the name of contact: ");
    }

    public static int addPhoneNumber() {
        return input.getInt(10, "\nPlease add phone number: ");
    }

    public static boolean doesContactExist(String name) {
        for (Contact contact : contactList) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
//left here
    public static boolean doesContactExist(int phoneNumber) {
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber() == phoneNumber) {
                return true;
            }
        }
        return false;
    }

    public static void override(String existingContactName) {
        int newContactNumber = addPhoneNumber();
        while (doesContactExist(newContactNumber)) {
            System.out.println("\n" + newContactNumber + " already exists.");
            newContactNumber = addPhoneNumber();
        }
        for (Contact contact : contactList) {
            if (contact.getName().equalsIgnoreCase(existingContactName)) {
                System.out.println(contactFileList);
                System.out.println(contactList);
                System.out.println(contactFileList.indexOf(contact.getName() + "|" + contact.getPhoneNumber()));

                contactFileList.set(
                        (contactFileList.indexOf(contact.getName() + "|" + contact.getPhoneNumber())),
                        existingContactName + "|" + newContactNumber
                );

                contact.setPhoneNumber(newContactNumber);
            }
        }

        System.out.println(ANSI_RESET);
    }

    public static void searchContact() {
        System.out.println(ANSI_CYAN);
        String userInput = input.getString("\nWould you like to search by name or number? (\"name\" or \"number\")");

        if (userInput.equalsIgnoreCase("name")) {
            userInput = input.getString("\nPlease enter the name of the contact: ");
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getName().equalsIgnoreCase(userInput)) {
                    isFound = true;
                    System.out.println("\n" + contact.getName() + "'s phone number is " + contact.phoneNumberStringFormat());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("\nCould not find contact.");
            }

        } else if (userInput.equalsIgnoreCase("number")) {
            userInput = input.getString("\nPlease enter the number of the contact: ");
            int userNumInput = Integer.parseInt(userInput);
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getPhoneNumber() == userNumInput) {
                    isFound = true;
                    System.out.println("\n" + contact.phoneNumberStringFormat() + " belongs to " + contact.getName());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("\nCould not find contact.");
            }
        } else {
            System.out.println("\nInvalid input");
        }
        System.out.println(ANSI_RESET);
    }

    public static void deleteContact() {
        System.out.println(ANSI_CYAN);
        String userInput = input.getString("\nPlease enter the name of the contact: ");
        boolean isFound = false;
        for (Contact contact : contactList) {
            if (contact.getName().equalsIgnoreCase(userInput)) {
                isFound = true;
                contactList.remove(contact);
                contactFileList.removeIf(element -> element.equals(contact.getName() + "|" + contact.getPhoneNumber()));
                System.out.println("\n" + contact.getName() + " has been deleted");
                break;
            }
        }
        if (!isFound) {
            System.out.println("\nCould not find contact.");
        }
        System.out.println(ANSI_RESET);
    }

    public static void submitContactList() {
        try {
            Files.deleteIfExists(contactsFile);
            Files.createFile(contactsFile);
            Files.write(contactsFile, contactFileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//e.printStackTrace(); tracks errors