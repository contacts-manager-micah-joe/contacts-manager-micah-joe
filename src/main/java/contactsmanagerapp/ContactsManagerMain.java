package contactsmanagerapp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsManagerMain {

    private static final Path contactsFile = Path.of("./src/main/java/contactsmanagerapp/contacts.txt");

    private static ArrayList<Contact> contactList = new ArrayList<>();

    private static ArrayList<String> contactFileList;

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


        }

        public static void updateListOfContacts() {
            try {
                contactFileList = new ArrayList<>(Files.readAllLines(contactsFile));
                if (contactFileList.size() == 0) {
                    System.out.println("There are no contacts currently");
                } else {
                    System.out.println("List of Contacts:");
                    for (String element : contactFileList) {
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



    }
