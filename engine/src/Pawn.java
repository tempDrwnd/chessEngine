import java.util.LinkedList;

public class Pawn  extends Piece{
    protected Pawn(boolean isWhite) {
        super(isWhite);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite, String board) {
        if(whitePiece(board.charAt(target)) != isWhite && board.charAt(target) != '0'){
            return false;
        }
        return false;
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite, String board) {
        if(isWhite){
            return getWhiteMoves(pos, board);
        }
        return getBlackMoves(pos, board);
    }

    public static LinkedList<Integer> getWhiteMoves(int pos, String board){
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if(isFreeSquare(pos + 8 , pos, true, board)) moves.add(pos_ + pos + 8);
        if(pos >> 3 == 1 && isFreeSquare(pos + 16, pos, true, board)) moves.add(pos_ + pos + 16);
        if(board.charAt(pos + 7) != '0' && isSquareBlocked(pos + 7, board) && isFreeSquare(pos + 7, pos, true, board)) moves.add(pos_ + pos + 7);
        if(board.charAt(pos + 9) != '0' && isSquareBlocked(pos + 9, board) && isFreeSquare(pos + 9, pos, true, board)) moves.add(pos_ + pos + 9);

        //TODO enPassant

        return moves;
    }

    public static LinkedList<Integer> getBlackMoves(int pos, String board){
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if(isFreeSquare(pos - 8 , pos, false, board)) moves.add(pos_ + pos - 8);
        if(pos >> 3 == 6 && isFreeSquare(pos - 16, pos, false, board)) moves.add(pos_ + pos - 16);
        if(board.charAt(pos - 7) != '0' && isSquareBlocked(pos - 7, board) && isFreeSquare(pos - 7, pos, true, board)) moves.add(pos_ + pos - 7);
        if(board.charAt(pos - 9) != '0' && isSquareBlocked(pos - 9, board) && isFreeSquare(pos - 9, pos, true, board)) moves.add(pos_ + pos - 9);

        //TODO enPassant

        return moves;
    }

    @Override
    public String getType() {
        return "Pawn";
    }
}
