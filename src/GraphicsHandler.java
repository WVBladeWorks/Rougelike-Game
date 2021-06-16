import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import java.util.HashSet;
import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;

public class GraphicsHandler extends JPanel implements ActionListener
{
    private Random rng;
    private LinkedList<Rectangle> platforms;
    private LinkedList<Fireball> fireballs, homingFireballs;
    private HashSet<Integer> keys;
    private final int PLATFORM_VELOCITY, PLATFORM_WIDTH, PLATFORM_HEIGHT, G_ACCEL, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_X_VELOCITY, PLAYER_JUMP_VELOCITY, TERMINAL_VELOCITY, MAX_JUMPS, BOSS_WIDTH, BOSS_HEIGHT, BOSS_SPEED, FIREBALL_SPEED, HOMING_SPEED, ARC_SPREAD;
    private Timer timer;
    private int frameCount, attackFrame, bossAttackFrame, bossRestFrame, deadFrame, deathCount;
    private Player player;
    private Boss boss;
    private DeadBody deadBody;
    private Dimension screenSize;
    private Image platform;
    private Image backgroundImage;
    private final boolean DEBUG = false;
    private File tracks;
    private Clip clip, collect, jump, swordMiss, swordHit, fireball, bossMelee;
    private AudioInputStream audioIn;
    private FloatControl volume;

    public GraphicsHandler()
    {
        //Set Variables
        this.setLayout(null);
        rng = new Random();
        keys = new HashSet<>();
        platforms = new LinkedList<>();
        fireballs = new LinkedList<>();
        homingFireballs = new LinkedList<>();
        PLATFORM_VELOCITY = -3;
        PLATFORM_WIDTH = 150;
        PLATFORM_HEIGHT = 25;
        G_ACCEL = 25;
        PLAYER_X_VELOCITY = 10;
        PLAYER_JUMP_VELOCITY = -14;
        BOSS_WIDTH = 256;
        BOSS_HEIGHT = 176;
        BOSS_SPEED = 25;
        FIREBALL_SPEED = -10;
        HOMING_SPEED = 5;
        ARC_SPREAD = 65;
        MAX_JUMPS = 2;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        TERMINAL_VELOCITY = screenSize.height / 60;
        PLAYER_WIDTH = 100;
        PLAYER_HEIGHT = 67;
        if (DEBUG)
            System.out.println("Screen Dimensions: " + screenSize.width + ", " + screenSize.height);
        player = new Player(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
        boss = new Boss(screenSize.width - BOSS_WIDTH, 0, BOSS_WIDTH, BOSS_HEIGHT);
        deadBody = new DeadBody(-1000, -1000, PLAYER_WIDTH, 74, 1);
        timer = new Timer(34, this);
        frameCount = 0;
        attackFrame = 0;
        bossAttackFrame = 0;
        bossRestFrame = 0;
        deadFrame = 0;
        backgroundImage = Toolkit.getDefaultToolkit().getImage("Misc/DarkForest.png");
        platform = Toolkit.getDefaultToolkit().getImage("Misc/Platforms.png");
      
        //Starting Platform
        platforms.add(new Rectangle(0, screenSize.height*9/10, screenSize.width, PLATFORM_HEIGHT));

        //Keybinds
        this.setFocusable(true);
        InputMap inputs = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "JUMP");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "CROUCH");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "ATTACK");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "REL LEFT");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "REL RIGHT");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "REL JUMP");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "REL CROUCH");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "REL ATTACK");

        ActionMap actions = this.getActionMap();
        actions.put("LEFT", new KeyPressed(KeyEvent.VK_LEFT));
        actions.put("RIGHT", new KeyPressed(KeyEvent.VK_RIGHT));
        actions.put("JUMP", new KeyPressed(KeyEvent.VK_UP));
        actions.put("CROUCH", new KeyPressed(KeyEvent.VK_DOWN));
        actions.put("ATTACK", new KeyPressed(KeyEvent.VK_SPACE));
        actions.put("REL LEFT", new KeyReleased(KeyEvent.VK_LEFT));
        actions.put("REL RIGHT", new KeyReleased(KeyEvent.VK_RIGHT));
        actions.put("REL JUMP", new KeyReleased(KeyEvent.VK_UP));
        actions.put("REL CROUCH", new KeyReleased(KeyEvent.VK_DOWN));
        actions.put("REL ATTACK", new KeyReleased(KeyEvent.VK_SPACE));

        //Initialize frame
        this.add(player.getComponent());
        this.add(boss.getComponent());
        this.add(deadBody.getComponent());
        this.repaint();
        timer.start();

        //Audio
        try
        {
            //Music
            tracks = Path.of("Misc/Battle.wav").toAbsolutePath().toFile();
            audioIn = AudioSystem.getAudioInputStream(tracks.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
            //Collect
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Collect.wav").toAbsolutePath().toFile().toURI().toURL());
            collect = AudioSystem.getClip();
            collect.open(audioIn);
            volume = (FloatControl) collect.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);
            /*
            //Jump
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Jump.wav").toAbsolutePath().toFile().toURI().toURL());
            jump = AudioSystem.getClip();
            jump.open(audioIn);
            volume = (FloatControl) jump.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);*/

            //Sword Miss
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Sword_Miss.wav").toAbsolutePath().toFile().toURI().toURL());
            swordMiss = AudioSystem.getClip();
            swordMiss.open(audioIn);
            volume = (FloatControl) swordMiss.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);

            //Sword Hit
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Sword_Hit.wav").toAbsolutePath().toFile().toURI().toURL());
            swordHit = AudioSystem.getClip();
            swordHit.open(audioIn);
            volume = (FloatControl) swordHit.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);
            /*
            //Boss Melee
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Boss_Melee.wav").toAbsolutePath().toFile().toURI().toURL());
            bossMelee = AudioSystem.getClip();
            bossMelee.open(audioIn);
            volume = (FloatControl) bossMelee.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20);

            //Fireball
            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Fireball.wav").toAbsolutePath().toFile().toURI().toURL());
            fireball = AudioSystem.getClip();
            fireball.open(audioIn);*/

        }

        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
          e.printStackTrace();
        }
        //*/
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == timer)
        {
            //Move platforms left
            LinkedList<Rectangle> delPlatforms = new LinkedList<>();
            for (Rectangle rect : platforms)
            {
                rect.x += PLATFORM_VELOCITY;
                if (rect.x <= -1 * rect.width)
                {
                    delPlatforms.add(rect);
                }
            }
            for (Rectangle rect : delPlatforms)
            {
                platforms.remove(rect);
            }
            
            //Homing fireballs
            for (Fireball homing : homingFireballs)
            {
                if (player.getY() - homing.getY() < 0 && Math.abs(player.getY() - homing.getY()) >= PLAYER_HEIGHT)
                    homing.setYVelocity(-1 * HOMING_SPEED);
                else if (player.getY() - homing.getY() > 0 && Math.abs(player.getY() - homing.getY()) >= PLAYER_HEIGHT)
                    homing.setYVelocity(HOMING_SPEED);
                else
                    homing.setYVelocity(0);
            }

            //Add fireballs
            for (Fireball ball : fireballs)
                this.add(ball.getComponent());

            //Move fireballs
            LinkedList<Fireball> delFireballs = new LinkedList<>();
            for (Fireball ball : fireballs)
            {
                ball.translate(ball.getXVelocity(), ball.getYVelocity());
                if (ball.getX() <= -1 * ball.getWidth())
                {
                    delFireballs.add(ball);
                }
            }
            for (Fireball ball : delFireballs)
            {
                fireballs.remove(ball);
                if (ball.isHoming())
                    homingFireballs.remove(ball);
                this.remove(ball.getComponent());
            }

            //Move Dead Body Left
            deadBody.translate(deadBody.getXVelocity(), deadBody.getYVelocity());

            //Spawn Platforms, Checks for Platform Collision
            if (frameCount % 30 == 0)
            {
                Rectangle newPlatform = new Rectangle(screenSize.width, 100 + screenSize.height/10 * rng.nextInt(10), PLATFORM_WIDTH, PLATFORM_HEIGHT);
                boolean platCollision = false;
                for (Rectangle rect : platforms)
                {
                    if (newPlatform.intersects(rect))
                    platCollision = true;
                }
                if (!platCollision)
                    platforms.add(newPlatform);
            }

            //Gravity
            if (!player.isGrounded() && player.getYVelocity() <= TERMINAL_VELOCITY)
            {
              player.setYVelocity(player.getYVelocity() + 1);
            }

            //Collision Detection
            boolean playerCollision = false;
            for (Rectangle rect : platforms)
            {
              if (player.getBounds().intersects(rect))
              {
                if (player.getY() <= rect.y - PLAYER_HEIGHT + TERMINAL_VELOCITY + 1 && player.getBounds().intersection(rect).y == rect.y && player.getX() + 30 <= rect.x + rect.width && player.getX() + 75 >= rect.x && player.getYVelocity() >= 0 && !player.isCrouching())
                {
                    playerCollision = true;
                    player.setYVelocity(0);
                    player.setGrounded(true);
                }
                if (DEBUG)
                {
                    //System.out.println(player.getY() + ", " + rect.y + "," + (rect.y-PLAYER_HEIGHT + TERMINAL_VELOCITY + 1));
                    /*
                    System.out.println("Overall: " + playerCollision);
                    System.out.println("Vertical: " + (player.getY() <= rect.y - PLAYER_HEIGHT + TERMINAL_VELOCITY + 1));
                    System.out.println("Intersection: " + (player.getBounds().intersection(rect).y == rect.y));
                    System.out.println("Right: " + (player.getX() + 30 <= rect.x + rect.width));
                    System.out.println("Left: " + (player.getX() + 75 >= rect.x));
                    System.out.println("Falling: " + (player.getYVelocity() >= 0));
                    System.out.println("Crouching: " + (!player.isCrouching()));
                    System.out.println("------------------------------------------------");
                    //*/
                }
              }
            }
            if (!playerCollision)
            {
                player.setXVelocity(0);
                player.setGrounded(false);
            }

            //Body Collision
            if (player.getBounds().intersects(deadBody.getBounds()))
            {
                player.increaseDmg(deadBody.getFactor());
                deadBody.setLocation(-1000, -1000);
                collect.loop(0);
                if (collect.getFramePosition() >= collect.getFrameLength() - 1)
                    try
                    {
                        collect.close();
                        audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Collect.wav").toAbsolutePath().toFile().toURI().toURL());
                        collect = AudioSystem.getClip();
                        collect.open(audioIn);
                        volume = (FloatControl) collect.getControl(FloatControl.Type.MASTER_GAIN);
                        volume.setValue(-20);
                    }
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                    {
                        ex.printStackTrace();
                    }
            }

            //Fireball Collision
            for (Fireball ball : fireballs)
            {
                if (ball.getBounds().intersects(player.getBounds()) && !player.getDead())
                {
                    player.setDead(true);
                    /*fireball.loop(0);
                    if (fireball.getFramePosition() >= fireball.getFrameLength() - 1)
                        try
                        {
                            fireball.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Fireball.wav").toAbsolutePath().toFile().toURI().toURL());
                            fireball = AudioSystem.getClip();
                            fireball.open(audioIn);
                            deadFrame = frameCount;
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }*/
                }
            }

            //Input-Based Movement
            if (keys.contains(KeyEvent.VK_LEFT))
                player.setXVelocity(-1 * PLAYER_X_VELOCITY);
            if (keys.contains(KeyEvent.VK_RIGHT))
                player.setXVelocity(PLAYER_X_VELOCITY);
            if (!keys.contains(KeyEvent.VK_LEFT) && !keys.contains(KeyEvent.VK_RIGHT))
                player.setXVelocity(0);
            if (keys.contains(KeyEvent.VK_UP) && player.getJumps() > 0)
            {
                if (player.getYVelocity() <= 0)
                    player.setYVelocity(player.getYVelocity() + PLAYER_JUMP_VELOCITY);
                else
                    player.setYVelocity(PLAYER_JUMP_VELOCITY);
                player.jump();
            }
            if (keys.contains(KeyEvent.VK_DOWN))
            {
                player.setCrouching(true);
            }
            else
            {
                player.setCrouching(false);
            }

            //Player Location Setting
            if (!player.getDead() && player.getXVelocity() != 0 && (player.getX() > 0 || player.getXVelocity() > 0) && (player.getX() + PLAYER_WIDTH <= boss.getX() + 85 || player.getXVelocity() < 0))
            {
                player.translate(player.getXVelocity(), 0);
            }
            if (player.getYVelocity() != 0)
            {
                player.translate(0, player.getYVelocity());
            }

            //Boss Direction
            if (player.getY() - boss.getY() < 0 && Math.abs(player.getY() - boss.getY()) >= 2*BOSS_SPEED && boss.getY() > 0)
                boss.setYVelocity(-1 * BOSS_SPEED);
            else if (player.getY() - boss.getY() > 0 && Math.abs(player.getY() - boss.getY()) >= 2*BOSS_SPEED && boss.getY() + BOSS_HEIGHT < screenSize.height)
                boss.setYVelocity(BOSS_SPEED);
            else
                boss.setYVelocity(0);

            //Boss Location Setting
            if (boss.getYVelocity() != 0)
            {
                boss.translate(0, boss.getYVelocity());
            }

            //Choose Boss Attack
            if (frameCount >= 90 && frameCount - bossRestFrame >= 30 && boss.getState() == 0 && player.getBounds().x + player.getBounds().width >= boss.getX() - 50)
            {
                boss.setState(1);
                bossAttackFrame = frameCount;
            }
            else if (frameCount >= 150 && frameCount - bossRestFrame >= 10 && boss.getState() == 0)
            {
                int randAttack = rng.nextInt(3) + 2;
                boss.setState(randAttack);
                bossAttackFrame = frameCount;
            }

            //Boss Melee Attack
            if(boss.getState() == 1)
            {
                /*bossMelee.loop(0);
                if (bossMelee.getFramePosition() >= bossMelee.getFrameLength() - 1)
                    try
                    {
                        bossMelee.close();
                        audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Boss_Melee.wav").toAbsolutePath().toFile().toURI().toURL());
                        bossMelee = AudioSystem.getClip();
                        bossMelee.open(audioIn);
                        volume = (FloatControl) bossMelee.getControl(FloatControl.Type.MASTER_GAIN);
                        volume.setValue(-20);
                    }
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                    {
                        ex.printStackTrace();
                    }*/
                if (frameCount - bossAttackFrame == 50 && player.getBounds().intersects(new Rectangle(boss.getX() - 100, boss.getY() - 100, 200, boss.getHeight() + 200)) && !player.getDead())
                {
                    player.setDead(true);
                    deadFrame = frameCount;
                }
                else if (frameCount - bossAttackFrame >= 60)
                {
                    boss.setState(0);
                    bossRestFrame = frameCount;
                }
            }

            //Boss Straight Attack
            if(boss.getState() == 2)
            {
                if (frameCount - bossAttackFrame == 20)
                {
                    fireballs.add(new Fireball(boss.getBounds().x, boss.getY(), FIREBALL_SPEED));
                    /*fireball.loop(0);
                    if (fireball.getFramePosition() >= fireball.getFrameLength() - 1)
                        try
                        {
                            fireball.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Fireball.wav").toAbsolutePath().toFile().toURI().toURL());
                            fireball = AudioSystem.getClip();
                            fireball.open(audioIn);
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }*/

                }
                else if (frameCount - bossAttackFrame >= 60)
                {
                    boss.setState(0);
                    bossRestFrame = frameCount;
                }
            }

            //Boss Homing Attack
            if(boss.getState() == 3)
            {
                if (frameCount - bossAttackFrame == 20)
                {
                    Fireball homing = new Fireball(boss.getBounds().x, boss.getY(), FIREBALL_SPEED, true);
                    fireballs.add(homing);
                    homingFireballs.add(homing);
                    /*fireball.loop(0);
                    if (fireball.getFramePosition() >= fireball.getFrameLength() - 1)
                        try
                        {
                            fireball.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Fireball.wav").toAbsolutePath().toFile().toURI().toURL());
                            fireball = AudioSystem.getClip();
                            fireball.open(audioIn);
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }*/
                }
                else if (frameCount - bossAttackFrame >= 60)
                {
                    boss.setState(0);
                    bossRestFrame = frameCount;
                }
            }

            //Boss Arc Attack
            if(boss.getState() == 4)
            {
                if (frameCount - bossAttackFrame == 20)
                {
                    fireballs.add(new Fireball(boss.getBounds().x, boss.getY(), FIREBALL_SPEED));
                    fireballs.add(new Fireball(boss.getBounds().x, boss.getY() - ARC_SPREAD, FIREBALL_SPEED));
                    fireballs.add(new Fireball(boss.getBounds().x, boss.getY() + ARC_SPREAD, FIREBALL_SPEED));
                    /*fireball.loop(0);
                    if (fireball.getFramePosition() >= fireball.getFrameLength() - 1)
                        try
                        {
                            fireball.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Fireball.wav").toAbsolutePath().toFile().toURI().toURL());
                            fireball = AudioSystem.getClip();
                            fireball.open(audioIn);
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }*/
                }
                else if (frameCount - bossAttackFrame >= 60)
                {
                    boss.setState(0);
                    bossRestFrame = frameCount;
                }
            }

            //Player Attack
            if (!player.getDead() && !player.isAttacking() &&keys.contains(KeyEvent.VK_SPACE) && player.getXVelocity() == 0)
            {
                player.setAttacking(true);
                attackFrame = frameCount;
            }
            else if (frameCount - attackFrame >= 30 || player.getXVelocity() != 0)
            {
                player.setAttacking(false);
            }

            if (player.isAttacking() && frameCount - attackFrame == 15)
            {
                int damage = player.attack(boss.getBounds());
                if (damage > 0)
                {
                    boss.takeDamage(damage);
                    swordHit.loop(0);
                    if (swordHit.getFramePosition() >= swordHit.getFrameLength() - 1)
                        try
                        {
                            swordHit.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Sword_Hit.wav").toAbsolutePath().toFile().toURI().toURL());
                            swordHit = AudioSystem.getClip();
                            swordHit.open(audioIn);
                            volume = (FloatControl) swordHit.getControl(FloatControl.Type.MASTER_GAIN);
                            volume.setValue(-20);
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }
                }
                else
                {
                    swordMiss.loop(0);
                    if (swordMiss.getFramePosition() >= swordMiss.getFrameLength()/2)
                        try
                        {
                            swordMiss.close();
                            audioIn = AudioSystem.getAudioInputStream(Path.of("Misc/Sword_Miss.wav").toAbsolutePath().toFile().toURI().toURL());
                            swordMiss = AudioSystem.getClip();
                            swordMiss.open(audioIn);
                            volume = (FloatControl) swordMiss.getControl(FloatControl.Type.MASTER_GAIN);
                            volume.setValue(-20);
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
                        {
                            ex.printStackTrace();
                        }
                }
                
                if (boss.getHP() <= 0)
                {
                    clip.stop();
                    clip.close();
                    collect.stop();
                    collect.close();
                    //jump.stop();
                    //jump.close();
                    //fireball.stop();
                    //fireball.close();
                    //bossMelee.stop();
                    //bossMelee.close();
                    swordHit.stop();
                    swordHit.close();
                    swordMiss.stop();
                    swordMiss.close();
                    JFrame frame = new JFrame("Chrono-Fall");
                    frame.setContentPane(new Victory(this));
                    frame.setFocusable(true);
                    frame.setFocusTraversalKeysEnabled(false);
                    frame.setSize(screenSize.width, screenSize.height);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            }

            //Player Death
            if (player.getY() >= screenSize.height + 2*PLAYER_HEIGHT)
            {
                player.setDead(true);
                deadFrame = 0;
            }
            
            //Reset After Death
            if (player.getDead() && frameCount - deadFrame >= 15)
            {
                player.setDead(false);
                //Set Variables
                this.setLayout(null);
                rng = new Random();
                keys = new HashSet<>();
                platforms = new LinkedList<>();
                for (Fireball ball : fireballs)
                {
                    this.remove(ball.getComponent());
                }
                fireballs = new LinkedList<>();
                homingFireballs = new LinkedList<>();
                screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                if (DEBUG)
                    System.out.println("Screen Dimensions: " + screenSize.width + ", " + screenSize.height);
                player.setLocation(0, 0);
                boss.setLocation(screenSize.width - BOSS_WIDTH, 0);
                deadBody.setLocation(3 * screenSize.width / 4, screenSize.height*9/10 - PLAYER_HEIGHT);
                deadBody.setFactor(1 + frameCount / 90.);
                frameCount = 0;
                attackFrame = 0;
                bossAttackFrame = 0;
                bossRestFrame = 0;
                deadFrame = 0;
                deathCount += 1;
                
            
                //Starting Platform
                platforms.add(new Rectangle(0, screenSize.height*9/10, screenSize.width, PLATFORM_HEIGHT));
            }
            //Debug Panel
            if (DEBUG)
            {
                //System.out.println(player.getYVelocity());
                //System.out.println(player.getX() + "," + player.getY());
                //System.out.println(player.getComponent().getIcon().getIconWidth());
                //System.out.println(boss.getComponent().getIcon().getIconWidth());
                //System.out.println(boss.getComponent().getBounds().height);
                //System.out.println(boss.getX() + "," + boss.getY());
            }
            player.checkAnimation();
            boss.checkAnimation();
            this.repaint();
            frameCount += 1;
        }
    }

  public int getDeaths()
  {
    return deathCount;
  }

  /**
	 * Represents the action of pressing a key.
	 */
	public class KeyPressed extends AbstractAction
	{
		private final int keyCode;

		/**
		 * Creates the event of pressing a given key.
		 *
		 * @param keyCode the key's integer code as specified by the <code>KeyEvent</code> class.
		 */
		public KeyPressed(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e the event to be processed
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.add(keyCode);
		}
	}

	/**
	 * Represents the action of releasing a key.
	 */
	public class KeyReleased extends AbstractAction
	{
		private final int keyCode;

		/**
		 * Creates the event of releasing a given key.
		 *
		 * @param keyCode the key's integer code as specified by the <code>KeyEvent</code> class.
		 */
		public KeyReleased(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e the event to be processed
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.remove(keyCode);
		}
	}

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //Background
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        //PLatforms
        g.setColor(Color.WHITE);
        for (Rectangle rect : platforms)
        {
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
            g.drawImage(platform, rect.x, rect.y, rect.width, rect.height, this);
        }

        //Boss HP Bar
        int hp = boss.getHP();
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, (int)((screenSize.width - 20) * hp / 100000.), 35);
        if (hp >= 50000)
            g.setColor(new Color((int)(255 * (100000 - hp) / 50000), 255, 0));
        else if (hp >= 0)
            g.setColor(new Color(255, (int)(255 * hp / 50000), 0));
        g.fillRect(15, 15, (int)((screenSize.width - 20) * hp / 100000.) - 10, 25);
    }
}
