/** *************************************************************
 * file: ColorGameEngine.java
 * author: Brandon Nguyen, Charly Dang, Colin Koo, Felix Zhang, Gerianna Geminiano
 * class: CS 245 – Programming Graphical User Interface
 *
 * assignment: Swing Project v1.1
 * date last modified: 10/19/17
 *
 * purpose: purpose: This program is a "Point-and-click" Hangman and Color game. Using Swing,
 * we created a game that is controlled by your mouse and keyboard. The user
 * will be able to play the classic Hangman game with 6 guesses, play a matching
 * color game with 5 rounds, see the top 5 high scores, and the credits. You will
 * also be able to switch back and forth between the displays using the buttons
 * integrated.
 *
 *************************************************************** */
package cs245.v1.pkg0.pkg1;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColorGameEngine {

    Circle[] circles = new Circle[5];
    private int score = 0;
    private boolean won;
    private String input;
    private String[] colors = {"red", "yellow", "green", "blue","purple"};
    private String target;
    private int rounds = 0;


    /*
        Constructor
     */
    public ColorGameEngine() {

        
    }

    /*
        method: getText
        purpose: returns string of literal color word
     */
    public String getText() {
        int ran = new Random().nextInt(colors.length);
        target = colors[ran];
        return target;

    }

    /*
    method: setTarget
    puspose: sets the color of the text
     */
    public void setTarget(int rnd) {
        target = colors[rnd];
        //System.out.println(target);
    }

    /*
    method: getScore
    puspose: returns score
     */
    public int getScore() {
        return score;
    }

    /*
    method: setScore
    purpose: setter for the score variable
     */
    public void setScore(int x) {
        score = x;
    }

    /*
    method: matches
    puspose: check if the circle and color of text match and updates varaibles
     */
    public void matches(String input) {
        if (rounds <= 3) {
            if (target.equals(input)) {
                score += 100;
                //System.out.println("Correct: " + score);
                setScore(score);
                rounds++;
            } else {
                //System.out.println("WRONG: " + score);
                rounds++;
            }
        } else if (rounds == 4) {
            if (target.equals(input)) {
                score += 100;
                //System.out.println("Correct: " + score);
                rounds++;
            } else {
                //System.out.println("Wrong: " + score);
                rounds++;
            }
        } else {
            //System.out.println("Game is over");
        }
    }

    /*
    method: getRounds
    puspose: returns number of rounds
     */
    public int getRounds() {
        return rounds;
    }

    /*
    method: resetRounds
    puspose: sets the number of rounds to 0
     */
    public void resetRounds() {
        rounds = 0;
    }


    /*
    method: drawCircleAt
    puspose: draws a circles at coordinates (x,y)
     */
    public Shape drawCircleAt(int x, int y) {
        Shape circle = new Ellipse2D.Double(x, y, 50.0f, 50.0f);
        return circle;
    }

    /*
    method: getCircles
    puspose: returns an array of the circles
     */
    public Shape[] getCircles(int width, int height) {
        Shape[] shapes = new Shape[5];
        int x, y, radius;

        calcCirclePos(width, height);

        for (int i = 0; i < circles.length; i++) {
            x = circles[i].getXPos();
            y = circles[i].getYPos();
            radius = circles[i].getRadius();

            shapes[i] = new Ellipse2D.Double(x, y, radius, radius);

        }
        return shapes;
    }

    /*

    method: calcCirclePos
    purpose: 
    This is used to calculate the positions of the circles with help
    from the intersects method. Takes in the width and the height of the 
    JPanel and draws circles within the bounds of the JPanel.
    @returns a circle array to the circle panel class.
     */
    public void calcCirclePos(int xHeight, int yHeight) {
    
        circles = new Circle[5];
        int windowH = yHeight;
        int windowW = xHeight;
        int counter = 0;
        Random rand = new Random();
        Circle tempCirc;
        int tempX, tempY;
        boolean done = false;

        /*
        while the circle array is not filled, keep picking random numbers for
        coordinates of the circle. Then check if the random circle overlaps with 
        any circle thats already in the list. if so, repick new random coordinates.
         */
        while (!done) {
            tempX = rand.nextInt(windowW);
            tempY = rand.nextInt(windowH);
            tempCirc = new Circle(tempX, tempY);

            if (!containsInter(tempCirc)) {
                circles[counter] = tempCirc;
                counter++;
            }
            
            if (counter == 5) {
                done = true;
            }

        }

    }


    /*
    method: containsInter
    puspose: method to check if target circle has an intersection
    within the circle array.
     */
    private boolean containsInter(Circle target) {

        for (int i = 0; i < circles.length; i++) {
            if (circles[i] != null) {
                if (target.intersects(circles[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    object to hold the circle information.
     */
    public static class Circle {

        private int xPos, yPos;
        private int xCenter, yCenter;
        private final int radius = 70;

        public Circle(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            xCenter = xPos + 35;
            yCenter = yPos + 35;
        }

        /*
        method: intersects
        purpose: checks if two circles are intersecting given the two
        circle objects. 
         */
        public boolean intersects(Circle comp) {
            double radiSum = (radius * 2) + 10;
            double x0x1 = Math.pow((comp.getXCenter() - this.getXCenter()), 2);
            double y0y1 = Math.pow((comp.getYCenter() - this.getYCenter()), 2);
            double sum = x0x1 + y0y1;
            double distance = Math.sqrt(sum);

            if (radiSum >= distance) {
                return true;
            }
            return false;
        }

        public int getXCenter() {
            return xCenter;
        }

        public int getYCenter() {
            return yCenter;

        }

        public int getXPos() {
            return xPos;
        }

        public int getYPos() {
            return yPos;
        }

        public int getRadius() {
            return radius;
        }

        public void setXPos(int xPos) {
            this.xPos = xPos;
            xCenter = xPos + 25;
        }

        public void setYPos(int yPos) {
            this.yPos = yPos;
            yCenter = yPos + 25;
        }

        public String toString() {
            String retString = this.xCenter + " " + this.yCenter;
            return retString;
        }

    }
}
