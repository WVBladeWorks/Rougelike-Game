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
    private Dimension screenSize;
    private Image platform;
    private Image backgroundImage;
    //900width by 587height

    public GraphicsHandler()
    {
        rng = new Random();
        keys = new HashSet<>();
        platforms = new LinkedList<>();
        PLATFORM_VELOCITY = 250;
        PLATFORM_WIDTH = 100;
        PLATFORM_HEIGHT = 50;
        G_ACCEL = 25;
        PLAYER_X_VELOCITY = 50;
        PLAYER_JUMP_VELOCITY = 50;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      
      //Starting Platforms
        platforms.add(new Rectangle(0,  (screenSize.height*9/10), PLATFORM_WIDTH, PLATFORM_HEIGHT));
        player = new Player(0, screenSize.height*4/5, 100, 100);
        
        timer = new Timer(1, this);
        tickCount = 0;
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
                    {
                        platforms.remove(rect);
                        break;
                    }
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
                platforms.add(new Rectangle(screenSize.width, 100 + screenSize.height/10 * rng.nextInt(10), PLATFORM_WIDTH, PLATFORM_HEIGHT));

            //Player Location Setting
            if (player.getXVelocity() != 0 && tickCount % 1000/Math.abs(player.getXVelocity()) == 0)
            {
              System.out.println("DEBUG");
              player.translate((int)Math.signum((double)player.getXVelocity()), 0);
            }
            if (player.getYVelocity() != 0 && tickCount % 1000/Math.abs(player.getYVelocity()) == 0)
            {
              player.translate(0, (int)Math.signum((double)player.getYVelocity()));
            }


            //Move Player Left/Right
            if(keys.contains(KeyEvent.VK_RIGHT))
              player.setXVelocity(PLAYER_X_VELOCITY);
            else if(keys.contains(KeyEvent.VK_LEFT))
              player.setXVelocity(-1 * PLAYER_X_VELOCITY);

            //Collision Detection
            for (Rectangle rect : platforms)
            {
              if (player.getBounds().intersects(rect))
              {
                if (player.getBounds().intersection(rect).y == rect.y && player.getYVelocity() > 0)
                {
                  player.setYVelocity(0);
                  player.setGrounded(true);
                }
              }
            }

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
        
       //test this plz 
       g.drawRect(0, 0, screenSize.width, PLATFORM_HEIGHT);
       g.drawImage(platform, 0, 0, screenSize.width, PLATFORM_HEIGHT, this);


        g.setColor(Color.BLACK);
        for (Rectangle rect : platforms)
        {
            g.drawRect(rect.x, rect.y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
            g.drawImage(platform, rect.x, rect.y, PLATFORM_WIDTH, PLATFORM_HEIGHT, this);
        }
      

    }
}
