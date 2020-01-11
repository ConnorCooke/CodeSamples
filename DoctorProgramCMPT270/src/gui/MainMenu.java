package gui;
import javax.swing.JFrame;

public class MainMenu extends JFrame {

    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 350;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 260;

    /**
     * Creates a frame for the primary operations for the system
     */
    public MainMenu(){
        setTitle("Main Menu");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        MainMenuPanel panel = new MainMenuPanel();
        add(panel);
    }

}
