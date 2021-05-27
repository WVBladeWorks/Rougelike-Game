import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import java.util.HashSet;

public class GraphicsHandler extends JPanel implements ActionListener
{
    private Random rng;
    private LinkedList<Rectangle> platforms;
    private HashSet<Integer> keys;
    private final int PLATFORM_VELOCITY, PLATFORM_WIDTH, PLATFORM_HEIGHT, G_ACCEL, PLAYER_X_VELOCITY, PLAYER_JUMP_VELOCITY;
    private Timer timer;
    private int tickCount;
    private Player player;
    private Image platform;
    private Image backgroundImage;
    //900width by 587height

    public GraphicsHandler()
    {
        rng = new Random();
        keys = new HashSet<>();
        platforms = new LinkedList<>();
        PLATFORM_VELOCITY = 50;
        PLATFORM_WIDTH = this.getWidth * 1/10;
        PLATFORM_HEIGHT = this.getHeight * 1/30;
        G_ACCEL = 25;
        PLAYER_X_VELOCITY = 50;
        PLAYER_JUMP_VELOCITY = 50;
        platforms.add(new Rectangle(600, 1000, PLATFORM_WIDTH, PLATFORM_HEIGHT));
        player = new Player(0, 0, 100, 100);
        timer = new Timer(1, this);
        tickCount = 0;
        player = new Player();
        backgroundImage = Toolkit.getDefaultToolkit().getImage("Misc/DarkForest.png");
        platform = Toolkit.getDefaultToolkit().getImage("Misc/Platforms.png");
        this.add(player.getComponent());
        this.repaint();
        timer.start();
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
            //Gravity
            if (tickCount % (1000/G_ACCEL) == 0)
            {
              if (player.isGrounded() == false)
              {
                player.setYVelocity(player.getYVelocity() - 5);
              }
            }

            //Spawn Platforms
            if (tickCount % 1000 == 0)
                platforms.add(new Rectangle(600, 100 * (rng.nextInt(10) + 1), PLATFORM_WIDTH, PLATFORM_HEIGHT));

            //Move Player Left/Right
            //Exception in thread: if (tickCount % (1000/Math.abs(player.getXVelocity())) == 0)
            if(keys.contains(KeyEvent.VK_D))
              player.setXVelocity(PLAYER_X_VELOCITY);
            else if(keys.contains(KeyEvent.VK_A))
              player.setXVelocity(-1 * PLAYER_X_VELOCITY);

            //Start Jump
            if (keys.contains(KeyEvent.VK_SPACE))
              player.setYVelocity(PLAYER_JUMP_VELOCITY);
            
            player.checkAnimation();
            this.repaint();
            tickCount += 1;
        }
    }

    public class KAdapter extends KeyAdapter
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        keys.add(e.getKeyCode());
      }

      @Override
      public void keyReleased(KeyEvent e)
      {
        keys.remove(e.getKeyCode());
      }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        
        g.setColor(Color.BLACK);
        for (Rectangle rect : platforms)
        {
            g.drawRect(rect.x, rect.y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
            //g.drawImage(platform, rect.x, rect.y, this);
        }
      

    }
}
