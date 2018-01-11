import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
/**
 * Table - The game happens here.
 * The driver class(Yahtzee) depends on this class.
 * ScoreSheet and Cup are attributes of this class.
 * 
 * @author (Michael Fan) 
 * @version (March 31, 2015)
 */
public class Table extends JPanel
{
    // Instance field
    private int state;
    private int timeCount = 0;
    private int holdCount = 0;
    private int yahtzeeValue;
    private boolean jokerRule = false;
    private boolean upperSectionSum = false;
    private boolean upperSectionAllFilled = false;
    private boolean lowerSectionAllFilled = false;
    
    private final int ROLL_BUTTON_X = 240;
    private final int ROLL_BUTTON_Y = 700;
    private final int ROLL_BUTTON_WIDTH = 200;
    private final int ROLL_BUTTON_HEIGHT = 50;
    
    private final int STARTING = 0;
    private final int FIRST_ROLL = 1;
    private final int SECOND_ROLL = 2;
    private final int THIRD_ROLL = 3;
    private final int FINISHED = 4;
    
    private ScoreSheet scoreSheet;
    private Cup cup;
    
    private Timer timer;
    private Dimension panelSize = new Dimension(1000,900);
    private Color tableColor = new Color(34,139,34);
    
    // Constructor: Setup the JPanel
    public Table()
    {
        setPreferredSize(panelSize);
        setBackground(tableColor);
        
        cup = new Cup();
        scoreSheet = new ScoreSheet();
        
        timer = new Timer(1,new ShakingListener());
        
        // The state at the beginning of the game is FIRST_ROLL
        state = STARTING;
        
        addMouseListener(new Listener());
    }
    
    // The mouse listener class. The game happens here
    private class Listener implements MouseListener
    {
        // All the action happens in this method
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            
            // In the starting state the player can only roll the dice. The player cannot hold any die
            if(state == STARTING)
            {
                // Roll
                if(isClickOnButton(x,y))
                {
                    timer.start();
                    
                    // The state is changed to second roll
                    state = FIRST_ROLL;
                }
            }
            // In the first roll state the player can roll dice again of their choice or score.
            else if(state == FIRST_ROLL)
            {
                // Choose which die to hold
                choseWhichDieToHeld(x,y);
                
                // Roll. You can only reroll if the joker rule does not apply and you does not hold five dice
                if(isClickOnButton(x,y) && !jokerRule && holdCount != 5)
                {
                    // Since the player choose to roll therefore we clear the score sheet
                    clearScoreSheet();
                    
                    timer.start();
                    
                    // The state is changed to third roll
                    state = SECOND_ROLL;
                }
                
                // Or the player can choose can score 
                // If the joker rule does not apply the player score as normal
                if(!jokerRule)
                {
                    scoreOptionUpperSection(x,y);
                    scoreOptionLowerSection(x,y);
                }
                else if(jokerRule)
                {
                    jokerRule(x,y);
                }
            }
            // In the second roll state the player can keep rolling the dice of their choice or score
            else if(state == SECOND_ROLL)
            {
                // Choose which die to hold
                choseWhichDieToHeld(x,y);
                
                // Roll. You can only reroll if the joker rule does not apply and you does not hold five dice
                if(isClickOnButton(x,y) && !jokerRule && holdCount != 5)
                {
                    // Since the player choose to roll therefore we clear the score sheet
                    clearScoreSheet();
                    
                    timer.start();
                    
                    // The state is changed to third roll
                    state = THIRD_ROLL;
                }
                
                // Or the player can choose can score 
                // If the joker rule does not apply the player score as normal
                if(!jokerRule)
                {
                    scoreOptionUpperSection(x,y);
                    scoreOptionLowerSection(x,y);
                }
                else if(jokerRule)
                {
                    jokerRule(x,y);
                }
            }
            // In the third roll state the player can only score 
            else if(state == THIRD_ROLL)
            {
                // The player can choose can score 
                // If the joker rule does not apply the player score as normal
                if(!jokerRule)
                {
                    scoreOptionUpperSection(x,y);
                    scoreOptionLowerSection(x,y);
                }
                else if(jokerRule)
                {
                    jokerRule(x,y);
                }
            }
            
            // If both section has been filled the game ends
            if(upperSectionAllFilled && lowerSectionAllFilled)
            {
                state = FINISHED;
            }
            
            // Repaint the screen
            repaint();
        }
        
        // Unused methods
        public void mousePressed(MouseEvent e)
        {}
        public void mouseReleased(MouseEvent e)
        {}
        public void mouseEntered(MouseEvent e)
        {}
        public void mouseExited(MouseEvent e)
        {}
    }
    
    // The ActionListener class
    private class ShakingListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            timerControl();
            
            // Repaint the screen
            repaint();
        }
    }
    
    // The draw method
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // Draw the dice
        cup.draw(g);
        
        // Draw the score sheet
        scoreSheet.draw(g);
        
        // Draw the button
        drawUtlity(g);
    }
    
    // Utlity include a button and instructions
    public void drawUtlity(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        // The button
        g2d.setColor(Color.WHITE);
        g2d.fillRect(ROLL_BUTTON_X,ROLL_BUTTON_Y,ROLL_BUTTON_WIDTH,ROLL_BUTTON_HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Roll",ROLL_BUTTON_X + 75,ROLL_BUTTON_Y + 35);
        
        // The boarder of the button
        g2d.drawRect(ROLL_BUTTON_X,ROLL_BUTTON_Y,ROLL_BUTTON_WIDTH,ROLL_BUTTON_HEIGHT);
        
        // Instructions. The instructions varys upon state
        if(state == STARTING)
        {
            g2d.drawString("You are in the starting stage",50,60);
            g2d.drawString("You can only roll the dice",50,115);
        }
        else if(state == FIRST_ROLL)
        {
            g2d.drawString("You are in your first roll",50,60);
            g2d.drawString("You can roll again",50,115);
            g2d.drawString("You can choose which die you want to",50,140);
            g2d.drawString("hold(Maximum hold of 4) by click on",50,165);
            g2d.drawString("the die(an arrow will be showing if the die is held)",50,190);
            g2d.drawString("Or you can score by click on the score sheet",50,240);
            g2d.drawString("on the right side of each category(you can only score",50,265);
            g2d.drawString("once in each catrgory)",50,290);
        }
        else if(state == SECOND_ROLL)
        {
            g2d.drawString("You are in your second roll",50,60);
            g2d.drawString("You can roll again",50,115);
            g2d.drawString("You can choose which die you want to",50,140);
            g2d.drawString("hold(Maximum hold of 4) by click on",50,165);
            g2d.drawString("the die(an arrow will be showing if the die is held)",50,190);
            g2d.drawString("Or you can score by click on the score sheet",50,240);
            g2d.drawString("on the right side of each category(you can only score",50,265);
            g2d.drawString("once in each catrgory)",50,290);
        }
        else if(state == THIRD_ROLL)
        {
            g2d.drawString("You are in your third roll",50,60);
            g2d.drawString("You can only score",50,115);
            g2d.drawString("Or you can score by click on the score sheet",50,140);
            g2d.drawString("on the right side of each category(you can only score",50,165);
            g2d.drawString("once in each catrgory)",50,190);
        }
        else if(state == FINISHED)
        {
            int total = scoreSheet.getUpperSectionSum() + Integer.parseInt(scoreSheet.getUpperSectionBlock(8).getScore()) + scoreSheet.getLowerSectionSum();
            g2d.drawString("The game is finished",50,60);
            g2d.drawString("Your final score is: " + Integer.toString(total),50,140);
        }
    }
    
    // Make sure the player clicks on the button
    public boolean isClickOnButton(int x ,int y)
    {
        return (x >= ROLL_BUTTON_X && x <= ROLL_BUTTON_X + ROLL_BUTTON_WIDTH) && (y >= ROLL_BUTTON_Y && y <= ROLL_BUTTON_Y + ROLL_BUTTON_HEIGHT);
    }
    
    // Timer control
    public void timerControl()
    {
        // Shake the cup
        cup.shake();
        
        timeCount++;
        if(timeCount == 80)
        {
            timeCount = 0;
            
            // Display the possible score option
            scoreSheetUpdate(cup);

            timer.stop();
        }
    }
    
    // Let the player to chose which die they want to held
    public void choseWhichDieToHeld(int x,int y)
    {
        // Search thourgh the list
        for(int i = 0; i < cup.getCup().length; i++)
        {
            // Check which die is clicked
            if(x >= cup.getDie(i).getX() && x <= cup.getDie(i).getX() + cup.getDie(i).getSize() && y >= cup.getDie(i).getY() && y <= cup.getDie(i).getY() + cup.getDie(i).getSize())
            {
                // If the die is not held already than hold it
                if(!cup.getDie(i).isHeld())
                {
                    cup.getDie(i).hold();
                    holdCount++;
                }
                else
                {
                    cup.getDie(i).unhold();
                    holdCount--;
                }
            }
        }
    }
    
    // The joker rule
    public void jokerRule(int x,int y)
    {
        // If the appropriate box is open it will be scored automaticly
        if(!scoreSheet.getUpperSectionBlock(yahtzeeValue).isSelect())
        {
            scoreSheet.getUpperSectionBlock(yahtzeeValue).select();
            scoreSheet.getUpperSectionBlock(yahtzeeValue).setScore(cup.getTotalValue());
            jokerRule = false;
            
            // The state is back to the starting state
            state = STARTING;
        }
        // If the appropriate box is filled the player can score in any open box in the lower section
        else if(!lowerSectionAllFilled && scoreSheet.getUpperSectionBlock(yahtzeeValue).isSelect())
        {
            for(int i = 0; i < scoreSheet.getLowerSection().length; i++)
            {
                // Find which block the player clicked
                if(x >= scoreSheet.getLowerSectionBlock(i).getX() + ((scoreSheet.getLowerSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getLowerSectionBlock(i).getX() + scoreSheet.getLowerSectionBlock(i).getWidth()
                && y >= scoreSheet.getLowerSectionBlock(i).getY() && y <= scoreSheet.getLowerSectionBlock(i).getY() + scoreSheet.getLowerSectionBlock(i).getHeight() && !scoreSheet.getLowerSectionBlock(i).getScore().equals(" ")
                && !scoreSheet.getLowerSectionBlock(i).isSelect() && scoreSheet.getLowerSectionBlock(i).isClickable())
                {
                    if(i == 1)
                    {
                        scoreSheet.getLowerSectionBlock(1).select();
                        scoreSheet.getLowerSectionBlock(1).setScore(cup.getTotalValue());
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                    else if(i == 2)
                    {
                        scoreSheet.getLowerSectionBlock(2).select();
                        scoreSheet.getLowerSectionBlock(2).setScore(cup.getTotalValue());
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                    else if(i == 3)
                    {
                        scoreSheet.getLowerSectionBlock(3).select();
                        scoreSheet.getLowerSectionBlock(3).setScore(25);
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                    else if(i == 4)
                    {
                        scoreSheet.getLowerSectionBlock(4).select();
                        scoreSheet.getLowerSectionBlock(4).setScore(30);
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                    else if(i == 5)
                    {
                        scoreSheet.getLowerSectionBlock(5).select();
                        scoreSheet.getLowerSectionBlock(5).setScore(40);
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                    else if(i == 7)
                    {
                        scoreSheet.getLowerSectionBlock(7).select();
                        scoreSheet.getLowerSectionBlock(7).setScore(cup.getTotalValue());
                        jokerRule = false;
                        
                        // The state is back to the starting state
                        state = STARTING;
                    }
                }
            }
        }
        // If the appropriate box is filled and all lower section is filled and all upper section is not filled, the player will score 0 in any open upper section box.
        else if(lowerSectionAllFilled && !upperSectionAllFilled && scoreSheet.getUpperSectionBlock(yahtzeeValue).isSelect())
        {
            for(int i = 0; i < scoreSheet.getUpperSection().length; i++)
            {
                // Find which block the player clicked
                if(x >= scoreSheet.getUpperSectionBlock(i).getX() + ((scoreSheet.getUpperSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getUpperSectionBlock(i).getX() + scoreSheet.getUpperSectionBlock(i).getWidth()
                && y >= scoreSheet.getUpperSectionBlock(i).getY() && y <= scoreSheet.getUpperSectionBlock(i).getY() + scoreSheet.getUpperSectionBlock(i).getHeight() && !scoreSheet.getUpperSectionBlock(i).getScore().equals(" ")
                && !scoreSheet.getUpperSectionBlock(i).isSelect() && scoreSheet.getUpperSectionBlock(i).isClickable())
                {
                    scoreSheet.getUpperSectionBlock(i).select();
                    scoreSheet.getUpperSectionBlock(i).setScore(0);
                    jokerRule = false;
                    
                    // The state is back to the starting state
                    state = STARTING;
                }
            }
        }
        // If everything is filled the joker rule will not apply
        else
        {
            jokerRule = false;
        }
    }
    
    // Let the player to choose what to score in the upper section
    public void scoreOptionUpperSection(int x, int y)
    {
        // Search through the list
        for(int i = 0; i < scoreSheet.getUpperSection().length; i++)
        {
            // Find which block the player clicked
            if(x >= scoreSheet.getUpperSectionBlock(i).getX() + ((scoreSheet.getUpperSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getUpperSectionBlock(i).getX() + scoreSheet.getUpperSectionBlock(i).getWidth()
            && y >= scoreSheet.getUpperSectionBlock(i).getY() && y <= scoreSheet.getUpperSectionBlock(i).getY() + scoreSheet.getUpperSectionBlock(i).getHeight() && !scoreSheet.getUpperSectionBlock(i).getScore().equals(" ")
            && !scoreSheet.getUpperSectionBlock(i).isSelect() && scoreSheet.getUpperSectionBlock(i).isClickable())
            {
                scoreSheet.getUpperSectionBlock(i).select();
                
                // Unhold all the dice
                cup.unHoldAll();
             
                // Check for sum and bouns
                if(!upperSectionSum)
                {
                    checkSumAndBounsOfUpperSection();
                }
                
                // Clear the score sheet
                clearScoreSheet();
                
                // The state is back to the starting state
                state = STARTING;
            }
            // If the the block is not a scoring option the score will be 0
            else if(x >= scoreSheet.getUpperSectionBlock(i).getX() + ((scoreSheet.getUpperSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getUpperSectionBlock(i).getX() + scoreSheet.getUpperSectionBlock(i).getWidth()
            && y >= scoreSheet.getUpperSectionBlock(i).getY() && y <= scoreSheet.getUpperSectionBlock(i).getY() + scoreSheet.getUpperSectionBlock(i).getHeight() && scoreSheet.getUpperSectionBlock(i).getScore().equals(" ")
            && !scoreSheet.getUpperSectionBlock(i).isSelect() && scoreSheet.getUpperSectionBlock(i).isClickable())
            {
                scoreSheet.getUpperSectionBlock(i).select();
                scoreSheet.getUpperSectionBlock(i).setScore(0);
                
                // Unhold all the dice
                cup.unHoldAll();
                
                // Check for sum and bouns
                if(!upperSectionSum)
                {
                    checkSumAndBounsOfUpperSection();
                }

                // Clear the score sheet
                clearScoreSheet();
                
                // The state is back to the starting state
                state = STARTING;
            }
        }
    }
    // Let the player to choose what to score in the lower section
    public void scoreOptionLowerSection(int x,int y)
    {
        // Search through the list
        for(int i = 0; i < scoreSheet.getLowerSection().length; i++)
        {
            // Find which block the player clicked
            if(x >= scoreSheet.getLowerSectionBlock(i).getX() + ((scoreSheet.getLowerSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getLowerSectionBlock(i).getX() + scoreSheet.getLowerSectionBlock(i).getWidth()
            && y >= scoreSheet.getLowerSectionBlock(i).getY() && y <= scoreSheet.getLowerSectionBlock(i).getY() + scoreSheet.getLowerSectionBlock(i).getHeight() && !scoreSheet.getLowerSectionBlock(i).getScore().equals(" ")
            && !scoreSheet.getLowerSectionBlock(i).isSelect() && scoreSheet.getLowerSectionBlock(i).isClickable())
            {
                scoreSheet.getLowerSectionBlock(i).select();
                
                // Unhold all the dice
                cup.unHoldAll();
                
                // Check if the section has been filled
                checkLowerSectionFilled();
                
                // Clear the score sheet
                clearScoreSheet();
                
                // The state is back to the starting state
                state = STARTING;
            }
            // If the the block is not a scoring option the player will a 0
            else if(x >= scoreSheet.getLowerSectionBlock(i).getX() + ((scoreSheet.getLowerSectionBlock(i).getWidth() / 2) + 15) && x <= scoreSheet.getLowerSectionBlock(i).getX() + scoreSheet.getLowerSectionBlock(i).getWidth()
            && y >= scoreSheet.getLowerSectionBlock(i).getY() && y <= scoreSheet.getLowerSectionBlock(i).getY() + scoreSheet.getLowerSectionBlock(i).getHeight() && scoreSheet.getLowerSectionBlock(i).getScore().equals(" ")
            && !scoreSheet.getLowerSectionBlock(i).isSelect() && scoreSheet.getLowerSectionBlock(i).isClickable())
            {
                scoreSheet.getLowerSectionBlock(i).select();
                scoreSheet.getLowerSectionBlock(i).setScore(0);
                
                // Unhold all the dice
                cup.unHoldAll();
                
                // Check if the section has been filled
                checkLowerSectionFilled();
                
                // Clear the score sheet
                clearScoreSheet();
                
                // The state is back to the starting state
                state = STARTING;
            }
        }
    }
    
    // Clear the score sheet
    public void clearScoreSheet()
    {
        scoreSheet.clearUpperSection();
        scoreSheet.clearLowerSection();
    }
    
    // Check for sum and bouns of the upper section
    public void checkSumAndBounsOfUpperSection()
    {
        // If all the upper section is filled then the sum will be calculated
        if(!scoreSheet.getUpperSectionBlock(1).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(1).isSelect() && !scoreSheet.getUpperSectionBlock(2).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(2).isSelect()
        && !scoreSheet.getUpperSectionBlock(3).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(3).isSelect() && !scoreSheet.getUpperSectionBlock(4).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(4).isSelect()
        && !scoreSheet.getUpperSectionBlock(5).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(5).isSelect() && !scoreSheet.getUpperSectionBlock(6).getScore().equals(" ") && scoreSheet.getUpperSectionBlock(6).isSelect())
        {
            scoreSheet.getUpperSectionBlock(7).select();
            scoreSheet.sumUpUpperSection();
            scoreSheet.getUpperSectionBlock(7).setScore(scoreSheet.getUpperSectionSum());
            
            upperSectionSum = true;
            
            // If this method is triggered it means that the upper section has been filled  
            upperSectionAllFilled = true;
            
            // If the score is over 63 than a bouns will be applied
            if(scoreSheet.getUpperSectionSum() >= 63)
            {
                scoreSheet.getUpperSectionBlock(8).select();
                scoreSheet.getUpperSectionBlock(8).setScore(35);
            }
            else
            {
                scoreSheet.getUpperSectionBlock(8).select();
                scoreSheet.getUpperSectionBlock(8).setScore(0);
            }
        }
    }
    
    // Check if the lower section has been filled
    public void checkLowerSectionFilled()
    {
        if(scoreSheet.getLowerSectionBlock(1).isSelect() && scoreSheet.getLowerSectionBlock(2).isSelect() && scoreSheet.getLowerSectionBlock(3).isSelect() && scoreSheet.getLowerSectionBlock(4).isSelect()
        && scoreSheet.getLowerSectionBlock(5).isSelect() && scoreSheet.getLowerSectionBlock(6).isSelect() && scoreSheet.getLowerSectionBlock(7).isSelect())
        {
            lowerSectionAllFilled = true;
            scoreSheet.sumUpLowerSection();
        }
    }
    
    // Check for the scores of combinations
    public void scoreSheetUpdate(Cup cup)
    {
        int currentScore = 0;
        int ones = 0;
        int twos = 0;
        int threes = 0;
        int fours = 0;
        int fives = 0;
        int sixs = 0;
        
        // Check the occurance of the face values
        for(int i = 0; i < cup.getCup().length; i++)
        {
            if(cup.getDie(i).getFaceValue() == 1)
            {
                ones++;
            }
            else if(cup.getDie(i).getFaceValue() == 2)
            {
                twos++;
            }
            else if(cup.getDie(i).getFaceValue() == 3)
            {
                threes++;
            }
            else if(cup.getDie(i).getFaceValue() == 4)
            {
                fours++;
            }
            else if(cup.getDie(i).getFaceValue() == 5)
            {
                fives++;
            }
            else if(cup.getDie(i).getFaceValue() == 6)
            {
                sixs++;
            }
        }
        
        // Check which combination the player gets
        
        // Check for the upper section
        if(ones > 0)
        {
            // Set the score
            currentScore = ones;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(1).isSelect())
            {
                scoreSheet.getUpperSectionBlock(1).setScore(currentScore);
            }
        }
        if(twos > 0)
        {
            // Set the score
            currentScore = 2 * twos;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(2).isSelect())
            {
                scoreSheet.getUpperSectionBlock(2).setScore(currentScore);
            }
        }
        if(threes > 0)
        {
            // Set the score
            currentScore = 3 * threes;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(3).isSelect())
            {
                scoreSheet.getUpperSectionBlock(3).setScore(currentScore);
            }
        }
        if(fours > 0)
        {
            // Set the score
            currentScore = 4 * fours;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(4).isSelect())
            {
                scoreSheet.getUpperSectionBlock(4).setScore(currentScore);
            }
        }
        if(fives > 0)
        {
            // Set the score
            currentScore = 5 * fives;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(5).isSelect())
            {
                scoreSheet.getUpperSectionBlock(5).setScore(currentScore);
            }
        }
        if(sixs > 0)
        {
            // Set the score
            currentScore = 6 * sixs;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getUpperSectionBlock(6).isSelect())
            {
                scoreSheet.getUpperSectionBlock(6).setScore(currentScore);
            }
        }
        
        // Check for lower section
        
        // Checks for 3 of a kind
        if(ones >= 3 || twos >= 3 || threes >= 3 || fours >= 3 || fives >= 3 || sixs >= 3)
        {
            // Set the score for 3 of a kind
            currentScore = cup.getTotalValue();
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getLowerSectionBlock(1).isSelect())
            {
                scoreSheet.getLowerSectionBlock(1).setScore(currentScore);
            }
            
            // Checks for 4 of a kind
            if(ones >= 4 || twos >= 4 || threes >= 4 || fours >= 4 || fives >= 4 || sixs >= 4)
            {
                // Set the score for 4 of a kind
                currentScore = cup.getTotalValue();
                
                // Only set the score if the block is not selected
                if(!scoreSheet.getLowerSectionBlock(2).isSelect())
                {
                    scoreSheet.getLowerSectionBlock(2).setScore(currentScore);
                }
                
                // Checks for yahtzee
                if(ones == 5 || twos == 5 || threes == 5 || fours == 5 || fives == 5 || sixs == 5)
                {
                    // Set the score for yahtzee
                    currentScore = 50;
                    
                    // Only set the score if the block is not selected
                    if(!scoreSheet.getLowerSectionBlock(6).isSelect())
                    {
                        scoreSheet.getLowerSectionBlock(6).setScore(currentScore);
                    }
                    // The joker rule will apply if the player get a yahtzee already
                    else if(scoreSheet.getLowerSectionBlock(6).isSelect())
                    {
                        // If the yahtzee box is filled and not a 0 a yahtzee bouns and joker rule will apply
                        if(!scoreSheet.getLowerSectionBlock(6).getScore().equals("0"))
                        {
                            // Set score for yahtzee bouns
                            yahtzeeValue = cup.getDie(0).getFaceValue();
                            scoreSheet.getLowerSectionBlock(6).setScore( (Integer.parseInt(scoreSheet.getLowerSectionBlock(6).getScore()) + 100) );
                            
                            // Apply the joker rule
                            jokerRule = true;
                        }
                        // If it is filled with 0 only the joker rule will apply
                        else
                        {
                            jokerRule = true;
                        }
                    }
                }
            }
            // Checks for full house
            else if(ones == 3 || twos == 3 || threes == 3 || fours == 3 || fives == 3 || sixs == 3)
            {
                if(ones == 2 || twos == 2 || threes == 2 || fours == 2 || fives == 2 || sixs == 2)
                {
                    // Set the score for full house
                    currentScore = 25;
                    
                    // Only set the score if the block is not selected
                    if(!scoreSheet.getLowerSectionBlock(3).isSelect())
                    {
                        scoreSheet.getLowerSectionBlock(3).setScore(currentScore);
                    }
                }
            }
        }
        // Checks for small straight
        else if((ones >= 1 && twos >= 1 && threes >= 1 && fours >= 1) || (twos >= 1 && threes >= 1 && fours >= 1 && fives >= 1) || (threes >= 1 && fours >= 1 && fives >= 1 && sixs >= 1))
        {
            // Set the score for small straight
            currentScore = 30;
            
            // Only set the score if the block is not selected
            if(!scoreSheet.getLowerSectionBlock(4).isSelect())
            {
                scoreSheet.getLowerSectionBlock(4).setScore(currentScore);
            }
            
            // Checks for large straight
            if((ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1) || (twos == 1 && threes == 1 && fours == 1 && fives == 1 && sixs == 1))
            {
                // Set the score for large straight
                currentScore = 40;
                
                // Only set the score if the block is not selected
                if(!scoreSheet.getLowerSectionBlock(5).isSelect())
                {
                    scoreSheet.getLowerSectionBlock(5).setScore(currentScore);
                }
            }
        }
        
        // Set the score for chance
        currentScore = cup.getTotalValue();
        
        // Only set the score if the block is not selected
        if(!scoreSheet.getLowerSectionBlock(7).isSelect())
        {
            scoreSheet.getLowerSectionBlock(7).setScore(currentScore);
        }
    }
}
