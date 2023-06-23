import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class Main {

    //Bots used to play                             //Placeholder values since it's not trained
    public static Bot testBot = new Bot(new double[]{0.5, 0.5, -0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}, false);
    public static Bot testBot2 = new Bot(new double[]{0.5, 0.5, -0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}, true);

    public static PromotionPanel promotionPanel = new PromotionPanel();
    public static String sBoard;

    //set true to play against a bot.
    public static final boolean botPlays = true;

    public static final boolean botPlaysWhite = false;

    public static void main(String[] args) {

        int squareSize = 100; //Can be changed but 100 is good
        //Default board
        Piece.board = new Piece[][]{{new Rook(true), new Knight(true), new Bishop(true), new King(true), new Queen(true), new Bishop(true), new Knight(true), new Rook(true)},
                {new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)},
                {new Rook(false), new Knight(false), new Bishop(false), new King(false), new Queen(false), new Bishop(false), new Knight(false), new Rook(false)}};

        updateSBoard(0, 0, 0, 0);
        MyPanel panel = new MyPanel(squareSize, Piece.board);           //Creates the panel
        MyMouseListener mouse = new MyMouseListener(squareSize, panel); //creates the MouseListener
        promotionPanel.setPanel(panel);

        //Sets up the frame
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("Chess");
        frame.setSize(9 * squareSize + 16, 8 * squareSize + 39);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("Chess_board.png");
        frame.setIconImage(icon.getImage());

        //Binds all components to their proper position
        frame.add(panel);
        panel.setBounds(0, 0, 8 * squareSize, 8 * squareSize);
        frame.add(promotionPanel);
        promotionPanel.setBounds(8 * squareSize, 0, squareSize, 4 * squareSize);
        promotionPanel.setVisible(false);
        panel.addMouseListener(mouse);

        //Finishes frame setup
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setVisible(true);

        //Bot automatically makes the first move
        if(botPlays && botPlaysWhite) {
            makeBotMove(testBot2);
            panel.repaint();
        }
    }

    /*
    Updates Main.sboard to represent the board used for rendering and appends the last move played

    Params:
        self-explanatory
     */
    public static void updateSBoard(int originLine, int originFile, int targetLine, int targetFile) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {               //Loops through the board
            for (int j = 0; j < 8; j++) {           // ^
                if (Piece.board[i][j] == null) {
                    stringBuilder.append(0);        //Representation of empty square is '0'
                } else if (Piece.board[i][j].isWhite) {
                    //Upper case for white
                    stringBuilder.append(Piece.board[i][j].getType().toUpperCase(Locale.ROOT).charAt(0));   //Takes first char of Piece.getType as representation (Knight returns "Night")
                } else {
                    //lower case for black
                    stringBuilder.append(Piece.board[i][j].getType().toLowerCase(Locale.ROOT).charAt(0));
                }
            }
        }
        stringBuilder.append(((originLine << 9) + (originFile << 6) + (targetLine << 3) + targetFile));     //Appends the last move
        sBoard = stringBuilder.toString();
    }

    /*
    Calculates and plays the best move chosen by a bot.

    Params:
        Bot bot: The bot which decides on the move
     */
    public static void makeBotMove(Bot bot) {
        int move = bot.getBestMove(sBoard, 2);                             //Gets the best move
        int[] m = Piece.convertMoveFormat(move);                                        //Splits that move into it's components
        sBoard = Piece.move(move >> 6, move % 64, sBoard);                  //Makes the move in sboard
        Piece.updateBoard(sBoard);                                                      //Updates the rendering board
        System.out.println("lo" + m[0] + " fo" + m[1] + " lt" + m[2] + " ft" + m[3]);   //Prints the move as it's components to the console
    }
}
