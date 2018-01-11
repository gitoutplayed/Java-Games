import java.awt.Graphics;
import java.awt.Color;
/**
 * This is the ScoreSheet class. Responsible for the painting of the score sheet.
 * This class is an attribute of the Table class.
 * The ScoreBlock class is an attribute of this class.
 * 
 * @author (Michael Fan) 
 * @version (April 16, 2015)
 */
public class ScoreSheet
{
    // Instance field
    private int x,y;
    private int upperSectionSum;
    private int lowerSectionSum;
    
    private final int UPPERSECTION = 9;
    private final int LOWERSECTION = 8;
    
    ScoreBlock[] upperSection;
    ScoreBlock[] lowerSection;
    
    // Constructor 
    public ScoreSheet()
    {
        // The upper section
        upperSection = new ScoreBlock[UPPERSECTION];
        
        x = 675;
        y = 25;
        
        for(int i = 0; i < upperSection.length; i++)
        {
            if(i == 0)
            {
                upperSection[i] = new ScoreBlock(x,y,"Upper Section");
                upperSection[i].unClickable();
            }
            else if(i == 1)
            {
                upperSection[i] = new ScoreBlock(x,y,"Ones");
                upperSection[i].clickable();
            }
            else if(i == 2)
            {
                upperSection[i] = new ScoreBlock(x,y,"Twos");
                upperSection[i].clickable();
            }
            else if(i == 3)
            {
                upperSection[i] = new ScoreBlock(x,y,"Threes");
                upperSection[i].clickable();
            }
            else if(i == 4)
            {
                upperSection[i] = new ScoreBlock(x,y,"Fours");
                upperSection[i].clickable();
            }
            else if(i == 5)
            {
                upperSection[i] = new ScoreBlock(x,y,"Fives");
                upperSection[i].clickable();
            }
            else if(i == 6)
            {
                upperSection[i] = new ScoreBlock(x,y,"Sixs");
                upperSection[i].clickable();
            }
            else if(i == 7)
            {
                upperSection[i] = new ScoreBlock(x,y,"Sum");
                upperSection[i].unClickable();
            }
            else if(i == 8)
            {
                upperSection[i] = new ScoreBlock(x,y,"Bouns");
                upperSection[i].unClickable();
            }
            y += 50;
        }
        
        // The lower section
        lowerSection = new ScoreBlock[LOWERSECTION];
        
        for(int i = 0; i < lowerSection.length; i++)
        {
            if(i == 0)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Lower Section");
                lowerSection[i].unClickable();
            }
            else if(i == 1)
            {
                lowerSection[i] = new ScoreBlock(x,y,"3 Of A Kind");
                lowerSection[i].clickable();
            }
            else if(i == 2)
            {
                lowerSection[i] = new ScoreBlock(x,y,"4 Of A Kind");
                lowerSection[i].clickable();
            }
            else if(i == 3)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Full House");
                lowerSection[i].clickable();
            }
            else if(i == 4)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Small Straight");
                lowerSection[i].clickable();
            }
            else if(i == 5)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Large Straight");
                lowerSection[i].clickable();
            }
            else if(i == 6)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Yahtzee");
                lowerSection[i].clickable();
            }
            else if(i == 7)
            {
                lowerSection[i] = new ScoreBlock(x,y,"Chance");
                lowerSection[i].clickable();
            }
            y += 50;
        }
    }
    
    // Method
    
    // Sum up the upper section
    public void sumUpUpperSection()
    {
        for(int i = 1; i < upperSection.length - 2; i++)
        {
            upperSectionSum += Integer.parseInt(upperSection[i].getScore());
        }
    }
    // Sum up the lower section
    public void sumUpLowerSection()
    {
        for(int i = 1; i < lowerSection.length; i++)
        {
            lowerSectionSum += Integer.parseInt(lowerSection[i].getScore());
        }
    }
    
    // Return the sum of the upper section
    public int getUpperSectionSum()
    {
        return upperSectionSum;
    }
    // Return the sum of the lower section
    public int getLowerSectionSum()
    {
        return lowerSectionSum;
    }
    
    // Return the upper section list
    public ScoreBlock[] getUpperSection()
    {
        return upperSection;
    }
    // Return the lower section list
    public ScoreBlock[] getLowerSection()
    {
        return lowerSection;
    }
    
    // Return the upper section block at the specific index
    public ScoreBlock getUpperSectionBlock(int index)
    {
        return upperSection[index];
    }

    // Return the upper section block at the specific index
    public ScoreBlock getLowerSectionBlock(int index)
    {
        return lowerSection[index];
    }
    
    // Clear the upper secton
    public void clearUpperSection()
    {
        for(int i = 0; i < upperSection.length; i++)
        {
            // Only clear the block if the it is not selected
            if(!upperSection[i].isSelect())
            {
                upperSection[i].clear();
            }
        }
    }
    // Clear the lower secton
    public void clearLowerSection()
    {
        for(int i = 0; i < lowerSection.length; i++)
        {
            // Only clear the block if the it is not selected
            if(!lowerSection[i].isSelect())
            {
                lowerSection[i].clear();
            }
        }
    }
    
    // The draw method
    public void draw(Graphics g)
    {
        // Draw the upper section
        for(int i = 0; i < upperSection.length; i++)
        {
            upperSection[i].draw(g);
        }

        // Draw the lower section
        for(int i = 0; i < lowerSection.length; i++)
        {
            lowerSection[i].draw(g);
        }
        
        // Draw the division line of the upper section
        g.setColor(Color.BLACK);
        g.drawLine(840,75,840,475);
        
        // Draw the division line of the Lower section
        g.setColor(Color.BLACK);
        g.drawLine(840,525,840,875);
    }
}
