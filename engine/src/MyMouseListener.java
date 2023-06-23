import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

    private final int squareSize;
    private final MyPanel panel;

    private int selectedFile = -1, selectedLine = -1;   // -1 is default for no square selected

    public MyMouseListener(int squareSize, MyPanel panel) {
        this.squareSize = squareSize;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {    //General selection of Pieces

        if (Main.promotionPanel.isVisible()) {    //Cannot move or select Pieces when a Piece is supposed to be promoted
            return;
        }

        int temp1, temp2;
        boolean nameInProcess = true;   //Used to reduce compiler load   <-- old comment no idea what that's supposed to mean
        temp1 = e.getX() / squareSize;  //Gets clicked file
        temp2 = e.getY() / squareSize;  //Gets clicked line

        if (selectedLine == -1 && Piece.board[temp2][temp1] != null) { //If no piece is selected
            selectedFile = temp1;
            selectedLine = temp2;

            panel.setSelectedFile(selectedFile);
            panel.setSelectedLine(selectedLine);
            panel.repaint();
            return;

        } else if (selectedLine == -1 && Piece.board[temp2][temp1] == null) {    //If no piece is selected and an empty square is clicked
            return;
        }
        if (Piece.board[selectedLine][selectedFile] == null) { //Null-check
            nameInProcess = false;
        }
        if (selectedFile == temp1 && selectedLine == temp2) {   //Equals-check -> can't move a Piece to where it is
            nameInProcess = false;
            selectedFile = -1;
            selectedLine = -1;
        }
        if (nameInProcess) {
            //Don't worry about it, it's debug
//            System.out.println(Piece.containsMove(Piece.toArray(Pawn.getValidMoves((selectedLine << 3) + selectedFile, Piece.board[selectedLine][selectedFile].isWhite, Main.sBoard)), (selectedLine << 9) + (selectedFile << 6) + (temp2 << 3) + temp1));
//            System.out.println(Pawn.isMoveValid((selectedLine << 3) + selectedFile, (temp2 << 3) + temp1, Piece.board[selectedLine][selectedFile].isWhite, Main.sBoard));
//            System.out.println(Main.sBoard.charAt((selectedLine << 3) + selectedFile));
//            System.out.println(selectedLine);
//            System.out.println(selectedFile);

            //Opens promotionPanel for black if black can promote
            if(Main.sBoard.charAt((selectedLine << 3) + selectedFile) == 'p' && selectedLine == 1){
                Main.promotionPanel.promote(false, temp1);
            }
            //Opens promotionPanel for white if white can promote
            if(Main.sBoard.charAt((selectedLine << 3) + selectedFile) == 'P' && selectedLine == 6){
                Main.promotionPanel.promote(true, (temp2 << 3) + temp1);
            }
            Main.sBoard = Piece.move((selectedLine << 3) + selectedFile, (temp2 << 3) + temp1, Main.sBoard);    //Makes the actual move
            Piece.updateBoard(Main.sBoard);

            selectedFile = -1;  //Resets selection
            selectedLine = -1;  //^

            //Makes the bot move if the bot is playing, and you didn't just promote
            if(!Main.promotionPanel.isVisible() && Main.botPlays){
                if(Main.botPlaysWhite){
                Main.makeBotMove(Main.testBot2);
                }else{
                    Main.makeBotMove(Main.testBot);
                }
            }
        }

        //Repainting onto the screen
        panel.setSelectedFile(selectedFile);
        panel.setSelectedLine(selectedLine);
        panel.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /*
    May all be uncommented.
    It is recommended, that Main.botPlays == false in that occasion.
    Leaving the frame with the mouse makes a bot move form both bots.
    Not automated using task scheduling, because I don't want to.
     */
    @Override
    public void mouseExited(MouseEvent e) {
//    Main.makeBotMove(Main.testBot2);
//    Main.makeBotMove(Main.testBot);
    }
}
