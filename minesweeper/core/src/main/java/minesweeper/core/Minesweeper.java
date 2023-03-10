package minesweeper.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Minesweeeper wraps around Minefield, and takes care of game logic such as:
 * winning/losing, figuring out coordinates for chording (safeSquaresAround),
 * and keeping track of flags left to place. Minesweeper has the events:
 * win, lose, and start. You can set up actions to be taken when these happen.
 */
public class Minesweeper {
    private final Minefield minefield;
    private List<Action> onWinActions;
    private List<Action> onLossActions;
    private List<Action> onStartActions;
    private boolean gameIsStarted;
    private final int mineCount;
    private int openedSquares;
    private int flagCount;
    private boolean isLost = false;

    /**
     * Constructor for Minesweeper using a preset difficulty.
     * @param difficulty the difficulty of minesweeper
     */
    public Minesweeper(final Difficulty difficulty) {
        this(
            difficulty.getWidth(),
            difficulty.getHeight(),
            difficulty.getMineCount()
        );
    }

    /**
     * Constructor for Minesweeper with custom difficulty.
     * @param width the width of minefield
     * @param height the height of minefield
     * @param mineCount the amount of mines to be added to the minefield
     */
    public Minesweeper(
        final int width,
        final int height,
        final int mineCount
    ) {
        minefield = new Minefield(width, height);
        onWinActions = new ArrayList<>();
        onLossActions = new ArrayList<>();
        onStartActions = new ArrayList<>();
        gameIsStarted = false;
        this.mineCount = mineCount;
        openedSquares = 0;
        flagCount = 0;
    }

    /**
     * Checks whether the square has a mine or not.
     * @param x x-coordinate of the square
     * @param y y-coordinate of the square
     * @return whether or not the square has a mine
     */
    public Boolean hasMine(final int x, final int y) {
        return minefield.hasMine(x, y);
    }

    /**
     * Toggles flag at given square in minefield.
     * @param x x-coordinates of the square
     * @param y y-coordinates of the square
     */
    public void toggleFlag(final int x, final int y) {
        if (minefield.squareIsOpened(x, y) || isLost) {
            return;
        }

        minefield.toggleFlag(x, y);
        flagCount += minefield.isFlagged(x, y) ? 1 : -1;
    }

    /**
     * Checks whether the square is flagged or not in minefield.
     * @param x x-coordinates of the square
     * @param y y-coordinates of the square
     * @return whether the square is flagged or not
     */
    public boolean isFlagged(final int x, final int y) {
        return minefield.isFlagged(x, y);
    }

    /**
     * The amount of mines - the amount of flags.
     * @return flags left to be placed
     */
    public int flagsLeft() {
        return mineCount - flagCount;
    }

    /**
     * Opens the square in the minefield.
     * Starts the game on first open.
     * Loses the game if it had a mine.
     * Wins the game if all safe squares have been opened.
     * @param x x-coordinate of the square
     * @param y y-coordinate of the square
     */
    public void openSquare(final int x, final int y) {
        if (!gameIsStarted) {
            gameIsStarted = true;
            minefield.initializeMines(mineCount, x, y);
            start();
        }

        if (minefield.squareIsOpened(x, y) || isLost) {
            return;
        }

        minefield.openSquare(x, y);
        if (minefield.squareIsOpened(x, y)) {
            if (minefield.hasMine(x, y)) {
                lose();
            } else {
                openedSquares++;
                if (allSafeSquaresAreOpened()) {
                    win();
                }
            }
        }
    }

    /**
     * Checks if openedSquares is equal to the amount of squares that don't
     * have mines in them.
     * @return whether or not all safe squares are opened
     */
    private boolean allSafeSquaresAreOpened() {
        int safeSquares = getHeight() * getWidth() - mineCount;
        return openedSquares == safeSquares;
    }

    /**
     * Checks whether the square is opened or not.
     * @param x x-coordinate of the square
     * @param y y-coordinate of the square
     * @return whether the square is opened or not
     */
    public boolean squareIsOpened(final int x, final int y) {
        return minefield.squareIsOpened(x, y);
    }

    /**
     * Add an action to the list of actions to take
     * when the game is won.
     * The game is won when all safe squares have been opened.
     * @param action the action to be taken on win
     */
    public void addOnWin(final Action action) {
        onWinActions.add(action);
    }

    /**
     * Add an action to the list of actions to take
     * when the game is lost.
     * The game is lost when a mine is opened by the openSquare method.
     * @param action the action to be taken on loss
     */
    public void addOnLoss(final Action action) {
        onLossActions.add(action);
    }

    /**
     * Add an action to the list of actions to take
     * when the game starts.
     * The game starts the first time the openSquare method is used.
     * @param action the action to be taken on start
     */
    public void addOnStart(final Action action) {
        onStartActions.add(action);
    }

    /**
     * Takes all actions that have been registered
     * to be taken when the game is lost.
     */
    private void lose() {
        isLost = true;
        for (Action action : onLossActions) {
            action.run();
        }
    }

    /**
     * Takes all action that have been registered
     * to be take when the game is won.
     */
    private void win() {
        for (Action action : onWinActions) {
            action.run();
        }
    }

    /**
     * Takes all actions that have been registered
     * to to be taken when the game starts.
     */
    private void start() {
        for (Action action : onStartActions) {
            action.run();
        }
    }

    /**
     * Gets width of minefield.
     * @return width of minefield
     */
    public int getWidth() {
        return minefield.getWidth();
    }

    /**
     * Gets height of minefield.
     * @return height of minefield
     */
    public int getHeight() {
        return minefield.getHeight();
    }

    /**
     * Gets the amount of mines adjacent
     * to the square on the coordinate.
     * Including diagonal squares.
     * @param x x-coordinate of square
     * @param y y-coordinate of square
     * @return amount of adjacent mines
     */
    public int getAdjacentMines(final int x, final int y) {
        int adjacentMines = 0;
        int[] offsets = {-1, 0, 1};
        for (int offsetY : offsets) {
            for (int offsetX : offsets) {
                if (offsetX == 0 && offsetY == 0) {
                    continue;
                }
                int adjX = x + offsetX;
                int adjY = y + offsetY;
                if (!minefield.isOutOfBounds(adjX, adjY)
                    && minefield.hasMine(adjX, adjY)
                ) {
                    adjacentMines++;
                }
            }
        }
        return adjacentMines;
    }

    /**
     * If adjacent flag count is equal to the adjacent mine count,
     * the rest of the adjacent, unopened squares should be safe (if the flags
     * are correct). This method returns the coordinates of these safe squares.
     * If the adjacent mine and flag counts don't match, the array will be
     * empty.
     * @param x x-coordinates of square
     * @param y y-coordinates of square
     * @return array of {x, y} arrays, containing the safe squares.
     */
    public Integer[][] safeSquaresAround(final int x, final int y) {
        if (!squareIsOpened(x, y)) {
            return new Integer[0][];
        }
        int adjacentMines = getAdjacentMines(x, y);
        int adjacentFlags = 0;
        List<Integer[]> safeSquares = new ArrayList<>();

        int[] offsets = {-1, 0, 1};
        for (int offsetY : offsets) {
            for (int offsetX : offsets) {
                if (offsetX == 0 && offsetY == 0) {
                    continue;
                }
                int adjX = x + offsetX;
                int adjY = y + offsetY;
                if (minefield.isOutOfBounds(adjX, adjY)) {
                    continue;
                }
                if (minefield.isFlagged(adjX, adjY)) {
                    adjacentFlags++;
                } else if (!minefield.squareIsOpened(adjX, adjY)) {
                    Integer[] coords = {adjX, adjY};
                    safeSquares.add(coords);
                }
            }
        }
        if (adjacentFlags != adjacentMines) {
            return new Integer[0][];
        }
        Integer[][] safeSquaresArray = new Integer[safeSquares.size()][];
        return safeSquares.toArray(safeSquaresArray);
    }
}
