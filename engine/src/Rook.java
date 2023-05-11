import java.util.LinkedList;

public class Rook  extends Piece{
    protected Rook(boolean isWhite, int pos) {
        super(isWhite, pos);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        if(whitePiece(Main.sBoard.charAt(target)) != isWhite && Main.sBoard.charAt(target) != '0'){
            return false;
        }
        if ((pos >> 3) - (target >> 3) != 0 && (pos % 8) - (target % 8) != 0) {             //Checks whether the destination is on the same line or file
            return false;
        }
        if ((pos >> 3) - (target >> 3) == 0) {                                      //Moves left and right
            for (int i = 1, l = Math.abs((pos % 8) - (target % 8))-1; i <= l; i++) {//Loops through all squares in between
                int start = Math.min(pos, target);
                if(Main.sBoard.charAt(start + i) != '0'){
                    return false;
                }
            }
        } else {                                                                    //Moves up and down
            for (int i = 1, l = Math.abs((pos >> 3) - (target >> 3))-1; i <= l; i++) {//Loops through all squares in between
                int start = Math.min(pos, target);
                if(Main.sBoard.charAt(start + 8 * i) != '0'){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[] getValidMoves(int pos, boolean isWhite) {
        LinkedList<Integer> moves = new LinkedList<>();
        int tmp = 1;
        while((pos >> 3) - tmp >= 0){
            if(isFreeSquare(pos - 8 * tmp, pos, isWhite)) moves.add(pos - 8 * tmp);
            if(isSquareBlocked(pos - 8 * tmp)) break;
            tmp++;
        }
        tmp = 1;
        while((pos >> 3) + tmp < 8){
            if(isFreeSquare(pos + 8 * tmp, pos, isWhite)) moves.add(pos + 8 * tmp);
            if(isSquareBlocked(pos + 8 * tmp)) break;
            tmp++;
        }
        tmp = 1;
        while(pos % 8 - tmp >= 0){
            if(isFreeSquare(pos - tmp, pos, isWhite)) moves.add(pos - tmp);
            if(isSquareBlocked(pos - tmp)) break;
            tmp++;
        }
        tmp = 1;
        while(pos % 8 - tmp < 8){
            if(isFreeSquare(pos + tmp, pos, isWhite)) moves.add(pos + tmp);
            if(isSquareBlocked(pos + tmp)) break;
            tmp++;
        }

        return Piece.toArray(moves);
    }

    @Override
    public String getType() {
        return "Rook";
    }
}
