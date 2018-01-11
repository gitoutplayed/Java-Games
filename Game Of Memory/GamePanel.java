import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
/**
 * This class is the main panel. The game happens in this class.
 * Did not use any JButtons because only a new game button is nesseary and I can't find a place to just put one button.
 * The game has it's own feature(see quitOrRestart method) that replaces the new game button.
 * 
 * @author (Michael Fan) 
 * @version (April 19, 2015)
 */
public class GamePanel extends JPanel
{
    // Instance field
    private int state;
    private int pairs = 15;
    private int timeCount = 0;
    private int card1Row,card1Col;
    private int card2Row,card2Col;
    
    private final int ROW = 5;
    private final int COL = 6;
    private final int SELECTING = 1;
    private final int ONE_CARD_FLIPED = 2;
    private final int WAITING = 3;
    private final int FINISHED = 4;
    
    private Deck deck;
    
    private Timer timer;
    private Color panelColor = new Color(176,196,222);
    private Dimension panelSize = new Dimension(800,800);
    
    // Constructor
    public GamePanel()
    {
        setPreferredSize(panelSize);
        setBackground(panelColor);
        
        deck = new Deck();
        
        timer = new Timer(10,new TimerListener());
        
        addMouseListener(new Listener());
        
        // The state is SELECTING when the game fisrt starts
        state = SELECTING;
    }
    
    // Methods
    
    // The MouseListener class. The game happens here
    private class Listener implements MouseListener
    {
        // All the action happens here
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            
            // In SELECTING state the player may wish to click on any card
            if(state == SELECTING)
            {
                findWhichCardIsClicked(x,y);
            }
            // In ONE_CARD_FLIPED state the player may click any card other the one that is already been selected
            else if(state == ONE_CARD_FLIPED)
            {
                findWhichCardIsClicked(x,y);
            }
            // The player can't do anything in WAITING state
            else if(state == WAITING)
            {}
            // The game ends when it reaches FINISHED state. The player can either restart or quit
            else if(state == FINISHED)
            {
                quitOrRestart(x,y);
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
    
    // The Timer class
    private class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            timerControl();
        }
    }
    
    // The draw method
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // Draw the deck
        deck.draw(g);
        
        // Instructions for quit ond restart
        if(state == FINISHED)
        {
            Font font = new Font("Arial",Font.PLAIN,30);
            g.setFont(font);
            g.drawString("The game is finished",265,220);
            g.drawString("Click on the left side to restart or the right side to quit",50,400);
        }
    }
    
    // The timer control 
    public void timerControl()
    {
        timeCount++;
        if(timeCount == 80)
        {
            // If the cards match then remove them
            if(deck.isEqual(deck.getCard(card1Row,card1Col),deck.getCard(card2Row,card2Col)))
            {
                timeCount = 0;
                deck.getCard(card1Row,card1Col).remove();
                deck.getCard(card2Row,card2Col).remove();
                timer.stop();
                state = SELECTING;
                
                card1Row = -1;
                card1Col = -1;
                
                pairs--;
                
                // If all the pairs are found then finish the game
                if(pairs == 0)
                {
                    state = FINISHED;
                    
                    // Repaint the screen
                    repaint();
                }
            }
            // If not then flip them back
            else
            {
                timeCount = 0;
                deck.getCard(card1Row,card1Col).unFlip();
                deck.getCard(card2Row,card2Col).unFlip();
                timer.stop();
                state = SELECTING;
                
                card1Row = -1;
                card1Col = -1;
            }
            // Repaint the screen
            repaint();
        }
    }
    
    // Find which card is clicked
    public void findWhichCardIsClicked(int x,int y)
    {
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                if( x >= deck.getCard(row,col).getX() && x <= deck.getCard(row,col).getX() + deck.getCard(row,col).getWidth() && 
                y >= deck.getCard(row,col).getY() && y <= deck.getCard(row,col).getY() + deck.getCard(row,col).getHeight() && !deck.getCard(row,col).isFlip()
                && !deck.getCard(row,col).isRemove())
                {
                    if(state == SELECTING)
                    {
                        deck.getCard(row,col).flip();
                        card1Row = row;
                        card1Col = col;
                        state = ONE_CARD_FLIPED;
                    }
                    else if(state == ONE_CARD_FLIPED)
                    {
                        deck.getCard(row,col).flip();
                        card2Row = row;
                        card2Col = col;
                        state = WAITING;
                        timer.start();
                    }
                }
            }
        }
    }
    
    // Determine the user want to quit or restart
    public void quitOrRestart(int x,int y)
    {
        // If the user click on the left then restart
        if(x < 400)
        {
            newGame();
        }
        // If the user click on the right then quit
        else if(x > 400)
        {
            System.exit(0);
        }
    }
    
    // Restart the game
    public void newGame()
    {
        state = SELECTING;
        deck.reset();
        repaint();
    }
}
