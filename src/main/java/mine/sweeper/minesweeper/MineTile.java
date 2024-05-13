package mine.sweeper.minesweeper;

import javax.swing.JButton;

public class MineTile extends JButton {
    private int rowNumber;
    private int columnNumber;

    public MineTile(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
