package speedyshopper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class draw extends JPanel {

    String[][] movements;
    int x;
    int y;
    ArrayList<String> alphabet = new ArrayList();
    ArrayList<String> realList;

    public draw(String[][] movements, ArrayList<String> realList, int x, int y) {
        this.movements = movements;
        this.x = x;
        this.y = y;
        this.realList = realList;
        this.realList.remove(0);
        this.realList.remove(this.realList.size() - 1);
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");
        alphabet.add("d");
        alphabet.add("e");
        alphabet.add("f");
        alphabet.add("g");
        alphabet.add("h");
        alphabet.add("i");
        alphabet.add("j");
        alphabet.add("k");
        alphabet.add("l");
        alphabet.add("m");
        alphabet.add("n");
        alphabet.add("o");
        alphabet.add("p");
        alphabet.add("q");
        alphabet.add("r");
        alphabet.add("s");
        alphabet.add("t");
        alphabet.add("u");
        alphabet.add("v");
        alphabet.add("w");
        alphabet.add("x");
        alphabet.add("y");
        alphabet.add("z");

    }

    public void drawing() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        int xposition = 0;
        int yposition = 10;
        super.paintComponent(g);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (movements[i][j].equals("floorTile")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                } else if (movements[i][j].equals("1")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.red);
                    g.fillRect(xposition + 22, yposition + 22, 15, 15);
                } else if (movements[i][j].equals("2")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.red);
                    g.fillRect(xposition + 10, yposition + 22, 15, 15);
                    g.fillRect(xposition + 35, yposition + 22, 15, 15);
                } else if (movements[i][j].equals("3") || movements[i][j].equals("4") || movements[i][j].equals("5")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.red);
                    g.fillRect(xposition + 22, yposition + 10, 15, 15);
                    g.fillRect(xposition + 5, yposition + 35, 15, 15);
                    g.fillRect(xposition + 40, yposition + 35, 15, 15);
                } else if (movements[i][j].equals("entrance")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.black);
                    g.drawRect(xposition + 10, yposition + 5, 15, 30);
                    g.drawRect(xposition + 35, yposition + 5, 15, 30);
                    g.drawString("Entrance", xposition + 5, yposition + 55);

                } else if (movements[i][j].equals("exit")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.black);
                    g.drawRect(xposition + 10, yposition + 5, 15, 30);
                    g.drawRect(xposition + 35, yposition + 5, 15, 30);
                    g.drawString("Checkout", xposition + 5, yposition + 55);

                    //TODO
                } else if (alphabet.contains(movements[i][j])) {  //If the tile has a letter in it (Add in the letter)
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                    g.setColor(Color.white);
                    g.fillRect(xposition + 1, yposition + 1, 58, 58);
                    g.setColor(Color.green);
                    g.fillOval(xposition + 5, yposition + 5, 50, 50);
                } else if (movements[i][j].equals("outOfBounds")) {
                    g.setColor(Color.black);
                    g.fillRect(xposition, yposition, 60, 60);
                }
                xposition = xposition + 60;

            }
            xposition = 0;
            yposition = yposition + 60;
        }
        for (int i = 0; i < realList.size(); i++) {
            String itemParts[] = realList.get(i).split(" ");//y position is offset by 10
            int itemX = Integer.parseInt(itemParts[0]) * 60;
            int itemY = (Integer.parseInt(itemParts[1]) * 60) + 10;

            g.setColor(Color.black);
            g.fillRect(itemX, itemY, 60, 60);
            g.setColor(Color.white);
            g.fillRect(itemX + 1, itemY + 1, 58, 58);
            g.setColor(Color.green);
            g.fillOval(itemX + 5, itemY + 5, 50, 50);
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g.drawString(alphabet.get(i), itemX + 25, itemY + 37);
            g.drawString("Get " + itemParts[2] + " at point " + alphabet.get(i), x*60 + 10, i * 20 + 50);
        }
    }
}
