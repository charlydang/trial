/** *************************************************************
 * file: MainFrame.java
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author FelixZhang
 */
public class MainFrame extends JFrame {

    private CardLayout layout = new CardLayout();
    private JPanel mainP = new JPanel(layout);
    private MainMenu mainMenu = new MainMenu();
    private HighScore hScore = new HighScore();
    private Credits credit = new Credits();
    private TitlePanel title = new TitlePanel();
    private HManGameEngine engine = new HManGameEngine();
    private ColorGameEngine colorEngine = new ColorGameEngine();
    private SudokuGameEngine sudokuEngine = new SudokuGameEngine();
    private GameOver over = new GameOver(sudokuEngine);
    private HManGame play = new HManGame(engine);
    private ColorGame playColor = new ColorGame(colorEngine, engine);
    private SudokuGame sudoku = new SudokuGame(sudokuEngine, colorEngine);
    private JFrame frame;
    private Timer timer;

    /*
    Constructor for main frame.
     */
    public MainFrame() {
        initUI();
    }

    /*
    method: reload
    purpose: reloads and redraws the PlayGame panel and 
    starts a new game.
     */
    public void reload() {
        play.startNewGame();
    }

    /*
    method:startMenuTimer
    purpose: accessor method for sub classes to access mainMenu class
    method to start the timer for the main menu animation once
    the user returns to the main menu.
     */
    public void startMenuTimer() {
        mainMenu.startTimer();
    }

    /*
    method: stopMenuTimer
    purpose: accessor method for sub classes to access mainMenu class
    to stop the timer for the main menu animation once the
    user leaves the main menu.
     */
    public void stopMenuTimer() {
        mainMenu.stopTimer();
    }

    /*
    method: swapView
    purpose: This is used by the sub panels inside the layout to be able to change to
    other panels. Not sure if this is the correct way to do this but it works.
     */
    public void swapView(String key) {
        layout.show(mainP, key);
    }

    /*
    method: startNewGame
    purpose: redraws the game GUI elment
     */
    public void startNewGame() {
        play.startNewGame();
        playColor.startNewGame();
    }

    /*
    method: gameOverMessage
    purpose: controls which game over message is displayed.
    either You win or you lose.
     */
    public void gameOverMessage() {
        over.addTitle();
    }

    /*
    method: updateScore
    purpose: updates High Score
     */
    public void updateScore() {
        hScore.updateHS();
    }

    /*
    method: initUI
    purpose: initializer method to draw the frame and add elements to it.
     */
    private void initUI() {
        ImageIcon icon = new ImageIcon("thinking.jpg");

        startLayout();
        introTimer();

        setTitle("Thinkman's Hangman");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(icon.getImage());

        add(mainP);
        pack();
    }

    /*
    method:startLayout
    purpose: the panel initializations 
     */
    private void startLayout() {
        mainP.add(title, "title");
        mainP.add(mainMenu, "menu");
        mainP.add(hScore, "High Score");
        mainP.add(credit, "credits");
        mainP.add(play, "play");
        mainP.add(over, "over");
        mainP.add(playColor, "play2");
        mainP.add(sudoku, "play3");

        mainMenu.setMain(this);
        credit.setMain(this);
        hScore.setMain(this);
        play.setMain(this);
        playColor.setMain(this);
        sudoku.setMain(this);
        over.setMain(this);

        // Key Event Listener for F1 and Esc
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    // Is called twice when key is pressed then released
                    public boolean dispatchKeyEvent(KeyEvent ke) {
                        //So only do when key is pressed
                        if (ke.getID() == KeyEvent.KEY_PRESSED) {
                            // For Esc key
                            if (ke.getKeyCode() == ke.VK_ESCAPE) {
                                System.out.println("escaped ?");
                                System.exit(0);
                            } // For F1 key
                            else if (ke.getKeyCode() == ke.VK_F1) {
                                JOptionPane.showMessageDialog(frame, "Winter Quarter\nCharly Dang 010924537"
                                        + "\nBrandon Nguyen 011499566\nColin Koo 010291241\nFelix Zhang 01042383"
                                        + "\nGerianna Geminiano 010662522");
                            }
                        }
                        return false;
                    }
                });
    }

    /*
    method: introTimer
    purpose: Timer to set the 3 second delay between the title panel
    and the main menu.
     */
    private void introTimer() {
        int delay = 3000;

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(mainP, "menu");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Main method that uses EventQueue.invokeLater in order to have the GUI
     * execute in the Event Dispatch Thread so backend calculations do not cause
     * the GUI to freeze or lag.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame menu = new MainFrame();
            menu.setVisible(true);

        });

    }
}
