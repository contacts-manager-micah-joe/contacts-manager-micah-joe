package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsManagerMain {

    private static final Path contactsFile = Path.of("./src/main/java/contactsmanagerapp/contacts.txt");
    private static final ArrayList<Contact> contactList = new ArrayList<>();
    private static ArrayList<String> contactFileList;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        startProgram();
        displayContacts();
        boolean runProgram = true;

        while (runProgram) {
            contactMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine();
//to filter through list
            switch (userChoice) {
                case 1 -> displayContacts();
                case 2 -> addContact();
                case 3 -> searchContact();
                case 4 -> deleteContact();
                case 5 -> runProgram = false;
                default -> {
                }
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
                System.out.println("Could not create contacts.txt.");
            }
        }
    }

    public static void initializeContactList() {
        try {
            contactFileList = new ArrayList<>(Files.readAllLines(contactsFile));
            if (contactFileList.size() == 0) {
                System.out.println("There are no contacts currently");
            } else {
                System.out.println("List of Contacts:");
                for (String element : contactFileList) {
                    if (element.equals("")) {
                        continue;
                    }
                    contactList.add(Contact.fromFileString(element));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayContacts() {
        System.out.println();
        int i = 1;
        for (Contact contact : contactList) {
            System.out.println(i + ": " + contact);
            i++;
        }
    }

    //menu
    public static void contactMenu() {
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
        System.out.print("\nName of contact: ");
        String newContactName = scanner.nextLine();

        System.out.print("\nPlease add phone number: ");
        int newContactNumber = scanner.nextInt();

        Contact newContact = new Contact(newContactName, newContactNumber);
        contactList.add(newContact);
        contactFileList.add(newContact.getName() + "|" + newContact.getPhoneNumber());
    }

    public static void searchContact() {
        System.out.println("\nWould you like to search by name or number? (\"name\" or \"number\" \n");
        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("name")) {
            System.out.print("\nPlease enter the name of the contact: ");
            userInput = scanner.nextLine();
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getName().equalsIgnoreCase(userInput)) {
                    isFound = true;
                    System.out.println("\n" + contact.getName() + "'s phone number is " + contact.getPhoneNumber());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("\nCould not find contact.");
            }

        } else if (userInput.equalsIgnoreCase("number")) {
            System.out.print("\nPlease enter the number of the contact: ");
            userInput = scanner.nextLine();
            int userNumInput = Integer.parseInt(userInput);
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getPhoneNumber() == userNumInput) {
                    isFound = true;
                    System.out.println("\n" + contact.getPhoneNumber() + " belongs to " + contact.getName());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("\nCould not find contact.");
            }
        } else {
            System.out.println("\nInvalid input");
        }
    }

    public static void deleteContact() {
        System.out.print("\nPlease enter the name of the contact: ");
        String userInput = scanner.nextLine();
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
    }

    public static void submitContactList() {
        try {
            Files.write(contactsFile, contactFileList , StandardOpenOption.WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//e.printStackTrace(); tracks errors
