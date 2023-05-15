import java.util.LinkedList;

public abstract class Piece {

    public static Piece[][] board = new Piece[8][8];           //Current board

    public final boolean isWhite;   //Whether a piece is white
    protected Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public abstract String getType();

    public static boolean isInCheck(boolean white){
        int kingPos = -1;
        if(white){
            for(int i = 0; i < Main.sBoard.length(); i++){
                if(Main.sBoard.charAt(i) == 'K'){
                    kingPos = i;
                }
            }
        }else{
            for(int i = 0; i < Main.sBoard.length(); i++){
                if(Main.sBoard.charAt(i) == 'k'){
                    kingPos = i;
                }
            }
        }
        if(kingPos == -1){
            System.out.println("no king found");
            System.out.println(Main.sBoard);
        }
        for(int i = 0; i < Main.sBoard.length(); i++){
            char piece = Main.sBoard.charAt(i);
            if(whitePiece(piece) != white){
                if(piece == '0'){
                    continue;
                }
                switch(piece){
                    case 'P':
                    case 'p':
                        if(Pawn.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                    case 'R':
                    case 'r':
                        if(Rook.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                    case 'N':
                    case 'n':
                        if(Knight.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                    case 'B':
                    case 'b':
                        if(Bishop.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                    case 'K':
                    case 'k':
                        if(King.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                    case 'Q':
                    case 'q':
                        if(Queen.isMoveValid(i, kingPos, white)){
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

    public static int[] getAllValidMoves(boolean isWhite){
        LinkedList<Integer> moves = new LinkedList<>();

        for(int i = 0; i < Main.sBoard.length(); i++){
            char piece = Main.sBoard.charAt(i);
            if(whitePiece(piece) != isWhite){
                if(piece == '0'){
                    continue;
                }
                switch (piece) {
                    case 'P', 'p' -> moves.addAll(Pawn.getValidMoves(i, isWhite));
                    case 'R', 'r' -> moves.addAll(Rook.getValidMoves(i, isWhite));
                    case 'N', 'n' -> moves.addAll(Knight.getValidMoves(i, isWhite));
                    case 'B', 'b' -> moves.addAll(Bishop.getValidMoves(i, isWhite));
                    case 'K', 'k' -> moves.addAll(King.getValidMoves(i, isWhite));
                    case 'Q', 'q' -> moves.addAll(Queen.getValidMoves(i, isWhite));
                }
            }
        }
        return toArray(moves);
    }
    
    public static int[] toArray(LinkedList<Integer> moves){
        int[] arrayMoves = new int[moves.size()];
        int i = 0;
        for(int move : moves){
            arrayMoves[i] = move;
            i++;
        }
        return arrayMoves;
    }
    
    public static boolean isFreeSquare(int square, int origin, boolean isWhite){
        if(square < 0 || square > 63){
            return false;
        }
        if(Main.sBoard.charAt(square) != '0' && Piece.whitePiece(Main.sBoard.charAt(square)) == isWhite){
            return false;
        }
        char temp = Main.sBoard.charAt(square);
        StringBuilder s = new StringBuilder(Main.sBoard);

        s.setCharAt(square, s.charAt(origin));
        s.setCharAt(origin, '0');

        Main.sBoard = s.toString();

        if(isInCheck(isWhite)){
            s.setCharAt(origin, s.charAt(square));
            s.setCharAt(square, temp);
            Main.sBoard = s.toString();
            return false;
        }
        s.setCharAt(origin, s.charAt(square));
        s.setCharAt(square, temp);
        Main.sBoard = s.toString();
        return true;
    }

    public static boolean isSquareBlocked(int square){
        return Main.sBoard.charAt(square) != '0';
    }

    public static boolean whitePiece(char piece){
        return piece == 'P' || piece == 'R' || piece == 'N' || piece == 'B' || piece == 'K' || piece == 'Q';
    }

    public static boolean containsMove(int[] moves, int move){
        System.out.println(moves.length + " l");
        for(int i : moves){
            System.out.println(i % 64);
            if(i == move){
                return true;
            }
        }
        return false;
    }
    
    public static void promote(Piece piece, int pos) {
        board[pos >> 3][pos % 8] = piece;  //Promotes the Pawn
        Main.promotionPanel.setVisible(false);  //Hides the PromotionPanel
        Main.promotionPanel.panel.repaint();    //Repaints the board
    }
}
