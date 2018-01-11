import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.util.Random;
/**
 * A die for a game of dice.
 * This class is an attribute of the Cup class.
 * 
 * @author (Michael Fan) 
 * @version (March 31, 2015)
 */
public class Die
{
    // Instance field
    private int x,y;
    private int faceValue;
    private boolean held;
    
    private final int DIE_SIZE = 80;
    private static final Random ran = new Random();
    private static final int MAX = 6;
    
    // Constructor
    public Die(int x,int y)
    {
        this.x = x;
        this.y = y;
        
        // The default face value is 1
        faceValue = 1;
    }
    
    // Methods
    
    // Roll the die and return the face value
    public void roll()
    {
        faceValue = ran.nextInt(MAX) + 1;
    }
    
    // Get the face value
    public int getFaceValue()
    {
        return faceValue;
    }
    
    // Held the die
    public void hold()
    {
        held = true;
    }
    // Unheld the die
    public void unhold()
    {
        held = false;
    }
    // Return held
    public boolean isHeld()
    {
        return held;
    }
    
    // Get the x
    public int getX()
    {
        return x;
    }
    // Get the y
    public int getY()
    {
        return y;
    }
    // Get the size
    public int getSize()
    {
        return DIE_SIZE;
    }
    
    // Set the face value -- mainly for testing purposes 
    public void setFaceValue(int value)
    {
        faceValue = value;
    }
    
    // The draw method
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the die
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x,y,DIE_SIZE,DIE_SIZE);
        
        // Draw the boarder
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x,y,DIE_SIZE,DIE_SIZE);
        
        // Draw the faceValue
        g2d.setColor(Color.BLACK);
        if(faceValue == 1)
        {
            g2d.fillOval(x + 30,y + 30,20,20);
        }
        else if(faceValue == 2)
        {
            g2d.fillOval(x + 50,y + 5,20,20);
            g2d.fillOval(x + 10,y + 55,20,20);
        }
        else if(faceValue == 3)
        {
            g2d.fillOval(x + 50,y + 5,20,20);
            g2d.fillOval(x + 30,y + 30,20,20);
            g2d.fillOval(x + 10,y + 55,20,20);
        }
        else if(faceValue == 4)
        {
            g2d.fillOval(x + 10,y + 5,20,20);
            g2d.fillOval(x + 10,y + 55,20,20);
            g2d.fillOval(x + 50,y + 5,20,20);
            g2d.fillOval(x + 50,y + 55,20,20);
        }
        else if(faceValue == 5)
        {
            g2d.fillOval(x + 10,y + 5,20,20);
            g2d.fillOval(x + 10,y + 55,20,20);
            g2d.fillOval(x + 30,y + 30,20,20);
            g2d.fillOval(x + 50,y + 5,20,20);
            g2d.fillOval(x + 50,y + 55,20,20);
        }
        else if(faceValue == 6)
        {
            g2d.fillOval(x + 10,y + 5,20,20);
            g2d.fillOval(x + 10,y + 30,20,20);
            g2d.fillOval(x + 10,y + 55,20,20);
            g2d.fillOval(x + 50,y + 5,20,20);
            g2d.fillOval(x + 50,y + 30,20,20);
            g2d.fillOval(x + 50,y + 55,20,20);
        }
        
        // An arrow will appear at the bottom of the die if the die is held
        if(held)
        {
            g2d.drawLine(x + 40,y + 180,x + 40,y + 100);
            g2d.drawLine(x + 20,y + 120,x + 40,y + 100);
            g2d.drawLine(x + 60,y + 120,x + 40,y + 100);
        }
    }
}
