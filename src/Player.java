import java.util.*;
import javax.swing.*;
public class Player
{
	private JLabel player;
	private HashMap<String, ImageIcon> images;
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
		player.setIcon(images.get("Idle"));
	}
}