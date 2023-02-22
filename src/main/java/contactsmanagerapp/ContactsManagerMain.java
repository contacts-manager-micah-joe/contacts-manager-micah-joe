package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ContactsManagerMain {

    private static final Path contactsFile = Path.of("./src/main/java/contactsmanagerapp/contacts.txt");

    private static ArrayList<Contact> contactList = new ArrayList<>();

    private static ArrayList<String> contactFileList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (Files.notExists(contactsFile)) {
            try {
                Files.createFile(contactsFile);
            } catch (Exception e) {
                System.out.println("Could not create contacts.txt.");
            }
        }

        updateListOfContacts();
        displayContacts();
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
                break;
            case 4:
                break;
            case 5:
                break;
            default:
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
        System.out.println("Displaying contact objects: " + contactList);

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
    public static void addContact(){
        System.out.println("Name of contact");
        String newContactName = scanner.nextLine();

        System.out.println("Please add phone number: ");
        int newContactNumber = scanner.nextInt();

        try {
            Files.write(
                    contactsFile,
                    Arrays.asList("\n" + newContactName + "|" + newContactNumber),
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//e.printStackTrace(); tracks errors
