package mine.sweeper.holder;

public interface ConstantsHolder {
    String GAME_NAME = "Minesweeper";
    int TILE_SIZE = 70;
    int DEFAULT_NUM_ROWS = 8;
    int DEFAULT_NUM_COLUMNS = 8;
    int BOARD_WIDTH = DEFAULT_NUM_COLUMNS * TILE_SIZE;
    int BOARD_HEIGHT = DEFAULT_NUM_ROWS * TILE_SIZE;
    String FONT_NAME = "Arial";
    int TWENTY_FIVE = 25;
    String BLOCK_IMAGE = "🚫";
    String EMPTY = "";
    int ZERO = 0;
    String BOMB_IMAGE = "💣";
    int ONE = 1;
    int FORTY_FIVE = 45;
    String DASH_SEPARATOR = " - ";
}
