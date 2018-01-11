import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
/**
 * This is the information panel.
 * The panel sets the current turn and number of each color of discs.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class InfoPanel extends JPanel
{
    // Instance field
    private JLabel turn, blackDisc, whiteDisc;
    
    private final int PANEL_WIDTH = 380;
    private final int PANEL_HEIGHT = 80;
    
    private Font font = new Font("Courier", Font.PLAIN, 25);
    private final Dimension PANEL_SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    private Color panelColor = new Color(156,159,132);
    
    // Constructor
    public InfoPanel()
    {
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(panelColor);
        
        turn = new JLabel("Current Turn: ");
        blackDisc = new JLabel("Number of Black Disc: ");
        whiteDisc = new JLabel("Number of White Disc: ");
        
        
        turn.setFont(font);
        blackDisc.setFont(font);
        whiteDisc.setFont(font);
        
        add(turn);
        add(blackDisc);
        add(whiteDisc);
    }
    
    // Clear the information
    public void clear()
    {
        turn.setText("Current Turn: ");
        blackDisc.setText("Number of Black Disc: ");
        whiteDisc.setText("Number of White Disc: ");
    }
    
    // Set the labels
    public void setTurn(String turn)
    {
        this.turn.setText("Current Turn: " + turn);
    }
    public void setNumOfDisc(int black, int white)
    {
        blackDisc.setText("Number of Black Disc: " + Integer.toString(black));
        whiteDisc.setText("Number of White Disc: " + Integer.toString(white));
    }
}
