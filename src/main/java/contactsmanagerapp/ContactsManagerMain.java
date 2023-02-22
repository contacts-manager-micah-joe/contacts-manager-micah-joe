package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsManagerMain {
    public static void main(String[] args) {

        if (Files.notExists(Path.of("/Users/joeyvandzura/IdeaProjects/contacts-manager-micah-joe/src/main/java/contactsmanagerapp/contacts.txt"))) {
            try {
                Files.createFile(Path.of("/Users/joeyvandzura/IdeaProjects/contacts-manager-micah-joe/src/main/java/contactsmanagerapp/contacts.txt"));
            } catch (Exception e) {
                System.out.println("Could not create contacts.txt.");
            }
        } else {
            System.out.println("contacts.txt file already exists.");
        }

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> contact = new ArrayList<>();

        System.out.println("Please enter your name: ");
        String user = scanner.nextLine();
        System.out.println("Hello, " + user + "!");

        System.out.println("Would you like to enter a new contact? (Yes/No)");
        String newContact = scanner.nextLine();

        if (newContact.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.println("Please enter the name of the new contact or to exit phone book enter 'Finished': ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("finished")) {
                    break;
                }
                //below is the phone #
                System.out.println("Please enter the phone number of the new contact: ");
                String phoneNumber = scanner.nextLine();
                contact.add(name + " - " + phoneNumber);
                System.out.println(name + " has been added to your phone book!");
                //The equalsIgnoreCase() method is used to compare two strings
                // for equality while ignoring their case.
                //--prompt will continue after this line--

            }
            System.out.println("Goodbye!");
        }
    }
}
