import java.util.LinkedList;

public class Knight  extends Piece{
    protected Knight(boolean isWhite, int pos) {
        super(isWhite, pos);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        int tmp = Math.abs((pos >> 3) - (target >> 3));
        int tmp2 = Math.abs((pos % 8) - (target % 8));
        return tmp + tmp2 == 3 && tmp != 0 && tmp2 != 0 && (whitePiece(Main.sBoard.charAt(target)) == isWhite || Main.sBoard.charAt(target) == '0');
    }

    @Override
    public int[] getValidMoves(int pos, boolean isWhite) {
        LinkedList<Integer> moves = new LinkedList<>();

        if(isFreeSquare( (pos-17), pos, isWhite) && (pos >> 3) > 1 && pos % 8 > 0)      moves.add( (pos-17));
        if(isFreeSquare( (pos-15), pos, isWhite) && (pos >> 3) > 1 && pos % 8 < 7)      moves.add( (pos-15));
        if(isFreeSquare( (pos-10), pos, isWhite) && (pos >> 3) > 0 && pos % 8 > 1)      moves.add( (pos-10));
        if(isFreeSquare( (pos-6), pos, isWhite)  && (pos >> 3) > 0 && pos % 8 < 6)      moves.add( (pos-6));
        if(isFreeSquare( (pos+6), pos, isWhite)  && (pos >> 3) < 7 && pos % 8 > 1)      moves.add( (pos+6));
        if(isFreeSquare( (pos+10), pos, isWhite) && (pos >> 3) < 7 && pos % 8 < 6)      moves.add( (pos+10));
        if(isFreeSquare( (pos+15), pos, isWhite) && (pos >> 3) < 6 && pos % 8 > 0)      moves.add( (pos+15));
        if(isFreeSquare( (pos+17), pos, isWhite) && (pos >> 3) < 6 && pos % 8 < 7)      moves.add( (pos+17));

        return Piece.toArray(moves);
    }

    @Override
    public String getType() {
        return "Night";
    }
}
