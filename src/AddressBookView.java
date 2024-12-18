import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddressBookView extends JFrame {

    private AddressBook addressBook;
    private JList<BuddyInfo> buddyList;

    public AddressBookView(){
        super("AddressBook view");

        this.addressBook = new AddressBook();
        this.buddyList = new JList<>(addressBook); // JList displays the toString() output of each object in its list
        // to string output is always in CLassName@HashCode

        // Layout Setup
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(buddyList)); // renders BuddyInfo objects using toString

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // AddressBook Menu
        JMenu addressBookMenu = new JMenu("AddressBook");
        JMenuItem newAdressBookItem = new JMenuItem("New AddressBook");
        JMenuItem printAddressBookItem = new JMenuItem("Print AddressBook");
        JMenuItem saveAdressBook = new JMenuItem("Save");
        JMenuItem importAddressBook = new JMenuItem("Import");
        JMenuItem serialize = new JMenuItem("Serialize");
        JMenuItem deserialize = new JMenuItem("De-serialize");
        addressBookMenu.add(newAdressBookItem);
        addressBookMenu.add(printAddressBookItem);
        addressBookMenu.add(saveAdressBook);
        addressBookMenu.add(importAddressBook);
        addressBookMenu.add(serialize);
        addressBookMenu.add(deserialize);
        menuBar.add(addressBookMenu);

        // BuddyInfo Menu
        JMenu buddyInfo = new JMenu("BuddyInfo");
        JMenuItem addBuddy = new JMenuItem("Add Buddy");
        JMenuItem removeBuddy = new JMenuItem("Remove Buddy");
        buddyInfo.add(addBuddy);
        buddyInfo.add(removeBuddy);
        menuBar.add(buddyInfo);

        this.setJMenuBar(menuBar);

        // Add ActionListeners
        newAdressBookItem.addActionListener(e -> addressBook.clear());
        printAddressBookItem.addActionListener(e -> addressBook.printBuddies());
        addBuddy.addActionListener(e -> addBuddy());
        removeBuddy.addActionListener(e -> removeBuddy());
        saveAdressBook.addActionListener(e -> export());
        importAddressBook.addActionListener(e -> importBook());
        serialize.addActionListener(e -> serializeAdressBook());
        deserialize.addActionListener(e -> deserializeAdressBook());

        // Frame
        this.setSize(300,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void removeBuddy(){

        BuddyInfo selectedBuddy = buddyList.getSelectedValue();
        if (selectedBuddy != null){
            addressBook.removeBuddy(selectedBuddy);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a Buddy to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void addBuddy(){

        String name = JOptionPane.showInputDialog(this, "Enter Buddy Name: ");
        String address = JOptionPane.showInputDialog(this, "Enter Buddy Adress: ");

        if (name != null){
            String phoneStr = JOptionPane.showInputDialog(this, "Enter your phone number: ");
            try{
                int phone = Integer.parseInt(phoneStr);
                BuddyInfo buddy = new BuddyInfo(name, address, phone);
                addressBook.addBuddy(buddy);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Invalid phone number","Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void export(){
        String fileName = JOptionPane.showInputDialog(this, "Enter the name of file to store your AddressBook: ");
        if (fileName != null){
            try {
                addressBook.save(fileName);
                JOptionPane.showMessageDialog(this, "AddressBook successfully saved to: " + fileName);
            }catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving AdressBook: " + e.getMessage());
            }
        }
    }

    public void importBook(){
        String filename = JOptionPane.showInputDialog(this, "Enter file name to be imported: ");
        if (filename != null){
            try{ addressBook.importAdressBook(filename);
                JOptionPane.showMessageDialog(this, "AdressBook imported from: " + filename);
            }catch (IOException e){
                JOptionPane.showMessageDialog(this, "Error importing AddressBook " + e.getMessage());
            }
        }
    }

    private void serializeAdressBook(){
        String filename = JOptionPane.showInputDialog(this, "Enter file name to store serialized adressbook: ");
        if (filename != null){
            try {
                addressBook.serialize(filename);
                JOptionPane.showMessageDialog(this, "Successfully seriazlized addressBook to file: " + filename);
            } catch (IOException e){
                JOptionPane.showMessageDialog(this, "Error serializing object to file " + e.getMessage());
            }
        }
    }

    private void deserializeAdressBook() {
        String filename = JOptionPane.showInputDialog(this, "Enter the filename to deserialize the AddressBook from: ");
        if (filename != null) {
            try {
                AddressBook deserializedAddressBook = AddressBook.deserialize(filename);
                addressBook.clear();
                for (int i = 0; i < deserializedAddressBook.size(); i++) {
                    addressBook.addBuddy(deserializedAddressBook.getElementAt(i));
                }
                File temp = new File(filename);
                if (temp.delete()) {
                    System.out.println("Successfully deleted serialized file: " + filename);
                }
                JOptionPane.showMessageDialog(this, "AddressBook deserialized from: " + filename);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error deserializing AddressBook: " + e.getMessage());
            }
        }
    }



    public static void main(String[] args){
        new AddressBookView();
    }


}
