package mine.sweeper.config;

import static mine.sweeper.holder.ConstantsHolder.BOARD_HEIGHT;
import static mine.sweeper.holder.ConstantsHolder.BOARD_WIDTH;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame(String gameName) throws HeadlessException {
        this.setName(gameName);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        // when you close the game, it will terminate the whole program
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }
}
