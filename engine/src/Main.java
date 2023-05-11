import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class Main {

    public static PromotionPanel promotionPanel = new PromotionPanel();
    public static String sBoard;

    public static void main(String[] args) {

        int squareSize = 100;
        //Default board
        Piece.board = new Piece[][]{{new Rook(true, 0), new Knight(true, 1), new Bishop(true, 2), new King(true, 3), new Queen(true, 4), new Bishop(true, 5), new Knight(true, 6), new Rook(true, 7)},
                {new Pawn(true, 7), new Pawn(true, 8), new Pawn(true, 9), new Pawn(true, 10), new Pawn(true, 11), new Pawn(true, 12), new Pawn(true, 13), new Pawn(true, 14)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pawn(false, 47), new Pawn(false, 48), new Pawn(false, 49), new Pawn(false, 50), new Pawn(false, 51), new Pawn(false, 53), new Pawn(false, 54), new Pawn(false, 55)},
                {new Rook(false, 56), new Knight(false, 57), new Bishop(false, 58), new King(false, 59), new Queen(false, 60), new Bishop(false, 61), new Knight(false, 62), new Rook(false, 63)}};

        updateSBoard();
        MyPanel panel = new MyPanel(squareSize, Piece.board);       //Creates the panel
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

    public static void updateSBoard(){
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
        sBoard = stringBuilder.toString();
    }
}
