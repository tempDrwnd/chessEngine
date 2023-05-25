import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class Main {

    public static Bot testBot = new Bot(new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1}, false);

    public static PromotionPanel promotionPanel = new PromotionPanel();
    public static String sBoard;

    public static void main(String[] args) {

        int squareSize = 100;
        //Default board
        Piece.board = new Piece[][]{{new Rook(true), new Knight(true), new Bishop(true), new King(true), new Queen(true), new Bishop(true), new Knight(true), new Rook(true)},
                {new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)},
                {new Rook(false), new Knight(false), new Bishop(false), new King(false), new Queen(false), new Bishop(false), new Knight(false), new Rook(false)}};

        updateSBoard(0,0,0,0);
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
    }

    public static void updateSBoard(int originLine, int originFile, int targetLine, int targetFile){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(Piece.board[i][j] == null){
                    stringBuilder.append(0);
                }else if(Piece.board[i][j].isWhite){
                    stringBuilder.append(Piece.board[i][j].getType().toUpperCase(Locale.ROOT).charAt(0));
                }else{
                    stringBuilder.append(Piece.board[i][j].getType().toLowerCase(Locale.ROOT).charAt(0));
                }
            }
        }
        stringBuilder.append(((originLine << 9) + (originFile << 6) + (targetLine << 3) + targetFile));
        sBoard = stringBuilder.toString();
    }
}
