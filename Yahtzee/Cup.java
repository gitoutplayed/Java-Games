import java.awt.Graphics;
/**
 * Cup - a class to hold five dice and roll them and score them for a game of yahtzee.
 * This class is an attribute of the Table class.
 * The Die class is an attribute of this class.
 * 
 * @author (Michael Fan) 
 * @version (March 31, 2015)
 */
public class Cup
{
   // Instance field
   private int x,y;
   
   private final int SPACE_IN_BWTWEEN = 120;
   private final int MAX_NUMBER_OF_DICE = 5;
   
   Die[] cupOfDice;
   
   // Consturctor
   public Cup()
   {
       // Creates the tup
       cupOfDice = new Die[MAX_NUMBER_OF_DICE];
       
       // Put dice in cup
       x = 40;
       y = 300;
       
       for(int i = 0; i < cupOfDice.length; i++)
       {
           cupOfDice[i] = new Die(x,y);
           x += SPACE_IN_BWTWEEN;
       }
   }
   
   // Methods
   
   // This will shake all the die in the cup
   public void shake()
   {
       for(int i = 0; i < cupOfDice.length; i++)
       {
          if(!cupOfDice[i].isHeld())
          {
               cupOfDice[i].roll();
          }
       }
   }
   
   // Unhold all the die
   public void unHoldAll()
   {
       for(int i = 0; i < cupOfDice.length; i++)
       {
           cupOfDice[i].unhold();
       }
   }
   
   // Returns a single die
   public Die getDie(int index)
   {
       return cupOfDice[index];
   }
   
   // Returns the array
   public Die[] getCup()
   {
       return cupOfDice;
   }
   
   // Get the totalValue
   public int getTotalValue()
   {
       int totalValue = 0;
       for(int i = 0; i < cupOfDice.length; i++)
       {
           totalValue += cupOfDice[i].getFaceValue();
       }
       return totalValue;
   }
   
   // The draw method
   public void draw(Graphics g)
   {
       // Draw every single die in the cup
       for(int i = 0; i < cupOfDice.length; i++)
       {
           cupOfDice[i].draw(g);
       }
   }
    
}
