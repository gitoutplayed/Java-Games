import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Font;
/**
 * This is the card class.
 * 
 * @author (Michael Fan) 
 * @version (April 19, 2015)
 */
public class Card
{
    // Instance field
    private int x,y;
    private String name;
    private boolean flip,remove;
    
    private final int WIDTH = 110;
    private final int HEIGHT = 136;
    
    // Constructor
    public Card(int x,int y,String name)
    {
        this.x = x;
        this.y = y;
        flip = false;
        remove = false;
        this.name = name;
    }
    
    // Method 
    
    // Get the image
    public Image getImage()
    {
        ImageIcon ii = new ImageIcon(name);
        return ii.getImage();
    }
    
    // Return the name
    public String getName()
    {
        return name;
    }
    
    // Flip the card
    public void flip()
    {
        flip = true;
    }
    // Unflip the card
    public void unFlip()
    {
        flip = false;
    }
    // Return flip
    public boolean isFlip()
    {
        return flip;
    }
    
    // Remove the card
    public void remove()
    {
        remove = true;
    }
    // Unremove the card
    public void unRemove()
    {
        remove = false;
    }
    // Return remove
    public boolean isRemove()
    {
        return remove;
    }
    
    // Return x
    public int getX()
    {
        return x;
    }
    // Return y
    public int getY()
    {
        return y;
    }
    
    // Return WIDTH
    public int getWidth()
    {
        return WIDTH;
    }
    // Reurnn HEIGHT
    public int getHeight()
    {
        return HEIGHT;
    }
    
    // The draw method
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        Font font = new Font("Arial",Font.PLAIN,100);
        g2d.setFont(font);
        
        // The card it is not removed
        if(!remove)
        {
            // The front of the card
            if(flip)
            {
                g2d.drawImage(getImage(),x,y,null);
            }
            
            // The back of the card
            if(!flip)
            {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x,y,WIDTH,HEIGHT);
                g2d.setColor(Color.WHITE);
                g2d.drawString("?",x + 30,y + 105);
            }
            // Draws the borader
            g2d.setColor(Color.WHITE);
            g2d.drawRect(x,y,WIDTH,HEIGHT);
        }
    }
}
