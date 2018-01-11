import java.awt.Graphics;
import java.awt.Color;
/**
 * This class represents a square. 
 * Every square has its own x,y coordiante.
 * Every square knows how to draw itself.
 * A square can be either movable or not. 
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class Square
{
    // Instance field
    private int x,y;
    private boolean movable;
    
    public static final int SIZE = 75;
    
    private Color color = new Color(0,204,0); // Green
    private Color shadow;
    private Color black_shadow = new Color(0,0,0,100);
    private Color white_shadow = new Color(255,255,255,180);
    
    // Constructor
    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
        movable = false;
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
    public boolean isMovable()
    {
        return movable;
    }
    
    // Set the square to movable or non-movable
    public void setMovable(boolean movable, Color color)
    {
        this.movable = movable;
        // Sets the color of the shadow
        if(color.equals(Disc.BLACK))
        {
            shadow = black_shadow;
        }
        else if(color.equals(Disc.WHITE))
        {
            shadow = white_shadow;
        }
    }
    
    // Draws the square
    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(x,y,SIZE,SIZE);
        g.setColor(color);
        g.fillRect(x,y,SIZE,SIZE);
        
        // Draws the shadow of the disc
        if(movable)
        {
            g.setColor(shadow);
            g.fillOval(x,y,SIZE,SIZE);
        }
    }
}
