import java.util.LinkedList;

public abstract class Piece {

    public static Piece[][] board = new Piece[8][8]; // Current board

    public final boolean isWhite; // Whether a piece is white

    protected Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /*
     * Makes all necessary checks to find out if one side is in check
     * 
     * Params:
     * boolean isWhite:true for white, false for black
     * indicates which side is checked for to be in check
     * 
     * String board: The position in which one may be in check
     * 
     * returns: Whether the specified color is in check
     */
    public static boolean isInCheck(boolean isWhite, String board) {
        int kingPos = -1; // Default for no-King-detection
        // Finds white King
        if (isWhite) {
            for (int i = 0; i < 64; i++) {
                if (board.charAt(i) == 'K') {
                    kingPos = i;
                }
            }
        } else { // Finds black King
            for (int i = 0; i < 64; i++) {
                if (board.charAt(i) == 'k') {
                    kingPos = i;
                }
            }
        }
        if (kingPos == -1) { // no-King-detection if this goes off something is really wrong
            System.out.println("no king found");
            System.out.println(board);
            return true;
        }
        for (int i = 0; i < 64; i++) { // Loops through the board
            char piece = board.charAt(i); // Gets a piece
            if (piece == '0') {
                continue; // Skips empty squares
            }
            if (whitePiece(piece) == isWhite) {
                continue; // Skip pieces of equal color
            }
            // Takes the piece, checks its type and check if it can go to kingPos
            // if yes it returns true
            switch (piece) {
                case 'P':
                case 'p':
                    if (Pawn.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
                case 'R':
                case 'r':
                    if (Rook.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
                case 'N':
                case 'n':
                    if (Knight.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
                case 'B':
                case 'b':
                    if (Bishop.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
                case 'K':
                case 'k':
                    if (King.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
                case 'Q':
                case 'q':
                    if (Queen.isMoveValid(i, kingPos, !isWhite, board)) {
                        return true;
                    }
                    break;
            }
        }
        return false; // Returns false if no piece could go to the King
    }

    /*
     * Finds and returns all valid moves by the specified side
     * 
     * Params:
     * boolean isWhite: The side which moves to find true = white, false = black
     * 
     * returns: An integer array of all valid moves
     */
    public static int[] getAllValidMoves(boolean isWhite, String board) {
        LinkedList<Integer> moves = new LinkedList<>(); // List to store the moves

        for (int i = 0; i < 64; i++) { // Loops through the board
            char piece = board.charAt(i); // Gets the piece
            if (piece == '0') {
                continue; // Skips empty squares
            }
            if (whitePiece(piece) != isWhite) {
                continue; // Skip pieces of opposite color
            }
            // Adds the moves to the list
            switch (piece) {
                /*
                 * case 'P', 'p' -> moves.addAll(Pawn.getValidMoves(i, isWhite, board));
                 * case 'R', 'r' -> moves.addAll(Rook.getValidMoves(i, isWhite, board));
                 * case 'N', 'n' -> moves.addAll(Knight.getValidMoves(i, isWhite, board));
                 * case 'B', 'b' -> moves.addAll(Bishop.getValidMoves(i, isWhite, board));
                 * case 'K', 'k' -> moves.addAll(King.getValidMoves(i, isWhite, board));
                 * case 'Q', 'q' -> moves.addAll(Queen.getValidMoves(i, isWhite, board));
                 */
                case 'P':
                case 'p':
                    moves.addAll(Pawn.getValidMoves(i, isWhite, board));
                    break;
                case 'R':
                case 'r':
                    moves.addAll(Rook.getValidMoves(i, isWhite, board));
                    break;
                case 'N':
                case 'n':
                    moves.addAll(Knight.getValidMoves(i, isWhite, board));
                    break;
                case 'B':
                case 'b':
                    moves.addAll(Bishop.getValidMoves(i, isWhite, board));
                    break;
                case 'K':
                case 'k':
                    moves.addAll(King.getValidMoves(i, isWhite, board));
                    break;
                case 'Q':
                case 'q':
                    moves.addAll(Queen.getValidMoves(i, isWhite, board));
                    break;
            }
        }
        return toArray(moves); // Returns the list as an array
    }

    /*
     * Converts the move format from a single int to its components.
     * 
     * Params:
     * int move: The move to reformat
     * 
     * Returns: The reformatted move as an array
     */
    public static int[] convertMoveFormat(int move) {
        // { lineOrigin, fileOrigin, lineTarget, fileTarget}
        return new int[] { move >> 9, (move >> 6) % 8, (move >> 3) % 8, move % 8 };
    }

    /*
     * Converts an array of moves into the components-format
     * 
     * Params:
     * int[] moves: The array of moves to reformat
     * 
     * Returns: The reformatted moves as a two-dimensional array
     * First index is the move, second index the component of the move
     */
    public static int[][] convertMoveFormat(int[] moves) {
        int[][] result = new int[moves.length][4];
        for (int i = 0; i < moves.length; i++) {
            result[i] = convertMoveFormat(moves[i]);
        }
        return result;
    }

    /*
     * Converts a LinkedList of Integers into an array the same size
     * 
     * Params:
     * LinkedList<Integer> moves: The List to be converted
     * 
     * Returns: An array of ints in the single int move format
     */
    public static int[] toArray(LinkedList<Integer> moves) {
        int[] arrayMoves = new int[moves.size()];
        int i = 0;
        for (int move : moves) {
            arrayMoves[i] = move;
            i++;
        }
        return arrayMoves;
    }

    /*
     * Checks if a piece of a specified color could go to a square
     * 
     * Params:
     * int square: The index of the square to go to
     * int origin: The index of the square the piece came form
     * boolean isWhite: Whether the piece is white or not
     * String board: The board in which to check in
     * 
     * Returns: Whether one can move a that piece to that square legally. true if
     * yes, false if no
     */
    public static boolean isFreeSquare(int square, int origin, boolean isWhite, String board) {
        if (square < 0 || square > 63) {
            return false; // Returns false if the square is outside the board
        }
        if (board.charAt(square) != '0' && Piece.whitePiece(board.charAt(square)) == isWhite) {
            return false; // Returns false if the square is occupied by a piece of the same color
        }

        return !isInCheck(isWhite, Piece.move(origin, square, board)); // Returns true if one wouldn't be in check after
                                                                       // making that move
    }

    /*
     * Checks if a square is occupied by a piece
     * 
     * Params:
     * int square: The index of the square in question
     * String board: The board in which to check in
     * 
     * Returns: Whether a square is blocked by a piece
     */
    public static boolean isSquareBlocked(int square, String board) {
        if (square < 0 || square > 63) {
            return true; // Returns true if the square is outside the board
        }
        return board.charAt(square) != '0'; // Returns false if the square is empty
    }

    /*
     * Checks whether a piece belongs to white or to black.
     * Empty square is classified as black, so that always needs to be checked
     * manually
     * 
     * Params:
     * char piece: The piece in question
     * 
     * Returns: Whether that piece is white
     */
    public static boolean whitePiece(char piece) {
        return piece == 'P' || piece == 'R' || piece == 'N' || piece == 'B' || piece == 'K' || piece == 'Q';
    }

    // Debug method, please ignore
    public static boolean containsMove(int[] moves, int move) {
        for (int i : moves) {
            if (i == move) {
                return true;
            }
        }
        return false;
    }

    /*
     * Makes a specified move in the specified board and appends that move to the
     * following position
     * 
     * Params:
     * int origin: The index of the origin of the move
     * itn target: The index of the target of the move
     * Sting board: The board in which to make the move
     * 
     * Returns: The board after the move was made
     */
    public static String move(int origin, int target, String board) {
        StringBuilder s = new StringBuilder(board.substring(0, 64)); // String builder initialized with only the
                                                                     // position, not the last move
        s.setCharAt(target, s.charAt(origin)); // Places the piece at the target location
        s.setCharAt(origin, '0'); // Clears the origin

        // En-Passant Check
        if (s.charAt(target) == 'p' || s.charAt(target) == 'P') { // Is a pawn move
            int lastMove = Integer.parseInt(board.substring(64)); // Gets the last move from the board
            boolean hasLineMovement = Math.abs((origin % 8) - (target % 8)) == 1; // Makes sure the pawn moved
                                                                                  // horizontally only a single square
            // Makes sure the pawn moved forward only a single square && last move was a
            // pawn
            if ((target >> 3) - (origin >> 3) == 1 && hasLineMovement && board.charAt(lastMove % 64) == 'p'
            // Last move was two square forward && you move to the same file as the pawn
            // from the last move
                    && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2
                    && target % 8 == convertMoveFormat(lastMove)[3]
                    // you move behind the pawn
                    && (target >> 3) - convertMoveFormat(lastMove)[2] == 1) {
                s.setCharAt(target - 8, '0'); // deletes the taken Pawn
            }
            // Makes sure the pawn moved forward only a single square && last move was a
            // pawn
            if ((target >> 3) - (origin >> 3) == -1 && hasLineMovement && board.charAt(lastMove % 64) == 'P'
            // Last move was two square forward && you move to the same file as the pawn
            // from the last move
                    && Math.abs(convertMoveFormat(lastMove)[0] - convertMoveFormat(lastMove)[2]) == 2
                    && target % 8 == convertMoveFormat(lastMove)[3]
                    // you move behind the pawn
                    && (target >> 3) - convertMoveFormat(lastMove)[2] == -1) {
                s.setCharAt(target + 8, '0'); // deletes the taken Pawn
            }
        }

        // Auto promotes to queen for black
        if (s.charAt(target) == 'p' && target >> 3 == 0) {
            s.setCharAt(target, 'q');
        }
        // Auto promotes to queen for white
        if (s.charAt(target) == 'P' && target >> 3 == 7) {
            s.setCharAt(target, 'Q');
        }
        s.append((origin << 6) + target); // Appends the position with the move just played
        return s.toString();
    }

    /*
     * Updates the board used for rendering to the specified board
     * 
     * Params:
     * String board: The board to be updated to
     */
    public static void updateBoard(String board) {
        for (int i = 0; i < 8; i++) { // Loops through the rendering board
            for (int j = 0; j < 8; j++) { // ^
                char c = board.charAt(i * 8 + j); // Gets the piece at the equivalent location
                if (c == '0') {
                    Piece.board[i][j] = null;
                    continue;
                }
                if (c == 'p') {
                    Piece.board[i][j] = new Pawn(false);
                    continue;
                }
                if (c == 'P') {
                    Piece.board[i][j] = new Pawn(true);
                    continue;
                }
                if (c == 'r') {
                    Piece.board[i][j] = new Rook(false);
                    continue;
                }
                if (c == 'R') {
                    Piece.board[i][j] = new Rook(true);
                    continue;
                }
                if (c == 'k') {
                    Piece.board[i][j] = new King(false);
                    continue;
                }
                if (c == 'K') {
                    Piece.board[i][j] = new King(true);
                    continue;
                }
                if (c == 'q') {
                    Piece.board[i][j] = new Queen(false);
                    continue;
                }
                if (c == 'Q') {
                    Piece.board[i][j] = new Queen(true);
                    continue;
                }
                if (c == 'b') {
                    Piece.board[i][j] = new Bishop(false);
                    continue;
                }
                if (c == 'B') {
                    Piece.board[i][j] = new Bishop(true);
                    continue;
                }
                if (c == 'n') {
                    Piece.board[i][j] = new Knight(false);
                    continue;
                }
                if (c == 'N') {
                    Piece.board[i][j] = new Knight(true);
                }
            }
        }
    }

    /*
     * Converts a String board into an equivalent chat[][].
     * Ignores the last move appendix
     * 
     * Params:
     * String board: The board to be converted
     * 
     * Returns: An equivalent two-dimensional char array
     */
    public static char[][] toCharArray(String board) {
        char[][] result = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result[i][j] = board.charAt(i * 8 + j);
            }
        }
        return result;
    }

    /*
     * Promotes a pawn into a piece at the given index
     * 
     * Params:
     * char piece: The piece which to promote to
     * int pos: The index of the square, where you promote
     */
    public static void promote(char piece, int pos) {
        StringBuilder s = new StringBuilder(Main.sBoard);
        s.setCharAt(pos, piece); // Promotes the Pawn
        Main.sBoard = s.toString();
        updateBoard(Main.sBoard);
        Main.promotionPanel.setVisible(false); // Hides the PromotionPanel

        if (Main.botPlays) {        // Makes a bot move if you play against a bot
            if(Main.botPlaysWhite){
                Main.makeBotMove(Main.testBot2);
                }else{
                    Main.makeBotMove(Main.testBot);
                }
        }
        Main.promotionPanel.panel.repaint(); // Repaints the board
    }

    public abstract String getType();
}
