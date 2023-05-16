import javax.swing.*;
import java.awt.*;

public class PromotionPanel extends JPanel {

    //Buttons to promote to a specific Piece
    JButton queen = new JButton();
    JButton rook = new JButton();
    JButton bishop = new JButton();
    JButton knight = new JButton();

    MyPanel panel;  //Used for repainting

    public PromotionPanel() {   //Constructor
        //Layout
        setLayout(new GridLayout(4, 1));
        add(queen);
        add(rook);
        add(bishop);
        add(knight);
    }

    public void promote(boolean white, int line, int file) {
        if (white) {    //Checks if promoting Pawn is white
            //Creates Icons for the buttons
            final ImageIcon QueenWhite = new ImageIcon("QueenWhite.png");
            final ImageIcon RookWhite = new ImageIcon("RookWhite.png");
            final ImageIcon BishopWhite = new ImageIcon("BishopWhite.png");
            final ImageIcon KnightWhite = new ImageIcon("KnightWhite.png");

            //Sets the Icons on the Buttons
            queen.setIcon(QueenWhite);
            rook.setIcon(RookWhite);
            bishop.setIcon(BishopWhite);
            knight.setIcon(KnightWhite);
        } else {    //Promoting Pawn is black
            //Creates Icons for the buttons
            final ImageIcon QueenBlack = new ImageIcon("QueenBlack.png");
            final ImageIcon RookBlack = new ImageIcon("RookBlack.png");
            final ImageIcon BishopBlack = new ImageIcon("BishopBlack.png");
            final ImageIcon KnightBlack = new ImageIcon("KnightBlack.png");

            //Sets the Icons on the Buttons
            queen.setIcon(QueenBlack);
            rook.setIcon(RookBlack);
            bishop.setIcon(BishopBlack);
            knight.setIcon(KnightBlack);
        }
        //ActionListeners so clicking the Buttons actually does something
        queen.addActionListener(e -> Piece.promote(new Queen(white),(line << 3) + file));
        rook.addActionListener(e -> Piece.promote(new Rook(white),(line << 3) + file));
        bishop.addActionListener(e -> Piece.promote(new Bishop(white),(line << 3) + file));
        knight.addActionListener(e -> Piece.promote(new Knight(white),(line << 3) + file));

        setVisible(true);   //Makes the panel visible
    }

    public void setPanel(MyPanel panel) {    //Sets panel to the panel used everywhere else
        this.panel = panel;
    }
}
