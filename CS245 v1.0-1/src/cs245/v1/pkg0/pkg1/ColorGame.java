/** *************************************************************
 * file: ColorGame.java
 * author: Brandon Nguyen, Charly Dang, Colin Koo, Felix Zhang, Gerianna Geminiano
 * class: CS 245 – Programming Graphical User Interface
 *
 * assignment: Swing Project v1.1
 * date last modified: 10/19/17
 *
 * purpose: This program is a "Point-and-click" Hangman and Color game. Using Swing,
 * we created a game that is controlled by your mouse and keyboard. The user
 * will be able to play the classic Hangman game with 6 guesses, play a matching
 * color game with 5 rounds, see the top 5 high scores, and the credits. You will
 * also be able to switch back and forth between the displays using the buttons
 * integrated.
 *
 *************************************************************** */
package cs245.v1.pkg0.pkg1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class ColorGame extends JPanel {

    private ColorGameEngine engine;
    private HManGameEngine hEngine;
    private MainFrame main;
    private CirclePanel game;
    private Random rand = new Random();
    private JPanel titleBar = new JPanel(new BorderLayout());
    private JPanel skipPanel = new JPanel();
    private JButton skip = new JButton("Skip");
    private JLabel points = new JLabel();
    private JTextField color = new JTextField(10);
    private JLabel target = new JLabel();

    /*
    Constructor
     */
    public ColorGame(ColorGameEngine engine, HManGameEngine hEngine) {
        this.engine = engine;
        this.hEngine = hEngine;
        game = new CirclePanel();
        setLayout(new BorderLayout());
        loadUI();
    }

    /*
    method: loadUI
    purpose: Loads the UI for the color game
     */
    private void loadUI() {
        target.setText("Color: " + engine.getText());
        target.setFont(new Font("Papyrus", Font.BOLD, 18));
        target.setForeground(chooseRandomColor());
        titleBar.add(target, BorderLayout.LINE_START);
        add(titleBar, BorderLayout.PAGE_START);
        add(skipPanel, BorderLayout.LINE_END);
        add(game, BorderLayout.CENTER);
        drawTitle();
    }

    /*
    method: drawTitle
    purpose: Draws the title bar for the color game (diplays time)
     */
    private void drawTitle() {
        JLabel time = new JLabel();

        // adding the time
        time.setHorizontalAlignment(JLabel.CENTER);
        time.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 12));
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time.setText(DateFormat.getDateTimeInstance().format(new Date()));
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        titleBar.add(time, BorderLayout.LINE_END);
        
      //adding the points
       
    }
    
    
    /*
    method: chooseRandomeColor
    purpose: Chooses  a randome color
     */
    public Color chooseRandomColor() {
        Color[] colors = {Color.red, Color.yellow, Color.green, Color.blue, new Color(255, 0, 255)};
        int rnd = new Random().nextInt(colors.length);
        Color choice = colors[rnd];
        engine.setTarget(rnd);
        return choice;
    }

    /*
    method: setMain
    purpose: setting the current mainFrame to the reference in this class
    to allow for panel switching.
     */
    public void setMain(MainFrame main) {
        this.main = main;
    }

    /*
    method: startNewGame
    purpose: Resets the game.
     */
    public void startNewGame() {
        engine.setScore(0); // reset the score back to 0
        titleBar.removeAll();
        engine.resetRounds();
        loadUI();

    }

    /*
    inner panel that contains the circles. Game engine
    is supposed to calculate the circle positions and this 
    nested class just draws them.
     */
    private class CirclePanel extends JPanel implements MouseListener, MouseMotionListener {

        private Shape[] drawCirc = new Shape[5];
        private Color[] colors = {Color.red, Color.yellow, Color.green, Color.blue, new Color(255, 0, 255)};
        private Point mouse = null;
        private String color;

        /*
    Constructor
         */
        public CirclePanel() {
            drawCirc = engine.getCircles(431, 284);
            addMouseListener(this);
            addMouseMotionListener(this);
        }


        /*
        method: reDraw
        purpose: redraws the screen with new color, points, and random circles
         */
        public void reDraw() {
            target.setText("Color: " + engine.getText());
            target.setFont(new Font("Papyrus", Font.BOLD, 18));
            target.setForeground(chooseRandomColor());
            titleBar.add(target, BorderLayout.LINE_START);

            drawCirc = engine.getCircles(431, 284);
        }

        /*
        method: paintComponent
        purpose: paints the circles 
         */
        public void paintComponent(Graphics g) {
            Rectangle r = this.getBounds();
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1));

            //Draws the ovals based on the game engine calculated coordinates.
            //Need to fill in with a color later.
            for (int i = 0; i < drawCirc.length; ++i) {
                if (mouse != null && drawCirc[i].contains(mouse)) {//mouseX, mouseY)){
                    g2.setColor(colors[i].darker());
                    thisColor(i);
                } else {
                    g2.setColor(colors[i]);
                }
                g2.fill(drawCirc[i]);
            }

        }

        /*
        method: thisColor
        purpose: defines which color is chosen
         */
        public void thisColor(int i) {
            switch (i) {
                case 0:
                    color = "red";
                    break;
                case 1:
                    color = "yellow";
                    break;
                case 2:
                    color = "green";
                    break;
                case 3:
                    color = "blue";
                    break;
                case 4:
                    color = "pink";
                    break;

            }
        }
        

        /*
        method: mouseClicked
        purpose: if circle is clicked then ...
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (engine.getRounds() <= 3) {
                // If its initial round first get the score from hangman
                if (engine.getRounds() == 0) {
                    engine.setScore(hEngine.getScore());
                    System.out.println("HangmanScore:" + hEngine.getScore());
                }
                for (Shape shape : drawCirc) {
                    if (shape.contains(e.getPoint())) {;
                        //System.out.println(color);
                        engine.matches(color);
                        reDraw();
                        System.out.println("ColorScre:" + engine.getScore());
                    }
                }
            } else if (engine.getRounds() == 4) {
                for (Shape shape : drawCirc) {
                    if (shape.contains(e.getPoint())) {
                        //System.out.println(color);
                        engine.matches(color);
                        System.out.println("ColorScre:" + engine.getScore());
                        main.gameOverMessage();
                        main.swapView("play3");
                    }
                }
            } else {;
                main.gameOverMessage();
                main.swapView("play3");
            }
        }
           

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        /*
        method: mouseMoved
        purpose: if mouse is over circle button, then highlight it
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            mouse = e.getPoint();
            repaint();
        }

    }

}
