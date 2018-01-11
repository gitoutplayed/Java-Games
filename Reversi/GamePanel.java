import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
/**
 * This is the game panel.
 * The panel has all the game objects: Board, BoardLogic and AI.
 * The game is played in this class.
 * 
 * @author (Michael Fan) 
 * @version (Apr 19, 2016)
 */
public class GamePanel extends JPanel implements MouseListener, ActionListener
{
    // Instance field
    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 600;

    private Board board;
    private BoardLogic boardLogic;
    private AI ai;

    private Timer timer;

    private final Dimension PANEL_SIZE = new Dimension(PANEL_WIDTH,PANEL_HEIGHT);

    private int state = -1;
    private final int IN_TURN = 0;
    private final int END_TURN = 1;
    private final int FINISHED = 3;
    private final int AI = 4;

    private int turn = -1;
    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;

    private boolean singlePlayer = false;;
    private boolean twoPlayer = false;
    private boolean waiting = false;

    private Color currentColor;

    private InfoPanel infoPanel;

    // Constructor
    public GamePanel(InfoPanel infoPanel)
    {
        setPreferredSize(PANEL_SIZE);
        addMouseListener(this);

        this.infoPanel = infoPanel;

        board = new Board();
        boardLogic = new BoardLogic(board);
        ai = new AI(board, boardLogic);
        timer = new Timer(1000, this); // Timer for the AI
    }

    // Paint the panel
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        board.draw(g);
    }

    // Initializes the game 
    public void init()
    {
        board.init();
        boardLogic.init();

        turn = BLACK_TURN;
        currentColor = Disc.BLACK;
        state = IN_TURN;
        waiting = false;

        boardLogic.findAllLegalMoves(Disc.BLACK);

        infoPanel.setTurn(getTurn());
        infoPanel.setNumOfDisc(board.getNumOfBlackDisc(), board.getNumOfWhiteDisc());

        repaint();
    }

    // Clear the game
    public void clear()
    {
        board.init();
        boardLogic.init();

        turn = -1;
        state = -1;

        infoPanel.clear();

        repaint();
    }

    // Set to two player
    public void setTwoPlayer(boolean twoPlayer)
    {
        this.twoPlayer = twoPlayer;
    }

    // Set to one player
    public void setSinglePlayer(boolean singlePlayer)
    {
        this.singlePlayer = singlePlayer;
    }

    // Return the current turn
    private String getTurn()
    {
        String turn = "";
        if(this.turn == BLACK_TURN)
        {
            turn = "Black";
        }
        else if(this.turn == WHITE_TURN)
        {
            turn = "White";
        }
        return turn;
    }

    // Check if the board is full
    private boolean isFull()
    {
        return (board.getDiscs().size() == 64) ? true : false;
    }

    /////////////////////////////////////////////////// Game Play

    /**
     * In single player mode the player will always play black.
     * The player can make a move when the state is IN_TURN.
     * The player cannot do anything when the AI is making its move(waiting will be true if AI is moving).
     * Turn is switched in the END_TURN state.
     * The game ends when state is FINISHED.
     */
    private void singlePlayer(int x, int y)
    {
        if(!waiting)
        {
            // Player can make a move
            if(state == IN_TURN)
            {
                Square square = null;

                square = board.getSquareByLocation(x,y);

                // Check if the player clicked on a movable sqaure
                if(square.isMovable())
                {
                    board.placeNewDisc(square.getX(), square.getY(), currentColor);
                    boardLogic.flipAllPossibleDisc(square, currentColor);
                    boardLogic.updateBoard("Clear Legal Move", currentColor);

                    state = END_TURN;
                }
                // A message will pop up if the player clicked on a non-movable square
                else
                {
                    JOptionPane.showMessageDialog(this, "Ilegal Move");
                }
            }
            // Switches turn
            if(state == END_TURN)
            {
                // If the board is not full then switches turn
                if(!isFull())
                {
                    // Switch turn if the current turn is black and white has at least 1 legal move
                    if(turn == BLACK_TURN && boardLogic.findAllLegalMoves(Disc.WHITE))
                    {
                        turn = WHITE_TURN;
                        currentColor = Disc.WHITE;
                        waiting = true;
                        timer.start();
                    }
                    // Black gets to move again if white has no legal move
                    else
                    {
                        // If black has no legal moves either then the game ends
                        if(!boardLogic.findAllLegalMoves(currentColor))
                        {
                            state = FINISHED;
                        }
                        // If black has at least 1 legal move then the state will be back to IN_TURN
                        else
                        {
                            state = IN_TURN;
                        }
                    }
                }
                // The game is finished when the board is full
                else
                {
                    state = FINISHED;
                }
                // Updates the InfoPanel
                infoPanel.setTurn(getTurn());
                infoPanel.setNumOfDisc(board.getNumOfBlackDisc(), board.getNumOfWhiteDisc());
            }

            repaint();

            // A message will pop up when the game is finished.
            if(state == FINISHED)
            {
                if(board.getNumOfBlackDisc() > board.getNumOfWhiteDisc())
                {
                    JOptionPane.showMessageDialog(this, "Black Wins");
                }
                else if(board.getNumOfBlackDisc() < board.getNumOfWhiteDisc())
                {
                    JOptionPane.showMessageDialog(this, "White Wins");
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "It's a draw");
                }
            }
        }
    }

    /**
     * The AI will make a move approximately every 2 seconds.
     * The AI will always play white.
     * The turn structure of the AI is the same as the player.
     */
    public void actionPerformed(ActionEvent e)
    {
        // AI makes the move
        Square square = ai.pickBestMove(currentColor);
        board.placeNewDisc(square.getX(), square.getY(), currentColor);
        boardLogic.flipAllPossibleDisc(square, currentColor);
        boardLogic.updateBoard("Clear Legal Move", currentColor);

        // If the board is not full then switches turn
        if(!isFull())
        {
            // Switch turn only if the current turn is white and black has at least 1 legal move
            if(turn == WHITE_TURN && boardLogic.findAllLegalMoves(Disc.BLACK))
            {
                turn = BLACK_TURN;
                currentColor = Disc.BLACK;
                state = IN_TURN;
                waiting = false;
                timer.stop(); // Stops the AI
            }
            else
            {
                if(!boardLogic.findAllLegalMoves(currentColor))
                {
                    state = FINISHED;
                    waiting = false;
                    timer.stop(); // Stops the AI
                }
            }
        }
        // The game is finished when the board is full
        else
        {
            state = FINISHED;
            waiting = false;
            timer.stop(); // Stops the AI
        }

        // Updates the InfoPanel
        infoPanel.setTurn(getTurn());
        infoPanel.setNumOfDisc(board.getNumOfBlackDisc(), board.getNumOfWhiteDisc());

        repaint();

        // A message will pop up when the game is finished
        if(state == FINISHED)
        {
            timer.stop();
            if(board.getNumOfBlackDisc() > board.getNumOfWhiteDisc())
            {
                JOptionPane.showMessageDialog(this, "Black Wins");
            }
            else if(board.getNumOfBlackDisc() < board.getNumOfWhiteDisc())
            {
                JOptionPane.showMessageDialog(this, "White Wins");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "It's a draw");
            }
        }
    }

    /**
     * The two player mode.
     * The turn structure is very similar to that of single player.
     * The player can make a move when the state is IN_TURN.
     * Turn is switched in the END_TURN state.
     * The game ends when state is FINISHED.
     * 
     */
    private void twoPlayer(int x, int y)
    {
        // The player can make a move.
        if(state == IN_TURN)
        {
            Square square = null;

            square = board.getSquareByLocation(x,y);

            // Check if the player clicked a movable square
            if(square.isMovable())
            {
                board.placeNewDisc(square.getX(), square.getY(), currentColor);
                boardLogic.flipAllPossibleDisc(square, currentColor);
                boardLogic.updateBoard("Clear Legal Move", currentColor);

                state = END_TURN;
            }
            // A message will pop up if the player clicked on a non-movable square
            else
            {
                JOptionPane.showMessageDialog(this, "Ilegal Move");
            }
        }

        // Switches turn
        if(state == END_TURN)
        {
            if(!isFull())
            {
                // Switch turn if the current turn is black and that white has at least 1 legal move
                if(turn == BLACK_TURN && boardLogic.findAllLegalMoves(Disc.WHITE))
                {
                    turn = WHITE_TURN;
                    currentColor = Disc.WHITE;
                    state = IN_TURN;
                }
                // Switch turn if the current turn is white and that black has at least 1 legal move
                else if(turn == WHITE_TURN && boardLogic.findAllLegalMoves(Disc.BLACK))
                {
                    turn = BLACK_TURN;
                    currentColor = Disc.BLACK;
                    state = IN_TURN;
                }
                // The turn does not switch if there are no legal moves avaliable
                else
                {
                    // Continues the current turn if the there are legal moves
                    if(boardLogic.findAllLegalMoves(currentColor))
                    {
                        state = IN_TURN;
                    }
                    // The game ends if there is no legal move
                    else
                    {
                        state = FINISHED;
                    }
                }
            }
            else
            {
                state = FINISHED;
            }
            infoPanel.setTurn(getTurn());
            infoPanel.setNumOfDisc(board.getNumOfBlackDisc(), board.getNumOfWhiteDisc());
        }

        repaint();

        // The game finishes
        if(state == FINISHED)
        {
            if(board.getNumOfBlackDisc() > board.getNumOfWhiteDisc())
            {
                JOptionPane.showMessageDialog(this, "Black Wins");
            }
            else if(board.getNumOfBlackDisc() < board.getNumOfWhiteDisc())
            {
                JOptionPane.showMessageDialog(this, "White Wins");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "It's a draw");
            }
        }
    }

    // Registering the mouse click.
    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        
        // Make sure the program does not beark when the mouse is released outside of the window
        try
        {
            if(twoPlayer)
            {
                twoPlayer(x,y);
            }
            else if(singlePlayer)
            {
                singlePlayer(x,y);
            }
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(this, "Error: " + exception.getMessage());
        }
    }

    /////////////////////////////////////////////////// End

    public void mouseClicked(MouseEvent e)
    {}

    public void mousePressed(MouseEvent e)
    {}

    public void mouseEntered(MouseEvent e)
    {}

    public void mouseExited(MouseEvent e)
    {}
}
