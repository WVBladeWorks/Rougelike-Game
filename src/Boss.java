import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Boss {
    private JLabel boss;
    private HashMap<String, ImageIcon> images;
    private int bosswidth, bossheight;
    private int YVel;
    private int HP;
    private int state;
    private final static int IDLE = 0;
    private final static int MELEE = 1;
    private final static int STRAIGHT = 2;
    private final static int HOMING = 3;
    private final static int ARC = 4;
    public Boss(int x, int y, int width, int height)
    {
        boss = new JLabel();
        boss.setBounds(x, y, width, height);
        bosswidth = width;
        bossheight = height;
        state = 0;
        YVel = 0;
        HP = 100000;
        images = new HashMap<>();
        images.put("Attack", createImageIcon("demon/demon/GIFS/demon-attack.gif", "Attack"));
        images.put("Idle", createImageIcon("demon/demon/GIFS/demon-idle.gif", "Idle"));
        images.put("Fireball", createImageIcon("demon/demon/GIFS/Fireball-Attack.gif", "Fireball"));
        images.put("AttackNoBreath", createImageIcon("demon/demon/GIFS/demon-attack-no-breath.gif", "AttackNoBreath"));
        boss.setIcon(images.get("Idle"));
    }
    public Rectangle getBounds()
    {
        return new Rectangle(boss.getX() + 64, boss.getY(), 138, bossheight);
    }
    public JLabel getComponent()
    {
        return boss;
    }
    public int getX()
    {
        return boss.getBounds().x;
    }
    public int getY()
    {
        return boss.getBounds().y;
    }
    public int getWidth()
    {
        return bosswidth;
    }
    public int getHeight()
    {
        return bossheight;
    }
    public int getYVelocity()
    {
        return YVel;
    }
    public void setYVelocity(int YVel)
    {
        this.YVel = YVel;
    }
    public int getHP()
    {
        return HP;
    }
    public void takeDamage(int damage)
    {
        HP -= damage;
    }
    public void translate(int dx, int dy)
    {
        boss.setLocation(getX() + dx, getY() + dy);
    }
    public void setLocation(int x, int y)
    {
        boss.setLocation(x, y);
    }
    public int getState()
    {
        return state;
    }
    public void setState(int newState)
    {
        if (newState < 0 || newState > 4)
            throw new IllegalArgumentException("State must be an integer from 0-5!");
        this.state = newState;
    }
    public void checkAnimation()
    {
        if (state == IDLE && boss.getIcon() != images.get("Idle"))
            boss.setIcon(images.get("Idle"));
        if (state == MELEE && boss.getIcon() != images.get("Attack"))
            boss.setIcon(images.get("Attack"));
        if (state == STRAIGHT && boss.getIcon() != images.get("AttackNoBreath"))
            boss.setIcon(images.get("AttackNoBreath"));
        if (state == HOMING && boss.getIcon() != images.get("AttackNoBreath"))
            boss.setIcon(images.get("AttackNoBreath"));
        if (state == ARC && boss.getIcon() != images.get("AttackNoBreath"))
            boss.setIcon(images.get("AttackNoBreath"));
    }
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    //public boolean attack()
    //{
      //Rectangle attackBox = new Rectangle()
    //}
}
