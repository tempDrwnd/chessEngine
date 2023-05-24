import java.util.LinkedList;

public abstract class Piece {

    public static Piece[][] board = new Piece[8][8];           //Current board

    public final boolean isWhite;   //Whether a piece is white

    protected Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public abstract String getType();

    public static boolean isInCheck(boolean white, String board) {
        int kingPos = -1;
        if (white) {
            for (int i = 0; i < 64; i++) {
                if (board.charAt(i) == 'K') {
                    kingPos = i;
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                if (board.charAt(i) == 'k') {
                    kingPos = i;
                }
            }
        }
        if (kingPos == -1) {
            System.out.println("no king found");
            System.out.println(board);
        }
        for (int i = 0; i < 64; i++) {
            char piece = board.charAt(i);
            if (piece == '0') {
                continue;
            }
            if (whitePiece(piece) == white) {
                continue;
            }
            switch (piece) {
                case 'P':
                case 'p':
                    if (Pawn.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
                case 'R':
                case 'r':
                    if (Rook.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
                case 'N':
                case 'n':
                    if (Knight.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
                case 'B':
                case 'b':
                    if (Bishop.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
                case 'K':
                case 'k':
                    if (King.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
                case 'Q':
                case 'q':
                    if (Queen.isMoveValid(i, kingPos, white, board)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public static int[] getAllValidMoves(boolean isWhite, String board) {
        LinkedList<Integer> moves = new LinkedList<>();

        for (int i = 0; i < 64; i++) {
            char piece = board.charAt(i);
            if (whitePiece(piece) != isWhite) {
                if (piece == '0') {
                    continue;
                }
                switch (piece) {
                    case 'P', 'p' -> moves.addAll(Pawn.getValidMoves(i, isWhite, board));
                    case 'R', 'r' -> moves.addAll(Rook.getValidMoves(i, isWhite, board));
                    case 'N', 'n' -> moves.addAll(Knight.getValidMoves(i, isWhite, board));
                    case 'B', 'b' -> moves.addAll(Bishop.getValidMoves(i, isWhite, board));
                    case 'K', 'k' -> moves.addAll(King.getValidMoves(i, isWhite, board));
                    case 'Q', 'q' -> moves.addAll(Queen.getValidMoves(i, isWhite, board));
                }
            }
        }
        return toArray(moves);
    }

    public static int[] convertMoveFormat(int move) {
        //{ lineOrigin, fileOrigin, lineTarget, fileTarget}
        return new int[]{move >> 9, (move >> 6) % 8, (move >> 3) % 8, move % 8};
    }

    public static int[] toArray(LinkedList<Integer> moves) {
        int[] arrayMoves = new int[moves.size()];
        int i = 0;
        for (int move : moves) {
            arrayMoves[i] = move;
            i++;
        }
        return arrayMoves;
    }

    public static boolean isFreeSquare(int square, int origin, boolean isWhite, String board) {
        if (square < 0 || square > 63) {
            return false;
        }
        if (board.charAt(square) != '0' && Piece.whitePiece(board.charAt(square)) == isWhite) {
            return false;
        }
        StringBuilder s = new StringBuilder(board);

        s.setCharAt(square, s.charAt(origin));
        s.setCharAt(origin, '0');

        return !isInCheck(isWhite, s.toString());
    }

    public static boolean isSquareBlocked(int square, String board) {
        return board.charAt(square) != '0';
    }

    public static boolean whitePiece(char piece) {
        return piece == 'P' || piece == 'R' || piece == 'N' || piece == 'B' || piece == 'K' || piece == 'Q';
    }

    public static boolean containsMove(int[] moves, int move) {
        for (int i : moves) {
            if (i == move) {
                return true;
            }
        }
        return false;
    }

    public static String move(int origin, int target, String board){
        StringBuilder  s = new StringBuilder(board.substring(0, 64));
        s.setCharAt(target, s.charAt(origin));
        s.setCharAt(origin, '0');

        //En-Passant Check
        if(s.charAt(target) == 'p' || s.charAt(target) == 'P'){
            int lastMove = Integer.parseInt(board.substring(64));
            boolean hasLineMovement = Math.abs((origin % 8) - (target % 8)) == 1;
            if((target >> 3) - (origin >> 3) == 1 && hasLineMovement && board.charAt(lastMove % 64) == 'p'
                    && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && target % 8 == convertMoveFormat(lastMove)[3]){
                s.setCharAt(target - 8, '0');
            }
            if((target >> 3) - (origin >> 3) == -1 && hasLineMovement && board.charAt(lastMove % 64) == 'P'
                    && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2 && target % 8 == convertMoveFormat(lastMove)[3]){
                s.setCharAt(target + 8, '0');
            }
        }
        s.append((origin << 6) + target);
        return  s.toString();
    }

    public static void updateBoard(String board){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                char c = board.charAt(i * 8 + j);
                if(c == '0'){
                    Piece.board[i][j] = null;
                }if(c == 'p'){
                    Piece.board[i][j] = new Pawn(false);
                }if(c == 'P'){
                    Piece.board[i][j] = new Pawn(true);
                }if(c == 'r'){
                    Piece.board[i][j] = new Rook(false);
                } if(c == 'R'){
                    Piece.board[i][j] = new Rook(true);
                } if(c == 'k'){
                    Piece.board[i][j] = new King(false);
                } if(c == 'K'){
                    Piece.board[i][j] = new King(true);
                } if(c == 'q'){
                    Piece.board[i][j] = new Queen(false);
                } if(c == 'Q'){
                    Piece.board[i][j] = new Queen(true);
                } if(c == 'b'){
                    Piece.board[i][j] = new Bishop(false);
                } if(c == 'B'){
                    Piece.board[i][j] = new Bishop(true);
                } if(c == 'n'){
                    Piece.board[i][j] = new Knight(false);
                } if(c == 'N'){
                    Piece.board[i][j] = new Knight(true);
                }
            }
        }
    }

    public static void promote(Piece piece, int pos) {
        board[pos >> 3][pos % 8] = piece;  //Promotes the Pawn
        Main.promotionPanel.setVisible(false);  //Hides the PromotionPanel
        Main.promotionPanel.panel.repaint();    //Repaints the board
    }
}
