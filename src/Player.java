import java.util.*;
import javax.swing.*;
public class Player
{
	private JLabel player;
	private HashMap<String, ImageIcon> images;
	private boolean grounded = true;
	private boolean crouch = false;
	private boolean dogde = false;
	private int XVel = 0;
	private int YVel = 0;
	public Player()
	{
		player = new JLabel();
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
	public int getYVelocity()
	{
		return YVel;
	}
	public int getXVelocity()
	{
		return XVel;
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
}