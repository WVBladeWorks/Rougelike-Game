import java.util.*;
import javax.swing.*;
import java.awt.*;
public class Player
{
	private JLabel player;
	private HashMap<String, ImageIcon> images;
	private boolean grounded = false;
	private boolean crouch = false;
	private boolean dogde = false;
  private int jumps = 2;
	private int XVel = 0;
	private int YVel = 0;
  private int health = 100;
  private final int PLATFORM_VELOCITY = 250;
  private int playerx;
  private int playery;
  private int playerwidth;
  private int player

	public Player(int x, int y, int width, int height)
	{
		player = new JLabel();
    player.setBounds(x, y, width, height);
		images = new HashMap<>();
		images.put("AirAttack", new ImageIcon("Character/Character/- Gif/AirAttack.gif"));
		images.put("AirIdle", new ImageIcon("Character/Character/- Gif/AirIdle.gif"));
		images.put("Attack", new ImageIcon("Character/Character/- Gif/Attack.gif"));
		images.put("Crouch", new ImageIcon("Character/Character/- Gif/Crouch.gif"));
		images.put("Death", new ImageIcon("Character/Character/- Gif/Death.gif"));
		images.put("Dogde", new ImageIcon("Character/Character/- Gif/Dogde.gif"));
		images.put("Draw", new ImageIcon("Character/Character/- Gif/Draw.gif"));
		images.put("Fall", new ImageIcon("Character/Character/- Gif/Fall.gif"));
		images.put("Hurt", new ImageIcon("Character/Character/- Gif/Hurt.gif"));
		images.put("Idle", new ImageIcon("Character/Character/- Gif/Idle.gif"));
		images.put("Jump", new ImageIcon("Character/Character/- Gif/Jump.gif"));
		images.put("Run", new ImageIcon("Character/Character/- Gif/Run.gif"));
		images.put("Sheath", new ImageIcon("Character/Character/- Gif/Sheath.gif"));
		images.put("Slide", new ImageIcon("Character/Character/- Gif/Slide.gif"));
		images.put("Stand", new ImageIcon("Character/Character/- Gif/Stand.gif"));
		images.put("DeathFinal", new ImageIcon("Character/Character/Death/adventurer-die-06.png"));
		player.setIcon(images.get("Idle"));
	}
  public JLabel getComponent()
  {
    return player;
  }
  public Rectangle getBounds()
  {
    return player.getBounds();
  }
	public int getYVelocity()
	{
		return YVel;
	}
	public int getXVelocity()
	{
		return XVel;
	}
  public boolean isGrounded()
  {
    return grounded;
  }
  public void setGrounded(boolean grounded)
  {
    this.grounded = grounded;
    
  }
  public void translate(int dx, int dy)
  {
    player.setLocation(player.getX() + dx, player.getY() + dy);
  }
	public void setYVelocity(int YVel)
	{
		this.YVel = YVel;
	}
	public void setXVelocity(int XVel)
	{
		this.XVel = XVel;
	}

	public void checkAnimation()
	{
		if (XVel == 0 && grounded == true && player.getIcon() != images.get("Idle"))
		{
			player.setIcon(images.get("Idle"));
      jumps = 2;
		}
		if (XVel == 0 && grounded == false && player.getIcon() != images.get("Fall"))
		{
			player.setIcon(images.get("Fall"));
		}
		if (XVel != 0 && grounded == false && player.getIcon() != images.get("AirIdle"))
		{
			player.setIcon(images.get("AirIdle"));
		}
		if (XVel != 0 && grounded == true && player.getIcon() != images.get("Run"))
		{
			player.setIcon(images.get("Run"));
      jumps = 2;
		}
		if (crouch == true && XVel == 0 && player.getIcon() != images.get("Crouch"))
		{
			player.setIcon(images.get("Crouch"));
		}
		if (crouch == true && XVel != 0 && player.getIcon() != images.get("Idle"))
		{
			player.setIcon(images.get("Slide"));
		}
	}

  public void attackAnimation()
  {
    if(XVel == 0 && player.getIcon() != images.get("Attack"))
    {
      player.setIcon(images.get("Attack"));
    }
    if(XVel != 0 && grounded == false && player.getIcon() != images.get("AirAttack"))
    {
      player.setIcon(images.get("AirAttack"));
    }
  }

  public boolean attack()
  {
    //Rectangle attackBox = new Rectangle()
    return true;
  }

  public void deathAnimation()
  {
    if(health != 0)
    {
       player.setIcon(images.get("Death"));
    }
  }
  
}