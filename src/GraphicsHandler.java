import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class GraphicsHandler extends JPanel implements ActionListener
{
    private Random rng;
    private LinkedList<Rectangle> platforms;
    private final int PLATFORM_VELOCITY, PLATFORM_WIDTH, PLATFORM_HEIGHT;
    private Timer timer;
    private int tickCount;

    public GraphicsHandler()
    {
        rng = new Random();
        platforms = new LinkedList<Rectangle>();
        PLATFORM_VELOCITY = 10;
        PLATFORM_WIDTH = 100;
        PLATFORM_HEIGHT = 50;
        platforms.add(new Rectangle(1280, 1000, PLATFORM_WIDTH, PLATFORM_HEIGHT));
        timer = new Timer(1, this);
        tickCount = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == timer)
        {
            //Move platforms left
            if (tickCount % (1000/PLATFORM_VELOCITY) == 0)
            {
                platforms.forEach(rect -> rect.x -= 1);
            }


            tickCount++;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {

    }
}
