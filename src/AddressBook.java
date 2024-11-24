import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook extends DefaultListModel<BuddyInfo> {

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            this.addElement(buddy);
            System.out.println(buddy.getName() + " | " + buddy.getPhone());
        }
    }

    public void removeBuddy(BuddyInfo buddy) {
        if (this.contains(buddy) && buddy != null) {
            this.removeElement(buddy);
            System.out.println(buddy.getName() + " removed from the address book.");
        } else {
            System.out.println("Buddy not found in the address book.");
        }
    }

    public void printBuddies() {
        if (this.isEmpty()) {
            System.out.println("Address book is empty.");
        } else {

            for (int i = 0; i < this.size(); i++){
                BuddyInfo buddy = this.getElementAt(i);
                System.out.println(buddy.getName() + " - " + buddy.getPhone());
            }
        }
    }

    public void save(String filename) throws IOException {

        try (FileWriter fileWriter = new FileWriter(filename);
             BufferedWriter writer = new BufferedWriter(fileWriter);) {

            // Serialize and write each BuddyInfo into object
            for(int i = 0; i < this.size(); i++){
                writer.write(this.getElementAt(i).toString());
                writer.newLine();
            }
            //ostream.close(); // optional since it closes after the try statement completes
            System.out.println("AddressBook saved to: " + filename);
        } catch (IOException e){
            System.err.println("Error saving adress book: " + e.getMessage());
            throw e;
        }
    }

    public void importAdressBook(String filename) throws IOException{
        try (FileReader fileReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fileReader);){

            this.clear(); // clear addressbook data

            String line;

            while((line = reader.readLine()) != null){
                BuddyInfo buddy = BuddyInfo.importBuddyInfo(line);
                this.addBuddy(buddy);
            }
            System.out.println("Successfully imported AdressBook from file: " + filename);
        } catch (IOException e){
            System.err.println("Error importing file: " + e.getMessage());
            throw e;
        }
    }
}