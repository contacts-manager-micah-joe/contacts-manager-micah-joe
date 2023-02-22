package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsManagerMain {

    private static final Path contactsFile = Path.of("./src/main/java/contactsmanagerapp/contacts.txt");

    private static final ArrayList<Contact> contactList = new ArrayList<>();

    private static ArrayList<String> contactFileList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        startProgram();
        updateListOfContacts();
        displayContacts();
        boolean runProgram = true;

        while (runProgram) {
            contactMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine();
//to filter through list
            switch (userChoice) {
                case 1:
                    displayContacts();
                    break;
                case 2:
                    addContact();
                    updateListOfContacts();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    break;
                case 5:
                    runProgram = false;
                    break;
                default:
            }
        }
        System.out.println("\n\nGoodbye!");
    }

    public static void startProgram() {
        checkForContactsTextFile();

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

    //array list that adds new names and updates contacts
    public static void updateListOfContacts() {
        contactFileList.clear();
        contactList.clear();
        try {
            contactFileList = new ArrayList<>(Files.readAllLines(contactsFile));
            if (contactFileList.size() == 0) {
                System.out.println("There are no contacts currently");
            } else {
                System.out.println("List of Contacts:");
                for (String element : contactFileList) {
                    if (element.equals("")) {
                        //remove line
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
        for (Contact contact : contactList) {
            System.out.println(contact);
        }
    }

    //menu
    public static void contactMenu() {
        System.out.println("""
                1. View contacts.
                2. Add a new contact.
                3. Search a contact by name.
                4. Delete an existing contact.
                5. Exit.
                Enter an option (1, 2, 3, 4 or 5):
                """);


    }

    //adds new contacts and phone numbers
    public static void addContact() {
        System.out.println("Name of contact");
        String newContactName = scanner.nextLine();

        System.out.println("Please add phone number: ");
        int newContactNumber = scanner.nextInt();

        try {
            Files.write(
                    contactsFile,
                    List.of("\n" + newContactName + "|" + newContactNumber),
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchContact() {
        System.out.println("Would you like to search by name or number? (\"name\" or \"number\" ");
        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("name")) {
            System.out.println("Please enter the name of the contact.");
            userInput = scanner.nextLine();
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getName().equalsIgnoreCase(userInput)) {
                    isFound = true;
                    System.out.println(userInput + "'s phone number is " + contact.getPhoneNumber());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Could not find contact.");
            }

        } else if (userInput.equalsIgnoreCase("number")) {
            System.out.println("Please enter the number of the contact.");
            userInput = scanner.nextLine();
            int userNumInput = Integer.parseInt(userInput);
            boolean isFound = false;
            for (Contact contact : contactList) {
                if (contact.getPhoneNumber() == userNumInput) {
                    isFound = true;
                    System.out.println(userNumInput + " belongs to " + contact.getName());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Could not find contact.");
            }
        } else {
            System.out.println("Invalid input");
        }
    }

}

//e.printStackTrace(); tracks errors
