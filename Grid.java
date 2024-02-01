//https://youtu.be/_N4kh77F02c
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Grid {
    static int count = 0;
    private boolean[][] bombGrid;
    private int[][] countGrid;
    private int numRows;
    private int numColumns;
    private int numBombs;

    //private JFrame frame = new JFrame();

    public Grid(){
        numRows = 10;
        numColumns = 10;
        bombGrid = new boolean[numRows][numColumns];
        countGrid = new int[numRows][numColumns];
        numBombs = 25;
        createBombGrid();
        createCountGrid();
    }

    public Grid(int rows, int columns){
        numRows = rows;
        numColumns = columns;
        bombGrid = new boolean[numRows][numColumns];
        countGrid = new int[numRows][numColumns];
        numBombs = 25;
        createBombGrid();
        createCountGrid();
    }
    public Grid(int rows, int columns, int numBombs){
        numRows = rows;
        numColumns = columns;
        bombGrid = new boolean[numRows][numColumns];
        countGrid = new int[numRows][numColumns];
        this.numBombs = numBombs;
        createBombGrid();
        createCountGrid();
    }

    public int getNumRows(){
        return numRows;
    }
    public int getNumColumns(){
        return numColumns;
    }
    public int getNumBombs(){
        return numBombs;
    }
    public boolean[][] getBombGrid(){
        boolean[][] newArr = new boolean[numRows][numColumns];
        for(int i=0; i <numRows;i++){
            for (int j =0; j < numColumns; j++){
                newArr[i][j] = bombGrid[i][j];
            }
        }
        return newArr;
    }

    public int[][] getCountGrid() {
        int[][] newArr = new int[numRows][numColumns];
        for(int i=0; i <numRows;i++){
            for (int j =0; j < numColumns; j++){
                newArr[i][j] = countGrid[i][j];
            }
        }
        return newArr;
    }
    public boolean isBombAtLocation(int row, int column){
        if (bombGrid[row][column] == true){
            return true;
        }
        return false;
    }
    public int getCountAtLocation(int row, int column){
        return countGrid[row][column];
    }

    public void createBombGrid(){
        Random rand = new Random(); // initialize random number
        for (int i =0;i<numRows;i++){
            for(int j=0; j<numColumns;j++){
                bombGrid[i][j] = false;      // set all values to false
            }
        }
            for (int i = 0; i < numBombs; i++) {
                int randRow = rand.nextInt(numRows);     // gets random row with max number of rows
                int randColumn = rand.nextInt(numColumns); // gets random column with max number of columns
                if (bombGrid[randRow][randColumn] == true){
                    i--;                     // checks if random location is already occupied with bomb if it is loop again
                }
                else{
                    bombGrid[randRow][randColumn] = true;
                }
            }
    }
    public void createCountGrid(){
        for(int i=0;i<numRows;i++) {
            for (int j = 0; j < numColumns; j++) {
                int bombsNear = 0;
                if (bombGrid[i][j] == true){
                    bombsNear++;
                }
                if (i > 0 && bombGrid[i - 1][j]== true){
                    bombsNear++;
                }
                if (i+1<numRows && bombGrid[i+1][j]){
                    bombsNear++;
                }
                if(j > 0 && bombGrid[i][j-1] == true){
                    bombsNear++;
                }
                if(j+1 < numColumns && bombGrid[i][j+1] == true){
                    bombsNear++;
                }
                if (i > 0 && j > 0 && bombGrid[i-1][j-1]==true){
                    bombsNear++;
                }
                if(i + 1 < numRows && j+1 < numColumns && bombGrid[i+1][j+1] == true){
                    bombsNear++;
                }
                if (i > 0 && j+1 < numColumns && bombGrid[i-1][j+1]==true){
                    bombsNear++;
                }
                if (j > 0 && i+1 < numRows && bombGrid[i+1][j-1]==true){
                    bombsNear++;
                }
                countGrid[i][j] = bombsNear;
            }
        }


    }

    public static void  newGame() {
        Grid gameGrid = new Grid();
        JFrame jFrame = new JFrame();
        jFrame.setSize(gameGrid.getNumColumns() * 70, gameGrid.getNumRows() * 70); // increments the size based on rows and height
        jFrame.setTitle("Minesweeper");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits on close

        for (int i = 0; i < gameGrid.getNumRows(); i++) {
            for (int j = 0; j < gameGrid.getNumColumns(); j++) {
                JButton button = new JButton();
                button.setBounds(i *60 , j*60 , 60, 60); // creating the buttons
                button.setVisible(true);
                if (i == gameGrid.getNumRows() || j == gameGrid.getNumColumns()) {
                    button.setVisible(false);          // no index error
                }
                jFrame.add(button);   // adding the buttons to the layout
                jFrame.setLayout(null);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button1 = new JButton();
                        button1.setBounds(button.getLocation().x, button.getLocation().y, 60, 60);
                        button1.setVisible(true);
                        jFrame.add(button1);
                        button.setVisible(false);  // no buttons will display without the click
                        button1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                button1.setVisible(false);
                                JButton button2 = new JButton();
                                button2.setBounds(button.getLocation().x, button.getLocation().y, 60, 60);
                                button2.setVisible(true);
                                jFrame.add(button2);
                                if (gameGrid.isBombAtLocation((button.getLocation().y / 60), (button.getLocation().x / 60))) {
                                    button2.setText("BOMB");
                                    button2.setVisible(true);
                                    int input = JOptionPane.showOptionDialog(null, "Game over! Play Again?", "Nice Try", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                    if (input == JOptionPane.OK_OPTION) {
                                        jFrame.setVisible(false);  // creates new game
                                        count = 0;
                                        newGame();
                                    }
                                } else {
                                    button2.setText(Integer.toString(gameGrid.getCountAtLocation((button.getLocation().y / 60), (button.getLocation().x / 60))));
                                    count++;
                                    System.out.println(count);
                                    if (count == gameGrid.getNumRows() * gameGrid.getNumColumns() - gameGrid.getNumBombs()) {
                                        button2.setVisible(true);
                                        int input = JOptionPane.showOptionDialog(null, "You Won! Play Again?", "Good Job", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                        if (input == JOptionPane.OK_OPTION) {
                                            jFrame.setVisible(false);  // creates new game
                                            newGame();
                                        }
                                    }
                                }
                            }
                        });

                    }
                });
            }
        }
        jFrame.setVisible(true);

    }



    public static void main(String[] args){
        newGame();
    }

}