import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
/**
 * This is the ScoreBlock class. Responible to know it-self(x,y,title and the score it holds).
 * This class is an attribute of the ScoreSheet class.
 * 
 * @author (Michael Fan) 
 * @version (April 16, 2015)
 */
public class ScoreBlock
{
    // Instance field
    private int x,y;
    private String title,score;
    private boolean clickable,select;
    
    private final int WIDTH = 300;
    private final int HEIGHT = 50;
    
    // Constructor
    public ScoreBlock(int x,int y,String title)
    {
        this.x = x;
        this.y = y;
        this.title = title;
        score = " ";
        select = false;
    }
    
    // Methods
    
    // Set the block to be clickable
    public void clickable()
    {
        clickable = true;
    }
    // Set the block to be clickable
    public void unClickable()
    {
        clickable = false;
    }
    // Check if the block is clickable
    public boolean isClickable()
    {
        return clickable;
    }
    
    // Select the block
    public void select()
    {
        select = true;
    }
    // Unselect the block
    public void unselect()
    {
        select = false;
    }
    // Check if the block is selected
    public boolean isSelect()
    {
        return select;
    }
    
    // Get x
    public int getX()
    {
        return x;
    }
    // Get y
    public int getY()
    {
        return y;
    }
    // Get the width
    public int getWidth()
    {
        return WIDTH;
    }
    // Get the height
    public int getHeight()
    {
        return HEIGHT;
    }
    
    // Get the socre
    public String getScore()
    {
        return score;
    }
    // Set the score
    public void setScore(int score)
    {
        this.score = Integer.toString(score);
    }
    
    // Clear the block
    public void clear()
    {
        score = " ";
    }
    
    // The draw method
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create the font and the size
        Font font = new Font("Arial",Font.PLAIN,25);
        // Set the font
        g2d.setFont(font);
        
        // The rectangle
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x,y,WIDTH,HEIGHT);
        
        // The boarder
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x,y,WIDTH,HEIGHT);
        
        // The words
        g2d.drawString(title,x + 3,y + 35);
        
        // The score. The color will change if the color is selected
        if(select)
        {
            g2d.setColor(Color.BLUE);
        }
        g2d.drawString(score,x + 220,y + 35);
    }
}
