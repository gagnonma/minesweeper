package project2;

/**
 * Class that represents a cell that has a mine count value and
 * boolean values to determine if isExposed, isMine, or isFlagged.
 */
public class Cell {
    private int mineCount;
    private boolean isExposed;
    private boolean isMine;
    private boolean isFlagged;

    /**
     * Constructor that initializes all the instance variables
     * to the given values
     *
     * @param exposed
     * @param mine
     * @param flagged
     * @param mines
     */
    public Cell(boolean exposed, boolean mine, boolean flagged,
                int mines) {
        isExposed = exposed;
        isMine = mine;
        mineCount = mines;
        isFlagged = flagged;
    }

    /**
     * Returns if the cell is exposed
     * @return boolean
     */
    public boolean isExposed() {
        return isExposed;
    }

    /**
     * sets the exposed value
     * @param exposed
     */
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /**
     * returns if the cell is a mine
     * @return boolean
     */
    public boolean isMine() {
        return isMine;
    }

    /**
     * sets if the cell is a mine
     * @param mine
     */
    public void setMine(boolean mine) {
        isMine = mine;
    }

    /**
     * returns the amount mines neighboring the cell
     * @return int
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * sets the number of mines that neighbor the cell
     * @param mineCount
     */
    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    /**
     * returns if the cell if flagged
     * @return boolean
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * sets the value of whether or not the cell is flagged.
     * @param flagged
     */
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
