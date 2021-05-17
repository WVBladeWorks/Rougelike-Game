import javax.swing.*;

public class ReplayDriver {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Replay");
        frame.setContentPane(new GraphicsHandler());
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setSize(1280, 1280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
