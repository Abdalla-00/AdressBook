import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class AddressBookTest {

    @Before
    public void setUp(){
        File file = new File("exportAddressBookFile.txt");
        if (file.exists() && !file.delete()){
            System.err.println("Error deleting test file");
        }
    }

    @Test
    public void exportAddressBookFile() throws IOException {
        AddressBook book = new AddressBook();
        BuddyInfo b1 = new BuddyInfo("Abdalla", "crescent", 136195010);
        BuddyInfo b2 = new BuddyInfo("John", "Iceland", 555555555);
        BuddyInfo b3 = new BuddyInfo("Malek", "Norway", 666666666);
        book.addBuddy(b1);
        book.addBuddy(b2);
        book.addBuddy(b3);
        // Part a
        book.save("exportAddressBookFile.txt");

        // Part b
        AddressBook importedBook = new AddressBook();
        importedBook.importAdressBook("exportAddressBookFile.txt");

        // Part c
        assertEquals(book.size(), importedBook.size());

        for (int i = 0; i < book.size(); i++) {
            assertEquals(book.getElementAt(i).toString(), importedBook.getElementAt(i).toString());
        }
    }
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException{

        AddressBook originalAddressBook = new AddressBook();
        BuddyInfo buddy1 = new BuddyInfo("Alice", "789 Elm Street", 111222333);
        BuddyInfo buddy2 = new BuddyInfo("Bob", "101 Pine Road", 444555666);

        // Add buddies to the address book
        originalAddressBook.addBuddy(buddy1);
        originalAddressBook.addBuddy(buddy2);

        // Serialize
        String filename = "serializedAddressBook.ser";
        originalAddressBook.serialize(filename);

        //Deserialize
        AddressBook deserializedAddressBook = AddressBook.deserialize(filename);

        // Assert that the original and deserialized address books contain the same data
        assertEquals(originalAddressBook.size(), deserializedAddressBook.size());
        for (int i = 0; i < originalAddressBook.size(); i++) {
            assertEquals(originalAddressBook.getElementAt(i).toString(), deserializedAddressBook.getElementAt(i).toString());
        }

    }

    @After
    public void tearDown(){
        File file = new File("exportAddressBookFile.txt");
        File file1 = new File("serializedAddressBook.ser");
        if (file.exists() && !file.delete() && !file1.delete()){
            System.err.println("Error deleting test file");
        }
    }

}
