import java.util.*;
import javax.swing.*;

public class Boss {
   private JLabel boss, fireball;
   private HashMap<String, ImageIcon> images;
   private int XVel;
   private int YVel;
   private int HPStat;
   	public Boss()
	{
    boss = new JLabel();
    fireball = new JLabel();
    images = new HashMap<>();
    images.put("Attack", new ImageIcon("demon/demon/- GIFS/demon-attack.gif"));
    images.put("Idle", new ImageIcon("demon/demon/- GIFS/idle.gif"));
    images.put("Fireball", new ImageIcon("demon/demon/GIFS/Fireball-Attack.gif"));
    image.put("AttackNoBreath", new ImageIcon("demon/demon/GIFS/demon-attack-no-breath.gif"));
    boss.setIcon(images.get("Idle"));
  }
  public void meleeAttack()
  {
    boss.setIcon(images.get("AttackNoBreath"));
  }
  public void straightAttack()
  {
    boss.setIcon(images.get("Attack"));
  }
  public void homingAttack()
  {
    boss.setIcon(images.get("Attack"));
  }
  public void arcAttack()
  {
    boss.setIcon(images.get("Attack"));
  }
  public void flurriesAttack()
  {
    boss.setIcon(images.get("AttackNoBreath"));
  }
}
