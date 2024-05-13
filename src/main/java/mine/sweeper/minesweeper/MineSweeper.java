package mine.sweeper.minesweeper;

import static mine.sweeper.holder.ConstantsHolder.BLOCK_IMAGE;
import static mine.sweeper.holder.ConstantsHolder.BOARD_HEIGHT;
import static mine.sweeper.holder.ConstantsHolder.BOARD_WIDTH;
import static mine.sweeper.holder.ConstantsHolder.BOMB_IMAGE;
import static mine.sweeper.holder.ConstantsHolder.DEFAULT_NUM_COLUMNS;
import static mine.sweeper.holder.ConstantsHolder.DEFAULT_NUM_ROWS;
import static mine.sweeper.holder.ConstantsHolder.EMPTY;
import static mine.sweeper.holder.ConstantsHolder.FONT_NAME;
import static mine.sweeper.holder.ConstantsHolder.FOURTY_FIVE;
import static mine.sweeper.holder.ConstantsHolder.GAME_NAME;
import static mine.sweeper.holder.ConstantsHolder.ONE;
import static mine.sweeper.holder.ConstantsHolder.SEPARATOR;
import static mine.sweeper.holder.ConstantsHolder.TWENTY_FIVE;
import static mine.sweeper.holder.ConstantsHolder.ZERO;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MineSweeper {
    private final JFrame frame;
    private final JLabel textLabel;
    private MineTile[][] board;
    private List<MineTile> mineList;
    private int tilesClicked = 0;
    private boolean gameOver;
    private int minesCount = 10;
    private Boolean lostGame = null;
    private boolean levelsEnabled;

    {
        frame = new JFrame(GAME_NAME);
        textLabel = new JLabel();
        board = new MineTile[DEFAULT_NUM_ROWS][DEFAULT_NUM_COLUMNS];
        mineList = new ArrayList<>();
    }

    public MineSweeper(boolean levelsEnabled) {
        this.levelsEnabled = levelsEnabled;
        configureFrame();
        configureTextLabel();

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLUMNS));

        frame.add(boardPanel);

        fillBoard(boardPanel);

        frame.setVisible(true);

        setMines();
    }

    private JButton configureRestartButton() {
        JButton restartButton = new JButton("Restart the game");
        restartButton.addActionListener(e -> restartGame());
        return restartButton;
    }

    private void restartGame() {
        if (levelsEnabled) {
            if (lostGame != null && lostGame) {
                // if you lose - go to the previous level
                if (minesCount > 2) {
                    minesCount--;
                }
            } else if (lostGame != null) {
                // if you win - go to the next level
                if (minesCount < 64) {
                    minesCount++;
                }
            }
        }
        lostGame = null;
        tilesClicked = ZERO;
        gameOver = false;
        textLabel.setText(GAME_NAME + SEPARATOR + minesCount + " mines");

        // Reset mine tiles
        for (MineTile[] row : board) {
            for (MineTile tile : row) {
                tile.setText(EMPTY);
                tile.setEnabled(true);
            }
        }

        // Reset mine list and set new mine positions
        mineList.clear();
        setMines();
    }

    private void fillBoard(JPanel boardPanel) {
        for (int rowNumber = 0; rowNumber < DEFAULT_NUM_ROWS; rowNumber++) {
            for (int columnNumber = 0; columnNumber < DEFAULT_NUM_COLUMNS; columnNumber++) {
                MineTile tile = new MineTile(rowNumber, columnNumber);
                board[rowNumber][columnNumber] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(ZERO, ZERO, ZERO, ZERO));
                tile.setFont(new Font(FONT_NAME + " Unicode MS", Font.PLAIN, FOURTY_FIVE));
                tile.addMouseListener(new CustomMouseAdapter(tile));
                boardPanel.add(tile);
            }
        }
    }

    private JPanel configureTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        return textPanel;
    }

    private void configureTextLabel() {
        textLabel.setFont(new Font(FONT_NAME, Font.BOLD, TWENTY_FIVE));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(GAME_NAME + SEPARATOR + minesCount + " mines");
        textLabel.setOpaque(true);
    }

    private void configureFrame() {
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        // the config above will open the window at the middle of our screen
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // when you close the game, it will terminate the whole program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(configureTextPanel(), BorderLayout.NORTH);
        frame.add(configureRestartButton(), BorderLayout.SOUTH);
    }

    private void checkMine(int rowNumber, int columnNumber) {
        if (!isValid(rowNumber, columnNumber)) {
            return;
        }

        MineTile tile = board[rowNumber][columnNumber];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked++;
        int minesFound = getMinesFound(rowNumber, columnNumber);
        if (minesFound > ZERO) {
            tile.setText(Integer.toString(minesFound));
        } else {
            tile.setText(EMPTY);
            checkMine(rowNumber - ONE, columnNumber - ONE); // check top left
            checkMine(rowNumber - ONE, columnNumber); // check top
            checkMine(rowNumber - ONE, columnNumber + ONE); // check top right
            checkMine(rowNumber, columnNumber - ONE); // check left
            checkMine(rowNumber, columnNumber + ONE); // right
            checkMine(rowNumber + ONE, columnNumber - ONE); // check bottom left
            checkMine(rowNumber + ONE, columnNumber); // check bottom
            checkMine(rowNumber + ONE, columnNumber + ONE); // bottom right
        }

        if (tilesClicked == DEFAULT_NUM_ROWS * DEFAULT_NUM_COLUMNS - mineList.size()) {
            gameOver = true;
            // if you won - go to the next level
            textLabel.setText("Mines cleared! You won");
        }
    }

    private int getMinesFound(int rowNumber, int columnNumber) {
        int minesFound = ZERO;

        minesFound += countMines(rowNumber - ONE, columnNumber - ONE); // check top left
        minesFound += countMines(rowNumber - ONE, columnNumber); // check top
        minesFound += countMines(rowNumber - ONE, columnNumber + ONE); // check top right
        minesFound += countMines(rowNumber, columnNumber - ONE); // check left
        minesFound += countMines(rowNumber, columnNumber + ONE); // right
        minesFound += countMines(rowNumber + ONE, columnNumber - ONE); // check bottom left
        minesFound += countMines(rowNumber + ONE, columnNumber); // check bottom
        minesFound += countMines(rowNumber + ONE, columnNumber + ONE); // bottom right
        return minesFound;
    }

    private int countMines(int rowNumber, int columnNumber) {
        if (!isValid(rowNumber, columnNumber)) {
            return ZERO;
        }
        if (mineList.contains(board[rowNumber][columnNumber])) {
            return ONE;
        }
        return ZERO;
    }

    private boolean isValid(int rowNumber, int columnNumber) {
        if (rowNumber < ZERO
                || rowNumber >= DEFAULT_NUM_ROWS
                || columnNumber < ZERO
                || columnNumber >= DEFAULT_NUM_COLUMNS) {
            return false;
        }
        return true;
    }

    private void revealMines() {
        for (MineTile mineTile : mineList) {
            mineTile.setText(BOMB_IMAGE);
        }
        gameOver = true;
        lostGame = true;
        textLabel.setText("Game over! You lost");
    }

    private void setMines() {
        int minesLeft = minesCount;
        Random random = new Random();
        while (minesLeft > ZERO) {
            int row = random.nextInt(DEFAULT_NUM_ROWS);
            int column = random.nextInt(DEFAULT_NUM_COLUMNS);
            MineTile tile = board[row][column];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                minesLeft--;
            }
        }
    }

    private class CustomMouseAdapter extends MouseAdapter {
        private MineTile tile;

        public CustomMouseAdapter(MineTile tile) {
            this.tile = tile;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (gameOver) {
                return;
            }
            MineTile tilePressed = (MineTile) e.getSource();
            // left click
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (tilePressed.getText().isEmpty()) {
                    if (mineList.contains(tilePressed)) {
                        revealMines();
                    } else {
                        checkMine(tilePressed.getRowNumber(), tilePressed.getColumnNumber());
                    }
                }
                // right click
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (tilePressed.getText().isEmpty() && tilePressed.isEnabled()) {
                    tile.setText(BLOCK_IMAGE);
                } else if (tilePressed.getText().equals(BLOCK_IMAGE)) {
                    tile.setText(EMPTY);
                }
            }
        }
    }
}
