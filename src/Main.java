import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args)
    {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Chrono-Fall");
        frame.setContentPane(new Title());
        //frame.setContentPane(new GraphicsHandler());
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}
