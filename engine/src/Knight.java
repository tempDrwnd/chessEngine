import java.util.LinkedList;

public class Knight  extends Piece{
    protected Knight(boolean isWhite) {
        super(isWhite);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        int tmp = Math.abs((pos >> 3) - (target >> 3));
        int tmp2 = Math.abs((pos % 8) - (target % 8));
        return tmp + tmp2 == 3 && tmp != 0 && tmp2 != 0 && (whitePiece(Main.sBoard.charAt(target)) == isWhite || Main.sBoard.charAt(target) == '0');
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        if((pos >> 3) > 1 && pos % 8 > 0 && isFreeSquare( (pos-17), pos, isWhite))      moves.add((pos_ + pos-17));
        if((pos >> 3) > 1 && pos % 8 < 7 && isFreeSquare( (pos-15), pos, isWhite))      moves.add((pos_ + pos-15));
        if((pos >> 3) > 0 && pos % 8 > 1 && isFreeSquare( (pos-10), pos, isWhite))      moves.add((pos_ + pos-10));
        if((pos >> 3) > 0 && pos % 8 < 6 && isFreeSquare( (pos-6), pos, isWhite))       moves.add((pos_ + pos-6));
        if((pos >> 3) < 7 && pos % 8 > 1 && isFreeSquare( (pos+6), pos, isWhite))       moves.add((pos_ + pos+6));
        if((pos >> 3) < 7 && pos % 8 < 6 && isFreeSquare( (pos+10), pos, isWhite))      moves.add((pos_ + pos+10));
        if((pos >> 3) < 6 && pos % 8 > 0 && isFreeSquare( (pos+15), pos, isWhite))      moves.add((pos_ + pos+15));
        if((pos >> 3) < 6 && pos % 8 < 7 && isFreeSquare( (pos+17), pos, isWhite))      moves.add((pos_ + pos+17));

        return moves;
    }

    @Override
    public String getType() {
        return "Night";
    }
}
