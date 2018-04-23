/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speedyshopper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ClaysPC
 */
public class Map {

    String mapName;
    int x;
    int y;
    int entranceX;
    int entranceY;
    int exitX;
    int exitY;
    ArrayList<ArrayList<spaceTile>> floor = new ArrayList<>();

    public Map(String mapName, int x, int y) {
        this.mapName = mapName;
        this.x = x;
        this.y = y;
    }

    public void createBlankMap() {
        for (int i = 0; i < y; i++) {        //Fill each list item with a spaceTile with default values. (Create a border)
            ArrayList<spaceTile> innerList = new ArrayList<>();
            floor.add(innerList);
            for (int j = 0; j < x; j++) {
                spaceTile blankTile = new spaceTile();
                innerList.add(blankTile);
            }
        }
    }

    public void addEntranceAndExit() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Where is the entrance of this map? X then Y seperated by comma");
        String entranceParts[] = scan.nextLine().split(",");
        floor.get(Integer.parseInt(entranceParts[1])).get(Integer.parseInt(entranceParts[0])).setType("entrance");
        System.out.println("Setting entrance to " + entranceParts[0] + " " + entranceParts[1]);
        entranceX = Integer.parseInt(entranceParts[0]);
        entranceY = Integer.parseInt(entranceParts[1]);
        System.out.println("Where is the checkout of this map? X then Y sperated by comma");
        String exitParts[] = scan.nextLine().split(",");
        floor.get(Integer.parseInt(exitParts[1])).get(Integer.parseInt(exitParts[0])).setType("exit");
    }

    public void addOutOfBounds() {//Create locations of shelves and other inacessable locations.
        printOut();
        Scanner scan = new Scanner(System.in);
        System.out.println("Insert both endpoints of your out of bounds (Format| X1,Y1:X2,Y2) type 'q' when finished");
        String input = scan.nextLine();
        if(!input.equals("q")){
        String endPointParts[] = input.split(":");
        if (endPointParts.length == 2) {
            String point1 = endPointParts[0];
            String point2 = endPointParts[1];
            String point1Parts[] = point1.split(",");
            String point2Parts[] = point2.split(",");
            if (point1Parts.length == 2 && point2Parts.length == 2) {
                int point1X = Integer.parseInt(point1Parts[0]);
                int point1Y = Integer.parseInt(point1Parts[1]);
                int point2X = Integer.parseInt(point2Parts[0]);
                int point2Y = Integer.parseInt(point2Parts[1]);
                if(point1X == point2X && point1Y == point2Y){
                    floor.get(point1X).get(point1Y).setType("outOfBounds");
                }else if(point1X == point2X){
                    if(point1Y < point2Y){
                        for(int i = 0; i <= point2Y-point1Y; i ++){
                            System.out.println("Case A");
                            floor.get(point1Y + i).get(point1X).setType("outOfBounds"); //Y is supposed to change not X
                        }
                        printOut();
                        addOutOfBounds();
                    }
                    if(point1Y > point2Y){
                        for(int i = 0; i <= point1Y-point2Y; i ++){
                            System.out.println("Case B");
                            floor.get(point1Y-i).get(point1X).setType("outOfBounds");
                        }
                        printOut();
                        addOutOfBounds();
                    }
                }else if(point1Y == point2Y){
                    if(point1X < point2X){
                        System.out.println("Case C");
                        for(int i = 0; i <= point2X-point1X; i ++){
                            
                            floor.get(point1Y).get(point1X+i).setType("outOfBounds");
                        }
                        printOut();
                        addOutOfBounds();
                    }
                    if(point1X > point2X){
                        System.out.println("Case D");
                        for(int i = 0; i <= point1X-point2X; i ++){
                            
                            floor.get(point1Y).get(point1X-i).setType("outOfBounds");
                        }
                        printOut();
                        addOutOfBounds();
                    }
                    
                }else{
                    System.out.println("Only coordinates which create a straight line can be accepted");
                    addOutOfBounds();
                }
                
            } else {
                System.out.println("This format is not recognized");
                addOutOfBounds();
            }

        } else {
            System.out.println("This format is not recognized");
            addOutOfBounds();
        }
        }
//        System.out.println("Where are the out of bounds on this floor? x then y, seperate by comma. (Type q when finished)");
//        while (settingOutOfBounds == true) {
//            printOut();
//            String input = scan.next();
//            if (input.equals("q")) {
//                settingOutOfBounds = false;
//            } else {
//                String[] parts = input.split(",");
//                int part1 = Integer.parseInt(parts[0]);
//                int part2 = Integer.parseInt(parts[1]);
//                floor.get(part2).get(part1).setType("outOfBounds");
//            }
//        }
    }

    public void addGroceries() {
        Scanner scan = new Scanner(System.in);
        boolean addingGroceries = true;
        System.out.println("Insert X position and Y position seperated by a comma. (Type q when finished)");
        while (addingGroceries == true) {
            String input = scan.next();
            if (input.equals("q")) {
                addingGroceries = false;
            } else {
                String[] parts = input.split(",");
                int part1 = Integer.parseInt(parts[0]);
                int part2 = Integer.parseInt(parts[1]);
                System.out.println("Type all groceries seperated by a comma");
                scan.nextLine();
                String groceryInput = scan.nextLine();  //This is necessary to eat the newline.
                String[] groceries = groceryInput.split(",");
                for (String grocery : groceries) {
                    System.out.println("Adding item: " + grocery);
                    floor.get(part2).get(part1).addItem(grocery);//add every typed grocery item to the list.

                }
            }
        }
    }

    public void createSaveFile() throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(mapName + ".txt", "UTF-8");      //This throws the imporoper encoding exception. May need to change that from UTF
        writer.println(x + "," + y);//first line is the x and y length
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {

                writer.print(floor.get(i).get(j).getType() + ":");
                for (String listItem : floor.get(i).get(j).itemList) {  //For every item in the list of that spaceTile.  may need to be get y then get x.  Check this out.
                    writer.print(listItem + ",");
                }
                writer.println();
            }
        }
        writer.close();
    }

    public void printOut() {
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < x; i++) {
            System.out.print(" " + i);
        }
        for (int i = 0; i < y; i++) {
            System.out.println();
            System.out.print(i + " ");
            for (int j = 0; j < x; j++) {
                System.out.print(floor.get(i).get(j).type.charAt(0) + " ");      //Prints out the first letter of the type, may want to change this if type names get out of hand
            }
        }
        System.out.println(" ");
    }

}
