import java.util.ArrayList;
import java.awt.Color;
/**
 * Handles the logic of the board.
 * The logic includes find legal moves and flip discs.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class BoardLogic
{
    // Instance field
    private Board board;
    private ArrayList<Square> location;

    // Constructor
    public BoardLogic(Board board)
    {
        this.board = board;
        location = new ArrayList<Square>();
    }

    // Return location
    public ArrayList<Square> getLocation()
    {
        return location;
    }

    // Initializes location 
    public void init()
    {
        location.clear();
    }

    /**
     * The method returns true if there is at least 1 legal moves.
     * The methods looks for all the legal moves.
     * The method looks for a legal move from a specific color of disc in 8 direction.
     * The legal moves or the movable squares are stored in a array list called "location".
     */
    public boolean findAllLegalMoves(Color color)
    {
        ArrayList<Disc> tempDiscs = board.getDiscs();
        boolean legalMove = false;
        for(int i = 0; i < tempDiscs.size(); i++)
        {
            // Find the disc that matches the color
            if(tempDiscs.get(i).getColor().equals(color))
            {
                int x = tempDiscs.get(i).getX();
                int y = tempDiscs.get(i).getY();
                int[] temp = board.getSquareIndex(x,y); // temp[0] == row and temp[1] == col
                Square tempSquare = null;

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), -1, 0); // search up
                location.add(tempSquare); // Stores this sqaure in the list

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), -1, -1); // search left up
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), -1, 1); // search right up 
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1] , tempDiscs.get(i), 0, -1); // search left 
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), 1, -1); // search left down 
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), 1, 0); // search down
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), 1, 1); // search right down 
                location.add(tempSquare);

                tempSquare = findMovableSquare(temp[0], temp[1], tempDiscs.get(i), 0, 1); // search right
                location.add(tempSquare);
            }
        }
        
        // Check if there is at least 1 legal move
        int legalSquare = 0;
        for(int i = 0; i < location.size(); i++)
        {
            if(location.get(i) != null)
            {
                legalSquare++;
            }
        }
        
        if(legalSquare > 0)
        {
            legalMove = true;
            updateBoard("Show Legal Move", color); // Show all the legal moves
        }

        return legalMove;
    }

    /**
     * The method return a movable square. Returns null if no movable square is found.
     * The method looks for 1 movable square in 1 direction from a specific disc location. For exmaple, up, down etc....
     * The the varible "vertical" and "hozriontal" indicates the direction of searching.
     */
    private Square findMovableSquare(int row, int col, Disc currentDisc, int vertical, int horizontal)
    {
        boolean found = false;
        boolean first = true;
        boolean exit = false;
        Square tempSquare = null;
        row += vertical;
        col += horizontal;
        // Only start searching if in bound
        if(row < board.ROW && row > -1 && col < board.COL && col > -1)
        {
            // Keeping searching if not found and not over
            while(!found && !exit)
            {
                int x = board.getSquareByIndex(row,col).getX();
                int y = board.getSquareByIndex(row,col).getY();
                Disc tempDisc = board.getDiscByLocation(x,y);
                
                // If the adjacent square is no empty than keeping searching
                if(tempDisc != null)
                {
                    first = false; // Indicates that this is not first time searching
                    // Check if is a different color
                    if(!currentDisc.getColor().equals(tempDisc.getColor()))
                    {
                        row += vertical;
                        col += horizontal;
                        // Ends the search if out of bound
                        if(row >= board.ROW || row < 0 || col >= board.COL || col < 0)
                        {
                            exit = true;
                        }
                    }
                    // Ends the search if the color is the same
                    else
                    {
                        exit = true; 
                    }
                }
                // If there is no disc beside it
                else
                {
                    // If it is only the first time searching that means that the square adjacent to the square where the search starts is empty
                    if(first)
                    {
                        exit = true; // therefore ends the search
                    }
                    // If at least 1 disc is in bewteen then the square is movable
                    else
                    {
                        tempSquare = board.getSquareByIndex(row,col);
                        found = true;
                    }
                }
            }
        }
        return tempSquare;
    }

    /**
     * Updates the board.
     * The methods either show legal move or clear the legal move.
     */
    public void updateBoard(String function, Color color)
    {
        for(int i = 0; i < location.size(); i++)
        {
            if(function.equalsIgnoreCase("Show Legal Move"))
            {
                if(location.get(i) != null)
                {
                    location.get(i).setMovable(true, color);
                }
            }
            else if(function.equalsIgnoreCase("Clear Legal Move"))
            {
                if(location.get(i) != null)
                {
                    location.get(i).setMovable(false, color);
                }
            }
        }
        if(function.equalsIgnoreCase("Clear Legal Move"))
        {
            location.clear(); // Clear the list
        }
    }

    /**
     * The method flips the discs in all 8 direction. 
     */
    public void flipAllPossibleDisc(Square square, Color color)
    {
        flipDisc(square, color, -1, 0); // Flip up

        flipDisc(square, color, -1, -1); // Flip left up

        flipDisc(square, color, 0, -1); // Flip left

        flipDisc(square, color, 1, -1); // Flip left down

        flipDisc(square, color, 1, 0); // Flip down

        flipDisc(square, color, 1, 1); // Flip right down

        flipDisc(square, color, 0, 1); // Flip right

        flipDisc(square, color, -1, 1); // Flip right up
    }

    /**
     * The method flips the discs in 1 single direction if possible.
     * The method searches from a specific square(the movable square) and work backwards.
     * The direction is indicated by the integer value "vertical", and "horizontal".
     */
    public void flipDisc(Square square, Color color, int vertical, int horizontal)
    {
        ArrayList<Disc> tempList = new ArrayList<Disc>();
        int[] temp = board.getSquareIndex(square.getX(), square.getY()); // temp[0] = row and temp[1] = col
        int row = temp[0];
        int col = temp[1];

        row += vertical;
        col += horizontal;

        boolean flipping = true;
        
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
                        tempList.add(tempDisc); // Adds the disc to the list
                        row += vertical;
                        col += horizontal;
                        
                        // Ends the search if out of bound
                        if(row >= board.ROW || row < 0 || col >= board.COL || col < 0)
                        {
                            flipping = false;
                        }
                    }
                    else
                    {
                        flipping = false;
                        // Flip the discs
                        for(int i = 0; i < tempList.size(); i++)
                        {
                            if(tempList.get(i) != null)
                            {
                                tempList.get(i).flip();
                            }
                        }
                    }
                }
                // Ends the search if there is no disc beside it
                else
                {
                    flipping = false;
                }
            }
        }
    }
}
