import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.Border;

public class Fireball
{
    private JLabel fireball;
    private HashMap<String, ImageIcon> images;
    private int XVel, YVel;
    private int x, y, width, height;
    private boolean homing;

    public Fireball(int x, int y, int speed)
    {
        this.fireball = new JLabel();
        this.width = 120;
        this.height = 63;
        //Border whiteline = BorderFactory.createLineBorder(Color.WHITE);
        //fireball.setBorder(whiteline);
        fireball.setBounds(x, y, width, height);
        this.XVel = speed;
        this.YVel = 0;
        this.homing = false;
        this.images = new HashMap<>();
        images.put("Fireball", new ImageIcon("Misc/FB000.png"));
        fireball.setIcon(images.get("Fireball"));
    }

    public Fireball(int x, int y, int speed, boolean homing)
    {
        this.fireball = new JLabel();
        //Border whiteline = BorderFactory.createLineBorder(Color.WHITE);
        //fireball.setBorder(whiteline);
        this.width = 120;
        this.height = 63;
        fireball.setBounds(x, y, width, height);
        this.XVel = speed;
        this.YVel = 0;
        this.homing = homing;
        this.images = new HashMap<>();
        if (homing)
            images.put("Fireball", new ImageIcon("Misc/FB001.png"));
        else
            images.put("Fireball", new ImageIcon("Misc/FB000.png"));
        fireball.setIcon(images.get("Fireball"));
    }

    public JLabel getComponent()
    {
        return fireball;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), width, height);
    }

    public int getXVelocity()
    {
        return XVel;
    }

    public void setXVelocity(int XVel)
    {
        this.XVel = XVel;
    }

    public int getYVelocity()
    {
        return YVel;
    }

    public void setYVelocity(int YVel)
    {
        this.YVel = YVel;
    }

    public int getX()
    {
        return fireball.getBounds().x;
    }

    public int getY()
    {
        return fireball.getBounds().y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isHoming()
    {
        return homing;
    }

    public void translate(int dx, int dy)
    {
        fireball.setLocation(getX() + dx, getY() + dy);
    }

    public void setLocation(int x, int y)
    {
        fireball.setLocation(x, y);
    }
}