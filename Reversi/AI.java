import java.awt.Color;
import java.util.ArrayList;
/**
 * This is the AI.
 * The AI will always pick the move that flips the most discs.
 * 
 * @author (Michael Fan) 
 * @version (Apr 26, 2016)
 */
public class AI
{
    // Instance field
    private Board board;
    private BoardLogic boardLogic;
    
    // Constructor
    public AI(Board board, BoardLogic boardLogic)
    {
        this.board = board;
        this.boardLogic = boardLogic;
    }

    /**
     * The method returns a square.
     * The method will find the move(square) that flips the most discs.
     */
    public Square pickBestMove(Color color)
    {
        ArrayList<Square> tempList = new ArrayList<Square>();
        Square square = null;
        
        // Get rid of all the null squares
        for(int i = 0; i < boardLogic.getLocation().size(); i++)
        {
            if(boardLogic.getLocation().get(i) != null)
            {
                tempList.add(boardLogic.getLocation().get(i));
            }
        }
        
        square = tempList.get(0); // If all the moves results in the same number of fliped discs then it will be the first square in the list
        
        // Compares the number of fliped discs of each move(square)
        for(int i = 0; i < tempList.size() - 1; i++)
        {
            if(getNumOfAllFlipedDisc(tempList.get(i),color) <  getNumOfAllFlipedDisc(tempList.get(i + 1),color))
            {
                square = tempList.get(i + 1); // Assign the square that results in the most number of fliped discs
            }
        }
        
        return square;
    }

    /**
     * The method returns the total number of fliped discs of a move(square).
     * The method counts the number of fliped discs in 8 directions.
     */
    private int getNumOfAllFlipedDisc(Square square,Color color)
    {
        int count = 0;

        count += findNumOfFlipDisc(square, color, -1, 0).size(); // Count up

        count += findNumOfFlipDisc(square, color, -1, -1).size(); // Count left up

        count += findNumOfFlipDisc(square, color, 0, -1).size(); // Count left

        count += findNumOfFlipDisc(square, color, 1, -1).size(); // Count left down

        count += findNumOfFlipDisc(square, color, 1, 0).size(); // Count down

        count += findNumOfFlipDisc(square, color, 1, 1).size(); // Count right down

        count += findNumOfFlipDisc(square, color, 0, 1).size(); // Count right

        count += findNumOfFlipDisc(square, color, -1, 1).size(); // Count right up

        return count;
    }
    
    /**
     * The method returns a list of discs that will fliped.
     * The method finds the number of fliped discs in 1 direction as indicated by the integer value "vertical" and "horizontal".
     * The method works in a similar way as the flipDisc() in the BoardLogic class.
     * The method only find the fliped discs but does not flip them.
     */
    private ArrayList<Disc> findNumOfFlipDisc(Square square, Color color, int vertical, int horizontal)
    {
        ArrayList<Disc> tempList = new ArrayList<Disc>();
        int[] temp = board.getSquareIndex(square.getX(), square.getY()); // temp[0] = row and temp[1] = col
        int row = temp[0];
        int col = temp[1];

        row += vertical;
        col += horizontal;

        boolean flipping  = true;
        
        // Check if within bound
        if(row < board.ROW && row > -1 && col < board.COL && col > -1)
        {
            while(flipping)
            {
                int x = board.getSquareByIndex(row,col).getX();
                int y = board.getSquareByIndex(row,col).getY();
                Disc tempDisc = board.getDiscByLocation(x,y);

                // Keep flipping if there is a different color of disc beside the current square
                if(tempDisc != null)
                {
                    if(!color.equals(tempDisc.getColor()))
                    {
                        tempList.add(tempDisc);
                        row += vertical;
                        col += horizontal;
                        
                        // Ends the search and clears the list if out of bound
                        if(row >= board.ROW || row < 0 || col >= board.COL || col < 0)
                        {
                            flipping = false;
                            tempList.clear();
                        }
                    }
                    // Ends the search
                    else
                    {
                        flipping = false;
                    }
                }
                // Ends the search if there is no disc beside the current square
                else
                {
                    flipping = false;
                    tempList.clear();
                }
            }
        }
        
        return tempList;
    }
}
