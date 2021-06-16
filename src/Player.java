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
    private boolean attacking = false;
    private boolean dead = false;
    private int jumps = 2;
	private int XVel = 0;
	private int YVel = 0;
    private final int PLATFORM_VELOCITY = 250;
    private int playerx;
    private int playery;
    private final int playerwidth;
    private final int playerheight;
    private int damage;
    

	public Player(int x, int y, int width, int height)
	{
		player = new JLabel();
        player.setBounds(x, y, width, height);
        playerx = x;
        playery = y;
        playerwidth = width;
        playerheight = height;
        damage = 1;
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
        return new Rectangle(player.getX() + 30, player.getY() + 18, 45, playerheight - 18);
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
        if (grounded)
            jumps = 2;
    }
    public boolean isCrouching()
    {
        return crouch;
    }
    public void setCrouching(boolean crouching)
    {
        crouch = crouching;
    }
    public void translate(int dx, int dy)
    {
        player.setLocation(player.getX() + dx, player.getY() + dy);
        playerx = player.getX();
        playery = player.getY();
    }
    public void setLocation(int x, int y)
    {
        player.setLocation(x, y);
        playerx = x;
        playery = y;
    }
    public int getX()
    {
        return playerx;
    }
    public int getY()
    {
        return playery;
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
		if (!getDead() && XVel == 0 && grounded == true && !attacking && player.getIcon() != images.get("Idle"))
		{
			player.setIcon(images.get("Idle"));
		}
		if (!getDead() && XVel == 0 && grounded == false && !attacking && player.getIcon() != images.get("Fall"))
		{
			player.setIcon(images.get("Fall"));
		}
		if (!getDead() && XVel != 0 && grounded == false && player.getIcon() != images.get("AirIdle"))
		{
			player.setIcon(images.get("AirIdle"));
		}
		if (!getDead() && XVel != 0 && grounded == true && player.getIcon() != images.get("Run"))
		{
			player.setIcon(images.get("Run"));
		}
		if (!getDead() && crouch == true && XVel == 0 && !attacking && player.getIcon() != images.get("Crouch"))
		{
			player.setIcon(images.get("Crouch"));
		}
		if (!getDead() && crouch == true && XVel != 0 && player.getIcon() != images.get("Slide"))
		{
			player.setIcon(images.get("Slide"));
		}
        if (!getDead() && attacking && XVel == 0 && player.getIcon() != images.get("Attack"))
        {
            player.setIcon(images.get("Attack"));
        }
        if (!getDead() && attacking && XVel != 0 && grounded == false && player.getIcon() != images.get("AirAttack"))
        {
             player.setIcon(images.get("AirAttack"));
        }
        if (dead)
        {
            player.setIcon(images.get("Death"));
        }
	}

    public int getJumps()
    {
        return jumps;
    }

    public void jump()
    {
        jumps -= 1;
    }

    public int attack(Rectangle other)
    {
        attacking = true;
        Rectangle attackBox = new Rectangle(getBounds().x + getBounds().width, playery, 100, playerheight);
        
        if (other.getBounds().intersects(attackBox))
        {
           return damage;
        }
        else
        {
          return 0;
        }    
    }
    
    public boolean isAttacking()
    {
        return attacking;
    }

    public void setAttacking(boolean attacking)
    {
        this.attacking = attacking;
    }

    public void increaseDmg(double factor)
    {
        damage = (int)(damage * factor);
    }

    public boolean getDead()
    {
        return dead;
    }

    public void setDead(boolean dead)
    {
        this.dead = dead;
    }
}