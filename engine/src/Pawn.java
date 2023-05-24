import java.util.LinkedList;

public class Pawn extends Piece {
    protected Pawn(boolean isWhite) {
        super(isWhite);
    }

    public static boolean isMoveValid(int pos, int target, boolean isWhite, String board) {
        if (board.charAt(target) != '0' && whitePiece(board.charAt(target)) == isWhite) {
            return false;
        }
        if (isWhite) {
            return isValidWhiteMove(pos, target, board);
        }
        return isValidBlackMove(pos, target, board);
    }

    public static boolean isValidWhiteMove(int pos, int target, String board) {
        if ((target >> 3) - (pos >> 3) == 1 && (pos % 8) - (target % 8) == 0 && !isSquareBlocked(target, board)) {
            return true;
        }
        if ((target >> 3) - (pos >> 3) == 2 && (pos % 8) - (target % 8) == 0 && (pos >> 3) == 1 && !isSquareBlocked(target, board) && !isSquareBlocked(pos + 8, board)) {
            return true;
        }
        boolean hasLineMovement = Math.abs((pos % 8) - (target % 8)) == 1;
        if ((target >> 3) - (pos >> 3) == 1 && hasLineMovement && isSquareBlocked(target, board)) {
            return true;
        }
        int lastMove = Integer.parseInt(board.substring(64));
        return (target >> 3) - (pos >> 3) == 1 && hasLineMovement && board.charAt(lastMove % 64) == 'p'
                && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && target % 8 == convertMoveFormat(lastMove)[3];
    }

    public static boolean isValidBlackMove(int pos, int target, String board) {
        if ((target >> 3) - (pos >> 3) == -1 && (pos % 8) - (target % 8) == 0 && !isSquareBlocked(target, board)) {
            return true;
        }
        if ((target >> 3) - (pos >> 3) == -2 && (pos % 8) - (target % 8) == 0 && (pos >> 3) == 6 && !isSquareBlocked(target, board) && !isSquareBlocked(pos - 8, board)) {
            return true;
        }
        boolean hasLineMovement = Math.abs((pos % 8) - (target % 8)) == 1;
        if ((target >> 3) - (pos >> 3) == -1 && hasLineMovement && isSquareBlocked(target, board)) {
            return true;
        }
        int lastMove = Integer.parseInt(board.substring(64));
        return (target >> 3) - (pos >> 3) == -1 && hasLineMovement && board.charAt(lastMove % 64) == 'P'
                && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && target % 8 == convertMoveFormat(lastMove)[3];
    }

    public static LinkedList<Integer> getValidMoves(int pos, boolean isWhite, String board) {
        if (isWhite) {
            return getWhiteMoves(pos, board);
        }
        return getBlackMoves(pos, board);
    }

    public static LinkedList<Integer> getWhiteMoves(int pos, String board) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        boolean bool1 = isFreeSquare(pos + 7, pos, true, board);
        boolean bool2 = isFreeSquare(pos + 9, pos, true, board);

        if (isFreeSquare(pos + 8, pos, true, board) && !isSquareBlocked(pos + 8, board)) moves.add(pos_ + pos + 8);
        if (pos >> 3 == 1 && isFreeSquare(pos + 16, pos, true, board) && !isSquareBlocked(pos + 16, board) && !isSquareBlocked(pos + 8, board))
            moves.add(pos_ + pos + 16);
        if (board.charAt(pos + 7) != '0' && isSquareBlocked(pos + 7, board) && bool1)
            moves.add(pos_ + pos + 7);
        if (board.charAt(pos + 9) != '0' && isSquareBlocked(pos + 9, board) && bool2)
            moves.add(pos_ + pos + 9);

        int lastMove = Integer.parseInt(board.substring(64));
        if (board.charAt(lastMove % 64) == 'p' && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && (pos % 8) - 1 == convertMoveFormat(lastMove)[3]
                && (pos >> 3) == convertMoveFormat(lastMove)[2] && !isSquareBlocked(pos + 7, board) && bool1) {
            moves.add(pos_ + pos + 7);
        }
        if (board.charAt(lastMove % 64) == 'p' && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && (pos % 8) + 1 == convertMoveFormat(lastMove)[3]
                && (pos >> 3) == convertMoveFormat(lastMove)[2] && !isSquareBlocked(pos + 9, board) && bool2) {
            moves.add(pos_ + pos + 9);
        }

        return moves;
    }

    public static LinkedList<Integer> getBlackMoves(int pos, String board) {
        LinkedList<Integer> moves = new LinkedList<>();
        int pos_ = pos << 6;

        boolean bool1 = isFreeSquare(pos - 7, pos, false, board);
        boolean bool2 = isFreeSquare(pos - 9, pos, false, board);

        if (isFreeSquare(pos - 8, pos, false, board) && !isSquareBlocked(pos - 8, board)) moves.add(pos_ + pos - 8);
        if (pos >> 3 == 6 && isFreeSquare(pos - 16, pos, false, board) && !isSquareBlocked(pos - 16, board) && !isSquareBlocked(pos - 8, board))
            moves.add(pos_ + pos - 16);
        if (board.charAt(pos - 7) != '0' && isSquareBlocked(pos - 7, board) && bool1)
            moves.add(pos_ + pos - 7);
        if (board.charAt(pos - 9) != '0' && isSquareBlocked(pos - 9, board) && bool2)
            moves.add(pos_ + pos - 9);

        int lastMove = Integer.parseInt(board.substring(64));
        if (board.charAt(lastMove % 64) == 'P' && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && (pos % 8) + 1 == convertMoveFormat(lastMove)[3]
                && (pos >> 3) == convertMoveFormat(lastMove)[2] && !isSquareBlocked(pos - 7, board) && bool1) {
            moves.add(pos_ + pos - 7);
        }
        if (board.charAt(lastMove % 64) == 'P' && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && (pos % 8) - 1 == convertMoveFormat(lastMove)[3]
                && (pos >> 3) == convertMoveFormat(lastMove)[2] && !isSquareBlocked(pos - 9, board) && bool2) {
            moves.add(pos_ + pos - 9);
        }
        return moves;
    }

    @Override
    public String getType() {
        return "Pawn";
    }
}
