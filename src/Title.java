import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.HashSet;


public class Title extends JPanel implements ActionListener
{
    private JFrame frame = new JFrame("Chrono-Fall");
    private final Dimension screenSize;
    private final HashSet<Integer> keys;
    private JLabel gameTitle;
    private final Image logoImage;
    private final Image playerImage;
    private final Image backgroundImage;
    private final Image gametitleImage;
    private final String[] options = {"Start", "Exit"};
    private final Timer timer;
    private int gameSection = 0;
    private JPanel credits;
    private int XMovement;
    private File tracks;
    private Clip clip;

    

    public Title()
    {
        keys = new HashSet<>();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        backgroundImage = Toolkit.getDefaultToolkit().getImage("Misc/DarkForest.png");
        logoImage = Toolkit.getDefaultToolkit().getImage("Misc/Logo.png");
        playerImage = Toolkit.getDefaultToolkit().getImage("Character/Character/- Gif/Run.gif");
        gametitleImage = Toolkit.getDefaultToolkit().getImage("Misc/SplashScreen.png");
       // managerLabel = new JLabel("Managers: Winston Lee, Anthony Armijos");
        //generalemployeesLabel = new JLabel("General Employees: Shawn Corcoran, Lavleen Madahar");
        timer = new Timer(140, this);
        //creditLabel.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/18));
        //creditLabel.setBounds(screenSize.width/3, -screenSize.height/2,500,500);
        //managerLabel.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/25));
        //managerLabel.setBounds(screenSize.width/5,-screenSize.height/2 + 42,750,500);
        //generalemployeesLabel.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/25));
        //generalemployeesLabel.setBounds(screenSize.width/5,-screenSize.height/2 + 82,750,500);
        //credits = new JPanel();
        XMovement = screenSize.width/2;
        try
        {
            tracks = Path.of("Misc/Hollow Knight OST - Crossroads.wav").toAbsolutePath().toFile();
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(tracks.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }

        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
          e.printStackTrace();
        }

      

        //Keybinds
        this.setFocusable(true);
        InputMap inputs = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "option Section (down)");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "option Section (up)");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Return");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "REL option Section (down)");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "REL option Section (up)");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "REL Enter");
        inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "REL Return");

        ActionMap actions = this.getActionMap();
        actions.put("option Section (down)", new KeyPressed(KeyEvent.VK_DOWN));
        actions.put("option Section (up)", new KeyPressed(KeyEvent.VK_UP));
        actions.put("Enter", new KeyPressed(KeyEvent.VK_ENTER));
        actions.put("Return", new KeyPressed(KeyEvent.VK_ESCAPE));
        actions.put("REL option Section (down)", new KeyReleased(KeyEvent.VK_DOWN));
        actions.put("REL option Section (up)", new KeyReleased(KeyEvent.VK_UP));
        actions.put("REL Enter", new KeyReleased(KeyEvent.VK_ENTER));
        actions.put("REL Return", new KeyReleased(KeyEvent.VK_ESCAPE));

        //Initialize frame
        this.setLayout(null);
        this.repaint();
        timer.start();

        //this.add(creditLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == timer) {
          if (keys.contains(KeyEvent.VK_DOWN)) {
            gameSection++;
             //System.out.println("TEST");
            if(gameSection >= options.length) {
              gameSection = 0;
              }
         }
          else if(keys.contains(KeyEvent.VK_UP))
          {
            gameSection--;
            if(gameSection < 0) {
              gameSection = 1;
              }
          }
          
          if(keys.contains(KeyEvent.VK_ENTER)) {
            if(gameSection == 0) {
              //System.exit(0);
              //frame.dispose();
              clip.stop();
              clip.close();
              frame.setContentPane(new GraphicsHandler());
              frame.setFocusable(true);
              frame.setFocusTraversalKeysEnabled(false);
              frame.setSize(screenSize.width, screenSize.height);
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
              keys.remove(KeyEvent.VK_ENTER);
            } 


            else if(gameSection == 1) {
              System.exit(0);
            } 
          }

          }
          //this.repaint();
    }

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
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(logoImage, 3 * screenSize.width/10, 0, screenSize.width/2, screenSize.height/5, this);
        //g.drawImage(playerImage, screenSize.width * 2/5, screenSize.height * 3/5, screenSize.width/4, screenSize.height/4, this);
        g.drawImage(playerImage, XMovement, 3 * screenSize.height/5, screenSize.width/4, screenSize.height/4, this);
        g.drawImage(gametitleImage, 2 * screenSize.width/15, screenSize.height/10, 17 * screenSize.width/20,3 * screenSize.height/8, this);

        //Menu Selection
        for(int i = 0; i < options.length; i++){
            if(i == gameSection)
                g.setColor(Color.RED);
            else
                g.setColor(Color.WHITE);
            //g.setFont(new Font("MONOSPACED", Font.BOLD, 20));
            //g.setFont(new Font("MONOSPACED", Font.BOLD, 40));
            g.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/16));
            g.drawString(options[i], screenSize.width * 3/8, screenSize.height * 9/16 + (i * 45));
        }
    }
}