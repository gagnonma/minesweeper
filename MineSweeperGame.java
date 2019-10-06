package project2;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Contains all the methods to create and run a game of
 * minesweeper
 *
 * @author Max Gagnon and Shane Watrous
 *
 */

public class MineSweeperGame {
    private Cell[][] board;
    private GameStatus status;
    private int totalMineCount;
    private int exposedCells;
    private int boardSize;

    /**
     * Constructor that initializes the gamestatus, board size
     * mine count, creates the board out of a 2D array of Cells
     * and lays the mines
     *
     * @param size
     * @param mines
     */
    public MineSweeperGame(int size, int mines) {
        status = GameStatus.NotOverYet;
        boardSize = size;
        totalMineCount = mines;
        board = new Cell[boardSize][boardSize];
        setEmpty();
        layMines (totalMineCount);
        exposedCells = 0;
    }

    /**
     * Sets all the cells as empty
     */
    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                board[r][c] = new Cell(false, false, false,0);
        // totally clear.
    }

    /**
     * returns the cell at the given coordinates
     *
     * @param row
     * @param col
     * @return Cell
     */
    public Cell getCell(int row, int col) {
        return board[row][col];
    }


    /**
     * Selects the cell at the given coordinates.
     *
     * @param row
     * @param col
     */
    public void select(int row, int col) {
        if (board[row][col].isFlagged()) {// if flagged do nothing.
            return;
        }
        if (board[row][col].isMine()) {   // did I lose
            status = GameStatus.Lost;
        }else {								//show the cell
            //showCell(row,col);
            showCellIter(row,col);
        }

        //determine if I won
        if(exposedCells == (boardSize*boardSize) - totalMineCount){
            status = GameStatus.Won;
            exposedCells = 0;
        }

    }

    /**
     * Returns the current status of the game
     * @return GameStatus
     */
    public GameStatus getGameStatus() {
        return status;
    }

    /**
     * Resets the game
     */
    public void reset() {
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines (totalMineCount);
    }

    /**
     * Given an amount of mines, set that many mines
     * randomly on the board.
     *
     * @param mineCount
     */
    private void layMines(int mineCount) {
        int i = 0;		// ensure all mines are set in place

        Random random = new Random();
        while (i < mineCount) {
            int c = random.nextInt(boardSize);
            int r = random.nextInt(boardSize);

            if (!board[r][c].isMine()) {
                board[r][c].setMine(true);
                i++;
            }
        }
    }

    /**
     * Using recursion show the amount of mines surrounding
     * the cell at the given location. If there are 0 mines
     * surrounding that cell expose all surrounding cells
     * that also have 0 mines neighboring them.
     *
     * @param row
     * @param col
     */
    private void showCell(int row, int col){
        board[row][col].setExposed(true);
        exposedCells++;
        int mines = 0;
        //check neighbors for mines
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if(isValidPosition(row+i,col+j)){
                    if (board[row + i][col + j].isMine()) {
                        mines++;
                    }
                }
            }
        }
        board[row][col].setMineCount(mines);

        if(board[row][col].getMineCount() == 0){
            for (int i = -1; i <= 1; i++){
                for (int j = -1; j <= 1; j++){
                    if(isValidPosition(row+i, col+j)){
                        if(!board[row+i][col+j].isExposed() &&
                                !board[row+i][col+j].isFlagged()) {
                            showCell(row + i, col + j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Using  iteration show the amount of mines surrounding
     * the cell at the given location. If there are 0 mines
     * surrounding that cell expose all surrounding cells
     * that also have 0 mines neighboring them.
     *
     * @param row
     * @param col
     */
    public void showCellIter(int row, int col){
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(row);
        pos.add(col);
        Stack<ArrayList<Integer>> stack = new Stack();
        stack.push(pos);
        exposedCells++;

        while(!stack.isEmpty()){
            pos = stack.pop();
            row = pos.get(0);
            col = pos.get(1);
            //expose position
            board[row][col].setExposed(true);
            int mines = findMineCount(row,col);
            board[row][col].setMineCount(mines);
            if (mines == 0){
                //expose neighbors
                for (int r = -1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c++) {
                        if (isValidPosition(row + r, col + c) &&
                                !board[row + r][col + c].isExposed() &&
                                !board[row + r][col + c].isFlagged()){

                            board[row + r][col + c].setExposed(true);
                            exposedCells++;
                            int m = findMineCount(row + r, col + c);
                            board[row + r][col + c].setMineCount(m);
                            //if the neighbor has 0 mines push it
                            if (m == 0){
                                ArrayList<Integer> newPos = new ArrayList<>();
                                newPos.add(row+r);
                                newPos.add(col+c);
                                stack.push(newPos);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds and returns the amount of mines neighboring the cell
     * at the given location
     * @param row
     * @param col
     * @return int
     */
    public int findMineCount(int row, int col){
        int mines = 0;
        //check neighbors for mines
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if(isValidPosition(row+i,col+j)){
                    if (board[row + i][col + j].isMine()) {
                        mines++;
                    }
                }
            }
        }
        return mines;
    }

    /**
     * Determines if the given position is within the game board
     *
     * @param row
     * @param col
     * @return boolean
     */
    public boolean isValidPosition(int row, int col){
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        MineSweeperGame game = new MineSweeperGame (700, 1);    // board size of 100 with 1 mine
        Long t = System.currentTimeMillis();
        game.select (2,3)	;
        System.out.println ("Time:" + (System.currentTimeMillis() - t));
    }

}


