import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.HashSet;


public class Victory extends JPanel 
{
    private JFrame frame = new JFrame("Replay");
    private final Dimension screenSize;
    private final Image backgroundImage;
    private final JLabel winLabel;
    private final JLabel deathCount;
    private final String[] credits = {"Credits: ", "Managers: Winston Lee, Anthony Armijos", "General Employees: Shawn Corcoran, Lavleen Madahar"};
    private DeadBody deadBody;
    private File tracks;
    private Clip clip;

    //Needs: Credits, Victory, Numbers of deaths, 
    
    public Victory(GraphicsHandler deaths) {
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    backgroundImage = Toolkit.getDefaultToolkit().getImage("Misc/DarkForest.png");
    winLabel = new JLabel("Congratulations! You won!!!");
    winLabel.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/10));
    winLabel.setForeground(Color.WHITE);
    winLabel.setBounds(screenSize.width/12,
    -screenSize.height/4,3 * screenSize.width/2,screenSize.height);
    deathCount = new JLabel("Number of Deaths: " + String.valueOf(deaths.getDeaths()));
    deathCount.setForeground(Color.WHITE);
    deathCount.setBounds(screenSize.width/12, screenSize.height/3, 3 * screenSize.width/2,screenSize.height);
    this.add(deathCount);
    this.add(winLabel);
    //System.out.println(screenSize.height);
    //500

     try
        {
            tracks = Path.of("Misc/Final Fantasy VII - Victory Fanfare [HQ].wav").toAbsolutePath().toFile();
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

         //Initialize frame
        this.setLayout(null);
  }
 
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

      //Credits
      for(int i = 0; i < credits.length; i++){
        g.setColor(Color.WHITE);
        g.setFont(new Font("MONOSPACED", Font.BOLD, screenSize.height/20));
        g.drawString(credits[i], screenSize.width * 1/10, screenSize.height/2 + (i * 40));
        }
    }
    
}