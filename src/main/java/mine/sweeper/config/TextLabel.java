package mine.sweeper.config;

import static mine.sweeper.holder.ConstantsHolder.DASH_SEPARATOR;
import static mine.sweeper.holder.ConstantsHolder.FONT_NAME;
import static mine.sweeper.holder.ConstantsHolder.GAME_NAME;
import static mine.sweeper.holder.ConstantsHolder.TWENTY_FIVE;

import java.awt.Font;
import javax.swing.JLabel;

public class TextLabel extends JLabel {

    public TextLabel(int minesCount) {
        this.setFont(new Font(FONT_NAME, Font.BOLD, TWENTY_FIVE));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setText(GAME_NAME + DASH_SEPARATOR + minesCount + " mines");
        this.setOpaque(true);
    }
}
