package speedyshopper;
//Code by Claymazing

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.ArrayList;

public class SpeedyShopper {

    public static Map currentMap;
    public static ArrayList<String> shoppingList;
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Is this a new table or an existing table? (n = new, e = existing)"); //set the input to change to caps

        String newOrExisting = scan.nextLine();
        while (!newOrExisting.equals("e") && !newOrExisting.equals("n")) { //makes sure the new or existing is a 'e' or 'n' 
            System.out.println("Please input either 'e' or 'n'");
            newOrExisting = scan.nextLine();
        }

        switch (newOrExisting) {    //Is the user using an existing map or creating a new one?

            case "e":
                currentMap = useExistingMap();
                break;
            case "n":
                currentMap = createNewMap();
                break;
            default:
                System.out.println("ERROR, THE INPUT IS NEITHER 'e' OR 'n'");//Should never reach this statement.
                break;
        }

        insertShoppingList();
        //navigateStore();

    }

    public static Map useExistingMap() throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        File file = null;
        String fileName = null;
        //Retrieves the file
        while (exit == false) {
            System.out.println("What is the name of your file?");
            fileName = scan.nextLine();
            if (new File(fileName + ".txt").isFile()) {
                file = new File(fileName + ".txt");
                exit = true;
            } else {
                System.out.println("Could not find file");
            }
        }
        //Reads the X and Y dimensions of the existing map and creates a new map
        Scanner fileScan = new Scanner(file);
        String dimensions = fileScan.nextLine();
        String[] dimensionParts = dimensions.split(",");
        int x = Integer.parseInt(dimensionParts[0]);
        int y = Integer.parseInt(dimensionParts[1]);
        currentMap = new Map(fileName, x, y);

        currentMap.createBlankMap();

        for (int i = 0; i < y; i++) {      //for each tile (aka for each line) (Probably don't need to have x and y positions in the file?
            for (int j = 0; j < x; j++) {//DOUBLE CHECK THAT THE X AND Y DONT NEED TO BE SWAPPED, MAY WANT THEM IN THE TEXT FILE JUST FOR MY OWN SAKE OF TESTING.  MAKE SURE YOU ARE PUTTING CORRECT ITEMS IN THE CORRECT PLACES IN THE FILE!!!
                String fullLine = fileScan.nextLine();
                String[] typeAndItems = fullLine.split(":");
                if (typeAndItems.length > 1) {      //If there are groceries in that tile
                    String[] groceryItems = typeAndItems[1].split(",");
                    for (String grocery : groceryItems) {      //adds the grocery items to the new list.
                        currentMap.floor.get(i).get(j).addItem(grocery);
                    }
                }
                currentMap.floor.get(i).get(j).setType(typeAndItems[0]); //Changes the type
                if(typeAndItems[0].equals("entrance")){
                    currentMap.entranceX = j;
                    currentMap.entranceY = i;
                }
                if(typeAndItems[0].equals("exit")){
                    currentMap.exitX = j;
                    currentMap.exitY = i;
            }
            }
        }
        currentMap.createSaveFile();

        return currentMap;
    }

    /*
    Creates a new list (2d array of spacetile objects) which is blank (no out of bound spaces) and unfilled(no grocery items)
     */
    public static Map createNewMap() throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        System.out.println("What would you like to call this map?");
        String newMapName = scan.nextLine();
        System.out.println("What is the first dimension of the store (x-axis)");
        int x = scan.nextInt();
        System.out.println("What is the second dimesnion of the store (y-axis)");
        int y = scan.nextInt();
        currentMap = new Map(newMapName, x, y);
        currentMap.createBlankMap();
        currentMap.createSaveFile();
        currentMap.addEntranceAndExit();
        currentMap.addOutOfBounds();
        currentMap.addGroceries();
        currentMap.createSaveFile();
        return currentMap;
    }

    public static void insertShoppingList() {
        Scanner scan = new Scanner(System.in);
        shoppingList = new ArrayList();
        shoppingList.add(currentMap.entranceX + " " + currentMap.entranceY + " entrance");
        boolean exit = false;
        while (exit == false) {
            System.out.println("Please insert the name of your grocery item (enter q when finished)");
            String input = scan.next();
            if (input.equals("q")) {
                exit = true;

            } else {
                ArrayList<String> optionsList = new ArrayList();
                for (int i = 0; i < currentMap.y; i++) {
                    for (int j = 0; j < currentMap.x; j++) {
                        for (String item : currentMap.floor.get(i).get(j).itemList) {
                            if (item.contains(input)) {     //need a case for doesn't contain
                                optionsList.add(j + " " + i + " " + item);
                            }
                        }
                    }
                }
                if(optionsList.isEmpty()){
                    System.out.println("That item could not be found");
                }
                else if (optionsList.size() > 1) { //If there is more than one option containing that word, put out a selection THIS NEEDS TO SET ALL THINGS TO CAPS OR IGNORESCASE FC
                    System.out.println("Which of these options are you looking for (insert the number):");
                    for (String option : optionsList) {
                        String[] optionParts = option.split(" ");
                        System.out.println(optionsList.indexOf(option) + ". " + optionParts[2]);
                    }
                    int choice = scan.nextInt();
                    shoppingList.add(optionsList.get(choice));

                } else if(optionsList.size() == 1){
                    System.out.println("Is this the correct item?(y/n)");
                    String item = optionsList.get(0);
                    String[] itemParts = item.split(" ");
                    System.out.println(itemParts[2]);
                    String correctItem = scan.next();
                    if (correctItem.equals("y")) {
                        shoppingList.add(optionsList.get(0)); //otherwise just add the only option to the list
                    }
                }

            }
        }
        System.out.println("Your shopping list is: " + shoppingList.toString());
        navigateStore();

    }
    
    public static void navigateStore(){
        Navigator navigate = new Navigator(currentMap);
        navigate.getPath(shoppingList);
        navigate.createMovements();
    }
}
