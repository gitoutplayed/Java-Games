import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
/**
 * This is the button panel.
 * The panel has 4 buttons: twoPlayer, singlePlayer, start and newGame.
 * 
 * @author (Michael Fan) 
 * @version (Apr 21, 2016)
 */
public class ButtonPanel extends JPanel implements ActionListener
{
    // Instance field
    private JButton twoPlayer, singlePlayer, start, newGame;
    private GamePanel gamePanel;
    
    private final int PANEL_WIDTH = 220;
    private final int PANEL_HEIGHT = 80;
    
    private final Dimension PANEL_SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    
    // Constructor
    public ButtonPanel(GamePanel gamePanel)
    {
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createTitledBorder("Control"));
        setLayout(new GridLayout(2,2));
        
        twoPlayer = new JButton("Two Player");
        singlePlayer = new JButton("Single Player");
        start = new JButton("Start");
        newGame = new JButton("New Game");
        
        twoPlayer.addActionListener(this);
        singlePlayer.addActionListener(this);
        start.addActionListener(this);
        newGame.addActionListener(this);
        
        start.setEnabled(false);
        newGame.setEnabled(false);
        
        add(twoPlayer);
        add(singlePlayer);
        add(start);
        add(newGame);
        
        this.gamePanel = gamePanel;
    }
    
    // Button functions
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton) e.getSource();
        
        // The two player button sets the game mode to two player mode
        if(button == twoPlayer)
        {
            twoPlayer.setEnabled(false);
            singlePlayer.setEnabled(false);
            start.setEnabled(true);
            newGame.setEnabled(true);
            gamePanel.setTwoPlayer(true);
            gamePanel.setSinglePlayer(false);
        }
        // The single player button sets the game mode to single player
        else if(button == singlePlayer)
        {
            twoPlayer.setEnabled(false);
            singlePlayer.setEnabled(false);
            start.setEnabled(true);
            newGame.setEnabled(true);
            gamePanel.setTwoPlayer(false);
            gamePanel.setSinglePlayer(true);
        }
        // The start button will start the game
        else if(button == start)
        {
            gamePanel.init();
            start.setEnabled(false);
        }
        // The new game will ends the current game and make a new one
        else if(button == newGame)
        {
            gamePanel.clear();
            twoPlayer.setEnabled(true);
            singlePlayer.setEnabled(true);
            start.setEnabled(false);
        }
    }
}
