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
        platforms = new LinkedList<>();
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
                for (Rectangle rect : platforms)
                {
                    rect.x -= 1;
                    if (rect.x <= -1 * PLATFORM_WIDTH)
                        platforms.remove(rect);
                }
            }

            //Spawn Platforms
            if (tickCount % 1000 == 0)
                platforms.add(new Rectangle(1280, 100 * (rng.nextInt(10) + 1)));

            this.repaint();
            tickCount++;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        for (Rectangle rect : platforms)
        {
            g.setColor(Color.BLACK);
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }
    }
}
