import java.util.LinkedList;

public class Queen  extends Piece{
    protected Queen(boolean isWhite) {
        super(isWhite);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite, String board) {
        if(whitePiece(board.charAt(target)) != isWhite && board.charAt(target) != '0'){
            return false;
        }
        return validRookMove(pos, target, board) || validBishopMove(pos, target, board);
    }

    private static boolean validRookMove(int pos, int target, String board) {       //Checks if a Rook could move to the destination
        if ((pos >> 3) - (target >> 3) != 0 && (pos % 8) - (target % 8) != 0) {     //Checks whether the destination is on the same line or file
            return false;
        }
        if ((pos >> 3) - (target >> 3) == 0) {                                      //Moves left and right
            for (int i = 1, l = Math.abs((pos % 8) - (target % 8))-1; i <= l; i++) {//Loops through all squares in between
                int start = Math.min(pos, target);
                if(board.charAt(start + i) != '0'){
                    return false;
                }
            }
        } else {                                                                      //Moves up and down
            for (int i = 1, l = Math.abs((pos >> 3) - (target >> 3))-1; i <= l; i++) {//Loops through all squares in between
                int start = Math.min(pos, target);
                if(board.charAt(start + 8 * i) != '0'){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean validBishopMove(int pos, int target, String board) { //Checks if a Bishop could move to the destination
        int abs = Math.abs((pos % 8) - (target % 8));
        int abs1 = Math.abs((pos >> 3) - (target >> 3));
        if (abs1 != abs) {                                                      //Checks if the destination is on a diagonal
            return false;
        }
        int lineMovement = ((target >> 3) - (pos >> 3)) / abs1;                 //1 or -1 for left and right movement
        int fileMovement = ((target % 8) - (pos % 8)) / abs;                    //1 or -1 for up and down movement
        for (int i = 1; i < abs1; i++) {                                        //Loops through all squares in between
            if(board.charAt(pos + 8 * lineMovement  * i+ fileMovement * i) != '0'){
                return false;
            }
        }
        return true;
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite, String board) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;
        int tmp = 1;
        while((pos >> 3) - tmp >= 0){
            if(isFreeSquare(pos - 8 * tmp, pos, isWhite, board)) moves.add(pos_ + pos - 8 * tmp);
            if(isSquareBlocked(pos - 8 * tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while(((pos >> 3)) + tmp < 8){
            if(isFreeSquare(pos + 8 * tmp, pos, isWhite, board)) moves.add(pos_ + pos + 8 * tmp);
            if(isSquareBlocked(pos + 8 * tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while(pos % 8 - tmp >= 0){
            if(isFreeSquare(pos - tmp, pos, isWhite, board)) moves.add(pos_ + pos - tmp);
            if(isSquareBlocked(pos - tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while(pos % 8 - tmp < 8){
            if(isFreeSquare(pos + tmp, pos, isWhite, board)) moves.add(pos_ + pos + tmp);
            if(isSquareBlocked(pos + tmp, board)) break;
            tmp++;
        }

        tmp = 1;
        while((pos >> 3) - tmp >= 0 && pos % 8 - tmp >= 0){
            if(isFreeSquare(pos - 9 * tmp, pos, isWhite, board)) moves.add(pos_ + pos - 9 * tmp);
            if(isSquareBlocked(pos - 9 * tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while((pos >> 3) - tmp >= 0 && pos % 8 + tmp < 8){
            if(isFreeSquare(pos - 7 * tmp, pos, isWhite, board)) moves.add(pos_ + pos - 7 * tmp);
            if(isSquareBlocked(pos - 7 * tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while((pos >> 3) + tmp < 8 && pos % 8 - tmp >= 0){
            if(isFreeSquare(pos + 7 * tmp, pos, isWhite, board)) moves.add(pos_ + pos + 7 * tmp);
            if(isSquareBlocked(pos + 7 * tmp, board)) break;
            tmp++;
        }
        tmp = 1;
        while((pos >> 3) + tmp < 8 && pos % 8 + tmp < 8){
            if(isFreeSquare(pos + 9 * tmp, pos, isWhite, board)) moves.add(pos_ + pos + 9 * tmp);
            if(isSquareBlocked(pos + 9 * tmp, board)) break;
            tmp++;
        }

        return moves;
    }

    @Override
    public String getType() {
        return "Queen";
    }
}
