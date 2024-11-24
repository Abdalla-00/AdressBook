import java.io.Serializable;

public class BuddyInfo implements Serializable {
    private String name;
    private int phone;
    private String address;
    public BuddyInfo(String name, String address, Integer phone) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public static BuddyInfo importBuddyInfo(String data){

        String[] parts = data.split("#");

        if(parts.length == 3){
            String name = parts[0];
            String address = parts[1];
            Integer phone = Integer.parseInt(parts[2]);
            return new BuddyInfo(name, address, phone);
        } return null;
//        else{
//            throw new IllegalArgumentException("Invalid data format for BuddyInfor: " + data);
//        }
    }

    public String getName() {
        return name;
    }

    public Integer getPhone() {
        return phone;
    }

    // To represent defualt object
    @Override
    public String toString(){
        return name + "#" + address + "#" +  phone;
    }

}
