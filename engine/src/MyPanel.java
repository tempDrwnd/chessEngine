import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private final int squareSize;
    private final Piece[][] board;

    private int selectedFile = -1, selectedLine = -1; // -1 is default for no square selected

    private final ImageIcon boardImage = new ImageIcon("Chess_Board.png");  //Image of the chessBoard

    //Images of every Piece in every color
    private final ImageIcon KingWhite = new ImageIcon("KingWhite.png");
    private final ImageIcon KingBlack = new ImageIcon("KingBlack.png");
    private final ImageIcon QueenWhite = new ImageIcon("QueenWhite.png");
    private final ImageIcon QueenBlack = new ImageIcon("QueenBlack.png");
    private final ImageIcon RookWhite = new ImageIcon("RookWhite.png");
    private final ImageIcon RookBlack = new ImageIcon("RookBlack.png");
    private final ImageIcon BishopWhite = new ImageIcon("BishopWhite.png");
    private final ImageIcon BishopBlack = new ImageIcon("BishopBlack.png");
    private final ImageIcon KnightWhite = new ImageIcon("KnightWhite.png");
    private final ImageIcon KnightBlack = new ImageIcon("KnightBlack.png");
    private final ImageIcon PawnWhite = new ImageIcon("PawnWhite.png");
    private final ImageIcon PawnBlack = new ImageIcon("PawnBlack.png");

    public MyPanel(int squareSize, Piece[][] board) {   //Constructor
        this.squareSize = squareSize;
        this.board = board;

    }

    public void paint(Graphics g) { //Paints the entire board

        Graphics2D g2D = (Graphics2D) g;
        int a10th_square = squareSize / 10;
        int eight10th_square = (int) (squareSize * 0.8);
        g2D.drawImage(boardImage.getImage(), 0, 0, 8 * squareSize, 8 * squareSize, null);   //Board in the background

        g2D.setPaint(Color.RED);
        g2D.fillRect(selectedFile * squareSize, selectedLine * squareSize, squareSize, squareSize);             //Red square for selected square

        for (int i = 0, l = board.length; i < l; i++) {     //Loops through the board
            for (int j = 0; j < l; j++) {                   //^
                if (board[i][j] == null) {
                    continue;   //Skips empty squares
                }
                String type = board[i][j].getType();        //Gets the piece type so we know what to render
                switch (type) {
                    case "King":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(KingWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(KingBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                    case "Queen":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(QueenWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(QueenBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                    case "Rook":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(RookWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(RookBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                    case "Bishop":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(BishopWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(BishopBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                    case "Night":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(KnightWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(KnightBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                    case "Pawn":
                        if (board[i][j].isWhite) {
                            g2D.drawImage(PawnWhite.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        } else {
                            g2D.drawImage(PawnBlack.getImage(), j * squareSize + a10th_square, i * squareSize + a10th_square, eight10th_square, eight10th_square, null);
                        }
                        break;
                }
            }
        }
    }

    public void setSelectedFile(int selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setSelectedLine(int selectedLine) {
        this.selectedLine = selectedLine;
    }
}
