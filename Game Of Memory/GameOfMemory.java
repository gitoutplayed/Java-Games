import javax.swing.JFrame;
/**
 * This is the driver class
 * 
 * @author (Michael Fan) 
 * @version (April 19, 2015)
 */
public class GameOfMemory
{
    public static void main(String[] args)
    {
        // Setup the JFrame
        JFrame frame = new JFrame("Game of Memory");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GamePanel());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
