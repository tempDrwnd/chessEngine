import java.util.LinkedList;

public class Pawn  extends Piece{
    protected Pawn(boolean isWhite) {
        super(isWhite);
    }
    
    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        if(whitePiece(Main.sBoard.charAt(target)) != isWhite && Main.sBoard.charAt(target) != '0'){
            return false;
        }
        return false;
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite) {
        if(isWhite){
            return getWhiteMoves(pos);
        }
        return getBlackMoves(pos);
    }

    public static LinkedList<Integer> getWhiteMoves(int pos){
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if(isFreeSquare(pos + 8 , pos, true)) moves.add(pos_ + pos + 8);
        if(pos >> 3 == 1 && isFreeSquare(pos + 16, pos, true)) moves.add(pos_ + pos + 16);
        if(Main.sBoard.charAt(pos + 7) != '0' && isSquareBlocked(pos + 7) && isFreeSquare(pos + 7, pos, true)) moves.add(pos_ + pos + 7);
        if(Main.sBoard.charAt(pos + 9) != '0' && isSquareBlocked(pos + 9) && isFreeSquare(pos + 9, pos, true)) moves.add(pos_ + pos + 9);

        //TODO enPassant

        return moves;
    }

    public static LinkedList<Integer> getBlackMoves(int pos){
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if(isFreeSquare(pos - 8 , pos, false)) moves.add(pos_ + pos - 8);
        if(pos >> 3 == 6 && isFreeSquare(pos - 16, pos, false)) moves.add(pos_ + pos - 16);
        if(Main.sBoard.charAt(pos - 7) != '0' && isSquareBlocked(pos - 7) && isFreeSquare(pos - 7, pos, true)) moves.add(pos_ + pos - 7);
        if(Main.sBoard.charAt(pos - 9) != '0' && isSquareBlocked(pos - 9) && isFreeSquare(pos - 9, pos, true)) moves.add(pos_ + pos - 9);

        //TODO enPassant

        return moves;
    }

    @Override
    public String getType() {
        return "Pawn";
    }
}
