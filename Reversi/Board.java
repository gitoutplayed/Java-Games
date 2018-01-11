import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
/**
 * This class represents the board.
 * The board has both the squares and discs drawn onto it.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class Board
{
    // Instance field
    private Square[][] squares;
    private ArrayList<Disc> discs;

    public final int ROW = 8;
    public final int COL = 8;

    // Constructor
    public Board()
    {
        squares = new Square[ROW][COL];
        discs = new ArrayList<Disc>();

        init();
    }
    
    // Initalizes the board
    public void init()
    {
        discs.clear();
        initSquaresList();
        
        loadSquare();
        
        // Placing the inital 4 discs onto the center of the board
        int x,y;
        x = squares[3][3].getX();
        y = squares[3][3].getY();
        discs.add(new Disc(x,y,Disc.WHITE));

        x = squares[3][4].getX();
        y = squares[3][4].getY();
        discs.add(new Disc(x,y,Disc.BLACK));

        x = squares[4][3].getX();
        y = squares[4][3].getY();
        discs.add(new Disc(x,y,Disc.BLACK));

        x = squares[4][4].getX();
        y = squares[4][4].getY();
        discs.add(new Disc(x,y,Disc.WHITE));
    }
    
    // Initalizes the squares list
    private void initSquaresList()
    {
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                squares[row][col] = null;
            }
        }
    }
    
    // Load the squares
    private void loadSquare()
    {
        int x = 0;
        int y = 0;

        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                squares[row][col] = new Square(x,y);
                x += Square.SIZE;
            }
            x = 0;
            y += Square.SIZE;
        }
    }
    
    // Getters ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Returns the discs list
    public ArrayList<Disc> getDiscs()
    {
        return discs;
    }
    
    // Return the disc that matches the specific x,y coordiantes 
    public Disc getDiscByLocation(int x, int y)
    {
        Disc tempDisc = null;

        for(int i = 0; i < discs.size(); i++)
        {
            if(discs.get(i).getX() == x && discs.get(i).getY() == y)
            {
                tempDisc = discs.get(i);
            }
        }

        return tempDisc;
    }
    
    // Reutrn the row and col of the square that matches the specific x,y coordiantes 
    public int[] getSquareIndex(int x, int y)
    {
        int[] temp = new int[2]; // temp[0] is row and temp[1] is col
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                if(squares[row][col].getX() == x && squares[row][col].getY() == y)
                {
                    temp[0] = row;
                    temp[1] = col;
                }
            }
        }
        return temp;
    }
    
    // Return the square that matches the row and col number
    public Square getSquareByIndex(int row, int col)
    {
        return squares[row][col];
    }
    
    // Return the square that matches the specific x,y coordiantes 
    public Square getSquareByLocation(int x, int y)
    {
        Square square = null;
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                if(x >= squares[row][col].getX() && x <= squares[row][col].getX() + Square.SIZE && y >= squares[row][col].getY()
                && y <= squares[row][col].getY() + Square.SIZE)
                {
                    square = squares[row][col];
                }
            }
        }
        return square;
    }
    
    // Return the total number of black discs
    public int getNumOfBlackDisc()
    {
        int count = 0;
        for(int i = 0; i < discs.size(); i++)
        {
            if(discs.get(i).getColor().equals(Disc.BLACK))
            {
                count++;
            }
        }
        return count;
    }
    
    // Return the total number of white discs
    public int getNumOfWhiteDisc()
    {
        int count = 0;
        for(int i = 0; i < discs.size(); i++)
        {
            if(discs.get(i).getColor().equals(Disc.WHITE))
            {
                count++;
            }
        }
        return count;
    }
    
    // End of Getters //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Add a new disc
    public void placeNewDisc(int x, int y, Color color)
    {
        discs.add(new Disc(x,y,color));
    }

    // Draws both the squares and discs
    public void draw(Graphics g)
    {
        // Draws the squares
        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                squares[row][col].draw(g);
            }
        }

        // Draws the discs
        for(int i = 0; i < discs.size(); i++)
        {
            discs.get(i).draw(g);
        }
    }
}
