package speedyshopper;

import java.util.ArrayList;

public class spaceTile {
    String type = "floorTile";  //Need to make these all the sametypes.
    ArrayList<String> itemList = new ArrayList<>();

    public void printOut(){
        System.out.println("Fuck you");
    }
    public void addItem(String itemName){
        itemList.add(itemName);
    }
    public void setType(String newType){
        this.type = newType;
    }
    public String getType(){
        return type;
    }
}