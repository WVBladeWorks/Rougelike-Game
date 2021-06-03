import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Replay");
        frame.setContentPane(new GraphicsHandler());
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.revalidate();
    }
}
