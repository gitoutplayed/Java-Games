import java.awt.Graphics;
import java.awt.Color;
/**
 * This class represents a disc.
 * Every disc has a x,y coordiantes.
 * Every disc knows how to draw itself.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class Disc
{
    // Instance field
    private int x,y;
    
    private final int SIZE = 75;
    
    private Color color;
    
    public static final Color BLACK = new Color(0,0,0);
    public static final Color WHITE = new Color(255,255,255);
    
    // Constructor
    public Disc(int x, int y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    // Getters
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public Color getColor()
    {
        return color;
    }
    
    // Flip the disc
    public void flip()
    {
        if(color.equals(BLACK))
        {
            color = WHITE;
        }
        else
        {
            color = BLACK;
        }
    }
    
    // Draws the disc
    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawOval(x,y,SIZE,SIZE);
        g.setColor(color);
        g.fillOval(x,y,SIZE,SIZE);
    }
}
