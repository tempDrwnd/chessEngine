import java.util.LinkedList;

public class King extends Piece{
    protected King(boolean isWhite) {
        super(isWhite);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite, String board) {
        return Math.abs((pos >> 3) - (target >> 3)) <= 1 && Math.abs((pos % 8) - (target % 8)) <= 1 && (whitePiece(board.charAt(target)) != isWhite || board.charAt(target) == '0');
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite, String board) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if((pos >> 3) != 0 && pos % 8 != 0 && isFreeSquare( (pos-9), pos, isWhite, board))     moves.add(pos_ + (pos-9));
        if((pos >> 3) != 0 && isFreeSquare( (pos-8), pos, isWhite, board))                     moves.add(pos_ + (pos-8));
        if((pos >> 3) != 0 && pos % 8 != 7 && isFreeSquare( (pos-7), pos, isWhite, board))     moves.add(pos_ + (pos-7));
        if(pos % 8 != 0 && isFreeSquare( (pos-1), pos, isWhite, board))                        moves.add(pos_ + (pos-1));
        if(pos % 8 != 7 && isFreeSquare( (pos+1), pos, isWhite, board))                        moves.add(pos_ + (pos+1));
        if((pos >> 3) != 7 && pos % 8 != 0 && isFreeSquare( (pos+7), pos, isWhite, board))     moves.add(pos_ + (pos+7));
        if((pos >> 3) != 7 && isFreeSquare( (pos+8), pos, isWhite, board))                     moves.add(pos_ + (pos+8));
        if((pos >> 3) != 7 && pos % 8 != 7 && isFreeSquare( (pos+9), pos, isWhite, board))     moves.add(pos_ + (pos+9));

        return moves;
    }

    @Override
    public String getType() {
        return "King";
    }
}
