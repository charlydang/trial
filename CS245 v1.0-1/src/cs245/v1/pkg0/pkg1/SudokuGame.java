/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs245.v1.pkg0.pkg1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;

/**
 *
 *
 */
public class SudokuGame extends JPanel {
    private ColorGameEngine cEngine; 
    private SudokuGameEngine engine;
    private MainFrame main;
    private JTextField[][] boxes = new JTextField[9][9];
    private JPanel board = new JPanel();

    public SudokuGame(SudokuGameEngine engine,ColorGameEngine cEngine) {
        this.engine = engine;
        this.cEngine = cEngine;
        setLayout(new BorderLayout());

        initBoard();
        createBoard();
        titleBar();
        submitButton();
        quitButton();
    }

    public void setMain(MainFrame main) {
        this.main = main;

    }

    /*
    For the sudoku board we are going to use a
    9x9 gridLayout for the inner JPanel thats going to be in the center of the 
    outer JPanel which is an instance of this class.
     */
    private void createBoard() {
        board.setLayout(new GridLayout(9, 9));
        
        

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {

                board.add(boxes[i][j]);
            }
        }

        board.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(board, BorderLayout.CENTER);
    }

    /*
    This method draws the title bar of the game panel.
    Title bar should include the time as well as a stylized
    version of the game name. 
     */
    private void titleBar() {
        JPanel title = new JPanel(new BorderLayout());
        JLabel sudoku = new JLabel("SUDOKU");
        JLabel time = new JLabel();
        
        //adding the time
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
        title.add(time, BorderLayout.LINE_END);
        
        
        //adding stylized sudoku name
        sudoku.setFont(new Font("Papyrus", Font.BOLD, 18));
        title.add(sudoku, BorderLayout.LINE_START);
        add(title, BorderLayout.PAGE_START);

    }

    /*
    adds in the submit button that goes on the left
    side of the game board.
     */
    private void submitButton() {
        JButton submit = new JButton("Submit");
        JPanel buttons = new JPanel(new BorderLayout());
        
        //formatting
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 25, 25, 25));
        buttons.add(submit, BorderLayout.PAGE_END);
        add(buttons, BorderLayout.LINE_START);
        
        //adding actionListener
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //add actionlistener actions for submitting
               // Game Over Score is Sudoku Score + previous games Score
               engine.setScore(cEngine.getScore()+engine.getFinalScore());
               System.out.println("Sudoku Score: " + engine.getFinalScore());
               main.gameOverMessage();
               main.swapView("over");
            }
            
        });
    }

    /*
    This method adds in the submit button that goes on the right
    side of the game board. 
     */
    private void quitButton() {
        JButton quit = new JButton("Quit");
        JPanel btns = new JPanel(new BorderLayout());
        
        //formatting
        btns.setBorder(BorderFactory.createEmptyBorder(10, 25, 25, 25));
        btns.add(quit, BorderLayout.PAGE_END);
        add(btns, BorderLayout.LINE_END);
        

        //adding actionListener
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If choose to quit then Socuku score is 0
                // So Ending score is previous games Score
                engine.setScore(cEngine.getScore());
                System.out.println("Sudoku Score: " + engine.getFinalScore());
                main.gameOverMessage();
                main.swapView("over");
            }
            
        });
    }

    /*
    initializes the board to a new state.
    Probably can use this to restart the game.
     */
    private void initBoard() {
        int[][] board = engine.getBoard();
        
        

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                boxes[i][j] = new JTextField();
                if (board[i][j] != 0) {
                    boxes[i][j].setText(Integer.toString(board[i][j]));
                    boxes[i][j].setEditable(false);
                } else {
                    boxes[i][j].setText(" ");
                    boxes[i][j].setEditable(true);
                }                              
            }
        }
        
        beautifyBorders();
    }
    
    private void beautifyBorders() {
        
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                
                if (j == 2 || j == 5) {
                    boxes[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
                } else if (i == 2 || i == 5) {
                    boxes[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
                } else {
                    boxes[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
                }
                
                boxes[2][5].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                boxes[2][2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                boxes[5][2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                boxes[5][5].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
                
                boxes[i][j].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }

    /*
    need to add a separate action listener that listens to 
    when the JtextFields are modified with user input.
    This listener needs to be different so that it does not 
    mess with the action listeners for the buttons.
     */
    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField src = (JTextField) e.getSource();

        }

        /*
        this method is used for finding the x and y values
        for the engine to check if the value is a valid input.x
         */
        private void findSrc() {

        }

    }

}
