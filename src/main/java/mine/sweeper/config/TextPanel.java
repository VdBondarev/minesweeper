package mine.sweeper.config;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextPanel extends JPanel {

    public TextPanel(JLabel textLabel) {
        this.setLayout(new BorderLayout());
        this.add(textLabel);
    }
}
