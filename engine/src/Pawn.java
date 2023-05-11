public class Pawn  extends Piece{
    protected Pawn(boolean isWhite, int pos) {
        super(isWhite, pos);
    }


    public static boolean isMoveValid(int pos, int target, boolean isWhite) {
        if(whitePiece(Main.sBoard.charAt(target)) != isWhite && Main.sBoard.charAt(target) != '0'){
            return false;
        }
        return true;
    }

    @Override
    public int[] getValidMoves(int pos, boolean isWhite) {
        return new int[0];
    }

    @Override
    public String getType() {
        return "Pawn";
    }
}
