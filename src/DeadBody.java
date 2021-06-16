import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DeadBody
{
    private JLabel deadBody;
    private HashMap<String, ImageIcon> images;
    private int XVel, YVel;
    private int x, y, width, height;
    private double factor;

    public DeadBody(int x, int y, int width, int height, double factor)
    {
        deadBody = new JLabel();
        deadBody.setBounds(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        XVel = -3;
        YVel = 0;
        this.factor = factor;
        images = new HashMap<>();
        images.put("DeadBody", new ImageIcon("Character/Character/Death/adventurer-die-06.png"));
        deadBody.setIcon(images.get("DeadBody"));
    }

    public JLabel getComponent()
    {
        return deadBody;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(getX() + 30, getY(), 45, height);
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
        return deadBody.getBounds().x;
    }

    public int getY()
    {
        return deadBody.getBounds().y;
    }

    public double getFactor()
    {
        return factor;
    }

    public void setFactor(double newFactor)
    {
        this.factor = newFactor;
    }

    public void translate(int dx, int dy)
    {
        deadBody.setLocation(getX() + dx, getY() + dy);
    }

    public void setLocation(int x, int y)
    {
        deadBody.setLocation(x, y);
    }
}

