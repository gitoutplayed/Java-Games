import java.awt.Graphics;
import java.util.Collections;
import java.util.Random;
/**
 * This is the deck class.
 * 
 * @author (Michael Fan) 
 * @version (April 19,2 2015)
 */
public class Deck
{
    // Instance field
    private final int CARD = 30;
    private final int ROW = 5;
    private final int COL = 6;
    
    private int[] imageNumber = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    private Card[][] deck;
    private Random random = new Random();
    
    // Constructor
    public Deck()
    {
        init();
    }
    
    // Method
    
    // The initalizer
    public void init()
    {        
        int x = 20,y = 20;
        int index = 0;
        String png = ".png";
        
        deck = new Card[ROW][COL];
        
        // Shuffle the image numbers
        int temp;
        for (int i = imageNumber.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = imageNumber[index];
            imageNumber[index] = imageNumber[i];
            imageNumber[i] = temp;
        }
        
        // Fill the deck. Horizontal gap: 20 and Vertical gap: 20
        index = 0;
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                deck[row][col] = new Card(x,y,Integer.toString(imageNumber[index]) + png);
                x += 130;
                index++;
            }
            x = 20;
            y += 156;
        }
    }
    
    // Reset the deck
    public void reset()
    {
        int x = 20,y = 20;
        int index = 0;
        String png = ".png";
        
        // Shuffle the image numbers
        int temp;
        for (int i = imageNumber.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = imageNumber[index];
            imageNumber[index] = imageNumber[i];
            imageNumber[i] = temp;
        }
        
        // Fill the deck. Horizontal gap: 20 and Vertical gap: 20
        index = 0;
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                deck[row][col] = new Card(x,y,Integer.toString(imageNumber[index]) + png);
                x += 130;
                index++;
            }
            x = 20;
            y += 156;
        }
    }
    
    // Compare two cards. Return true they are the same
    public boolean isEqual(Card card1,Card card2)
    {
        boolean same;
        if(card1.getName().equals(card2.getName()))
        {
            same = true;
        }
        else
        {
            same = false;
        }
        return same;
    }
    
    // Return the card at a specific index
    public Card getCard(int row,int col)
    {
        return deck[row][col];
    }
    
    // The draw method
    public void draw(Graphics g)
    {
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                deck[row][col].draw(g);
            }
        }
    }
}
