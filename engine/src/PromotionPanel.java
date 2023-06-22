import javax.swing.*;
import java.awt.*;

public class PromotionPanel extends JPanel {

    MyPanel panel;  //Used for repainting

    public PromotionPanel() {
    }

    public void promote(boolean white, int pos) {
        removeAll();                                  //Removes all buttons from previous promotions
        setLayout(new GridLayout(4, 1));    //Sets the Layout

        //Buttons to promote to a specific Piece
        JButton queen = new JButton();
        JButton rook = new JButton();
        JButton bishop = new JButton();
        JButton knight = new JButton();

        //Adds all the buttons to the panel
        add(queen);
        add(rook);
        add(bishop);
        add(knight);

        if (white) {    //Checks if promoting Pawn is white

            //Sets the Icons on the Buttons
            queen.setIcon( new ImageIcon("QueenWhite.png"));
            rook.setIcon(new ImageIcon("RookWhite.png"));
            bishop.setIcon(new ImageIcon("BishopWhite.png"));
            knight.setIcon(new ImageIcon("KnightWhite.png"));

        } else {    //Promoting Pawn is black

            //Sets the Icons on the Buttons
            queen.setIcon(new ImageIcon("QueenBlack.png"));
            rook.setIcon(new ImageIcon("RookBlack.png"));
            bishop.setIcon( new ImageIcon("BishopBlack.png"));
            knight.setIcon(new ImageIcon("KnightBlack.png"));
        }
        //ActionListeners so clicking the Buttons actually does something
        queen.addActionListener (e -> Piece.promote((white) ? 'Q' : 'q', pos));
        rook.addActionListener  (e -> Piece.promote((white) ? 'R' : 'r',pos));
        bishop.addActionListener(e -> Piece.promote((white) ? 'B' : 'b',pos));
        knight.addActionListener(e -> Piece.promote((white) ? 'N' : 'n',pos));

        setVisible(true);   //Makes the panel visible
    }

    public void setPanel(MyPanel panel) {    //Sets panel to the panel used everywhere else
        this.panel = panel;
    }
}
