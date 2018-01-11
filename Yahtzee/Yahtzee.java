import javax.swing.JFrame;
/**
 * This is the driver class.
 * 
 * @author (Michael Fan) 
 * @version (April 16, 2015)
 */
public class Yahtzee
{
    public static void main(String[] args)
    {
        // Setup the JFrame
        JFrame frame = new JFrame("Yahtzee");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Table());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
