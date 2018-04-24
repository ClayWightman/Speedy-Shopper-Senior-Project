package speedyshopper;

import java.util.ArrayList;
import javax.swing.*;

public class Navigator {

    String[][] movements;
    Map currentMap;
    int x;
    int y;
    ArrayList<String> realList = new ArrayList();

    public Navigator(Map currentMap) {
        this.currentMap = currentMap;
        this.x = currentMap.x;
        this.y = currentMap.y;
        movements = new String[currentMap.y][currentMap.x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (currentMap.floor.get(i).get(j).type.equals("outOfBounds")) {
                    movements[i][j] = "outOfBounds";
                } else if (currentMap.floor.get(i).get(j).type.equals("floorTile")) {
                    movements[i][j] = "floorTile";
                } else if (currentMap.floor.get(i).get(j).type.equals("exit")) {
                    movements[i][j] = "exit";
                } else if (currentMap.floor.get(i).get(j).type.equals("entrance")) {
                    movements[i][j] = "entrance";
                }
            }
        }
    }

    public void getPath(ArrayList<String> shoppingList) {
        int bestIndex = -1;
        int bestMoveCount = 1000;
        for (int i = 0; i < shoppingList.size() - 1; i++) {
            String point1 = shoppingList.get(0);
            String point2 = shoppingList.get(i + 1);
            String parts1[] = point1.split(" ");
            String parts2[] = point2.split(" ");
            int point1X = Integer.parseInt(parts1[0]);
            int point1Y = Integer.parseInt(parts1[1]);
            int point2X = Integer.parseInt(parts2[0]);
            int point2Y = Integer.parseInt(parts2[1]);
            if (move("Right", 0, point1X, point2X, point1Y, point2Y, false) < bestMoveCount) {
                bestIndex = i + 1;
                bestMoveCount = move("Right", 0, point1X, point2X, point1Y, point2Y, false);
            }
            if (move("Left", 0, point1X, point2X, point1Y, point2Y, false) < bestMoveCount) {

                bestIndex = i + 1;
                bestMoveCount = move("Left", 0, point1X, point2X, point1Y, point2Y, false);
            }
            if (move("Down", 0, point1X, point2X, point1Y, point2Y, false) < bestMoveCount) {

                bestIndex = i + 1;
                bestMoveCount = move("Down", 0, point1X, point2X, point1Y, point2Y, false);
            }
            if (move("Up", 0, point1X, point2X, point1Y, point2Y, false) < bestMoveCount) {

                bestIndex = i + 1;
                bestMoveCount = move("Up", 0, point1X, point2X, point1Y, point2Y, false);
            }
        }
        realList.add(shoppingList.get(0));
        String move = shoppingList.get(bestIndex);
        shoppingList.remove(bestIndex);
        shoppingList.remove(0);
        shoppingList.add(0, move);
        if (shoppingList.size() > 1) {
            getPath(shoppingList);
        } else {
            realList.add(shoppingList.get(0));
            realList.add(currentMap.exitX + " " + currentMap.exitY + " exit");
        }

    }

    public int move(String direction, int count, int x1, int x2, int y1, int y2, boolean editMovements) {

        if (editMovements == true) {    //marks all positions as long as they are not entrance and exit.
            if (!movements[y1][x1].equals("exit") && !movements[y1][x1].equals("entrance")) {
                if (movements[y1][x1].equals("1") || movements[y1][x1].equals("2") || movements[y1][x1].equals("3") || movements[y1][x1].equals("4")) {
                    int holder = Integer.parseInt(movements[y1][x1]);
                    holder++;
                    movements[y1][x1] = Integer.toString(holder);
                } else {
                    movements[y1][x1] = "1";
                }
            }

        }
        if (x1 == x2 && y1 == y2) {
            return count;
        }
        if (direction.equals("Right")) {

            if (x1 == currentMap.x || currentMap.floor.get(y1).get(x1 + 1).type.equals("outOfBounds") || (x1 >= x2 && nextMovePossible("Right", x1, x2, y1, y2))) {
                if (y1 < y2) {

                    return move("Up", count + 1, x1, x2, y1 + 1, y2, editMovements);
                }
                if (y1 > y2) {

                    return move("Down", count + 1, x1, x2, y1 - 1, y2, editMovements);
                }
                if (y1 == y2) {
                    if (y1 >= y / 2) {
                        return move("Up", count + 1, x1, x2, y1 + 1, y2, editMovements);
                    } else {
                        return move("Down", count + 1, x1, x2, y1 - 1, y2, editMovements);
                    }
                }
            } else {
                return move("Right", count + 1, x1 + 1, x2, y1, y2, editMovements);
            }
        }

        if (direction.equals("Left")) {
            if (x1 == 0 || currentMap.floor.get(y1).get(x1 - 1).type.equals("outOfBounds") || (x1 <= x2 && nextMovePossible("Left", x1, x2, y1, y2))) {

                if (y1 < y2) {
                    return move("Up", count + 1, x1, x2, y1 + 1, y2, editMovements);
                }
                if (y1 > y2) {
                    return move("Down", count + 1, x1, x2, y1 - 1, y2, editMovements);
                }
                if (y1 == y2) {
                    if (y1 >= y / 2) {
                        return move("Up", count + 1, x1, x2, y1 + 1, y2, editMovements);
                    } else {
                        return move("Down", count + 1, x1, x2, y1 - 1, y2, editMovements);
                    }
                }
            } else {
                return move("Left", count + 1, x1 - 1, x2, y1, y2, editMovements);
            }
        }

        if (direction.equals("Up")) {
            if (y1 == currentMap.y || currentMap.floor.get(y1 + 1).get(x1).type.equals("outOfBounds") || (y1 >= y2 && nextMovePossible("Up", x1, x2, y1, y2))) {
                if (x1 < x2) {
                    return move("Right", count + 1, x1 + 1, x2, y1, y2, editMovements);
                }
                if (x1 > x2) {
                    return move("Left", count + 1, x1 - 1, x2, y1, y2, editMovements);
                }
                if (x1 == x2) {
                    if (x1 >= x / 2) {
                        return move("Right", count + 1, x1 + 1, x2, y1, y2, editMovements);
                    } else {
                        return move("Left", count + 1, x1 - 1, x2, y1, y2, editMovements);
                    }
                }
            } else {
                return move("Up", count + 1, x1, x2, y1 + 1, y2, editMovements);
            }
        }

        if (direction.equals("Down")) {
            if (y1 == 0 || currentMap.floor.get(y1 - 1).get(x1).type.equals("outOfBounds") || (y1 <= y2 && nextMovePossible("Down", x1, x2, y1, y2))) {
                if (x1 < x2) {
                    return move("Right", count + 1, x1 + 1, x2, y1, y2, editMovements);
                }
                if (x1 > x2) {
                    return move("Left", count + 1, x1 - 1, x2, y1, y2, editMovements);
                }
                if (x1 == x2) {
                    if (x1 >= x / 2) {
                        return move("Right", count + 1, x1 + 1, x2, y1, y2, editMovements);
                    } else {
                        return move("Left", count + 1, x1 - 1, x2, y1, y2, editMovements);
                    }
                }
            } else {
                return move("Down", count + 1, x1, x2, y1 - 1, y2, editMovements);
            }
        }
        System.out.println("ERROR, END OF RECURSIVE METHOD INCORRECTLY REACHED");
        return 1000;       //Should never reach this

    }

    public Boolean nextMovePossible(String currentMove, int x1, int x2, int y1, int y2) {
        if (currentMove.equals("Right") || currentMove.equals("Left")) {      //If you are currently moving left or right
            if (y1 < y2 && currentMap.floor.get(y1 + 1).get(x1).type.equals("outOfBounds")) { //Find out if your next move is up or down, and check if up or down is possible
                return false;
            }
            if (y1 > y2 && currentMap.floor.get(y1 - 1).get(x1).type.equals("outOfBounds")) {
                return false;
            }
        }
        if (currentMove.equals("Up") || currentMove.equals("Down")) {
            if (x1 < x2 && currentMap.floor.get(y1).get(x1 + 1).type.equals("outOfBounds")) {
                return false;
            }
            if (x1 > x2 && currentMap.floor.get(y1).get(x1 - 1).type.equals("outOfBounds")) {
                return false;
            }
        }
        return true;
    }

    public void createMovements() {
        for (int i = 0; i < realList.size() - 1; i++) {
            String point1 = realList.get(i);
            String point2 = realList.get(i + 1);
            String[] point1Parts = point1.split(" ");
            String[] point2Parts = point2.split(" ");
            int point1X = Integer.parseInt(point1Parts[0]);
            int point1Y = Integer.parseInt(point1Parts[1]);
            int point2X = Integer.parseInt(point2Parts[0]);
            int point2Y = Integer.parseInt(point2Parts[1]);

            if (move("Right", 0, point1X, point2X, point1Y, point2Y, false) <= move("Left", 0, point1X, point2X, point1Y, point2Y, false)
                    && move("Right", 0, point1X, point2X, point1Y, point2Y, false) <= move("Down", 0, point1X, point2X, point1Y, point2Y, false)
                    && move("Right", 0, point1X, point2X, point1Y, point2Y, false) <= move("Up", 0, point1X, point2X, point1Y, point2Y, false)) {
                move("Right", 0, point1X, point2X, point1Y, point2Y, true);
            } else if (move("Left", 0, point1X, point2X, point1Y, point2Y, false) <= move("Down", 0, point1X, point2X, point1Y, point2Y, false)
                    && move("Left", 0, point1X, point2X, point1Y, point2Y, false) <= move("Up", 0, point1X, point2X, point1Y, point2Y, false)) {
                move("Left", 0, point1X, point2X, point1Y, point2Y, true);
            } else if (move("Down", 0, point1X, point2X, point1Y, point2Y, false) <= move("Up", 0, point1X, point2X, point1Y, point2Y, false)) {
                move("Down", 0, point1X, point2X, point1Y, point2Y, true);
            } else {
                move("Up", 0, point1X, point2X, point1Y, point2Y, true);
            }
        }
        drawMap();
    }

    public void printOut() {
        for (int i = 0; i < y; i++) {
            System.out.println();
            for (int j = 0; j < x; j++) {
                System.out.print(movements[i][j] + " ");
            }
        }
    }

    public void drawMap() {
        JFrame frame = new JFrame("Fastest Route");
        frame.setVisible(true);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        draw object = new draw(movements,realList, x, y);
        frame.add(object);
        object.drawing();
    }
}
