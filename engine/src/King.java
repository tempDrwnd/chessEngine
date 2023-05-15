import java.util.LinkedList;

public class King extends Piece{
    protected King(boolean isWhite, int pos) {
        super(isWhite, pos);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        return Math.abs((pos >> 3) - (target >> 3)) <= 1 && Math.abs((pos % 8) - (target % 8)) <= 1 && (whitePiece(Main.sBoard.charAt(target)) == isWhite || Main.sBoard.charAt(target) == '0');
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if(isFreeSquare( (pos-9), pos, isWhite) && (pos >> 3) != 0 && pos % 8 != 0)     moves.add(pos_ + (pos-9));
        if(isFreeSquare( (pos-8), pos, isWhite) && (pos >> 3) != 0)                     moves.add(pos_ + (pos-8));
        if(isFreeSquare( (pos-7), pos, isWhite) && (pos >> 3) != 0 && pos % 8 != 7)     moves.add(pos_ + (pos-7));
        if(isFreeSquare( (pos-1), pos, isWhite) && pos % 8 != 0)                      moves.add(pos_ + (pos-1));
        if(isFreeSquare( (pos+1), pos, isWhite) && pos % 8 != 7)                      moves.add(pos_ + (pos+1));
        if(isFreeSquare( (pos+7), pos, isWhite) && (pos >> 3) != 7 && pos % 8 != 0)     moves.add(pos_ + (pos+7));
        if(isFreeSquare( (pos+8), pos, isWhite) && (pos >> 3) != 7)                     moves.add(pos_ + (pos+8));
        if(isFreeSquare( (pos+9), pos, isWhite) && (pos >> 3) != 7 && pos % 8 != 7)     moves.add(pos_ + (pos+9));

        return moves;
    }

    @Override
    public String getType() {
        return "King";
    }
}
