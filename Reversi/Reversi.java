import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
/**
 * This is the driver class.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class Reversi
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Reversi");
        JPanel mainPanel = new JPanel();
        JPanel upperPanel = new JPanel();
        InfoPanel infoPanel = new InfoPanel();
        GamePanel gamePanel = new GamePanel(infoPanel);
        ButtonPanel buttonPanel = new ButtonPanel(gamePanel);
        
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(infoPanel, BorderLayout.WEST);
        upperPanel.add(buttonPanel, BorderLayout.EAST);
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(upperPanel, BorderLayout.NORTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
