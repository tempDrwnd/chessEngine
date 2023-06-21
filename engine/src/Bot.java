import java.util.HashMap;

public class Bot {
    public boolean isWhite;
    public Evaluate evaluate;

    public HashMap<Integer, String> positions = new HashMap<>();

    public Bot(double[] values, boolean isWhite){
        this.isWhite = isWhite;
        evaluate = new Evaluate(values);
    }

    public int getBestMove(String board, int remainingDepth){
        positions.clear();
        int[] moves = Piece.getAllValidMoves(isWhite, board);
        if(moves.length == 0){
            return -1;
        }

        if(isWhite) {
            int maxEvalIndex = 0;
            double maxEval = -100000d;
            for (int i = 0; i < moves.length; i++) {
                String tempBoard = Piece.move(moves[i] >> 6, moves[i] % 64, board);
                positions.put(moves[i], tempBoard);
                double eval = getMinEval(tempBoard, remainingDepth - 1, false);
                if (eval >= maxEval) {
                    maxEval = eval;
                    maxEvalIndex = i;
                }
            }
            return moves[maxEvalIndex];
        }
        int minEvalIndex = 0;
        double minEval = 100000d;
        for (int i = 0; i < moves.length; i++) {
            String tempBoard = Piece.move(moves[i] >> 6, moves[i] % 64, board);
            positions.put(moves[i], tempBoard);
            double eval = getMaxEval(tempBoard, remainingDepth - 1, true);
            if (eval <= minEval) {
                minEval = eval;
                minEvalIndex = i;
            }
        }
        return moves[minEvalIndex];
    }

    public double getMinEval(String board, int remainingDepth, boolean isWhite){
        //Recursion break
        if(remainingDepth <= 0){
            return evaluate.evaluate(Piece.toCharArray(board), Piece.convertMoveFormat(Piece.getAllValidMoves(true, board)),Piece.convertMoveFormat(Piece.getAllValidMoves(false, board)));
        }
        int[] moves = Piece.getAllValidMoves(isWhite, board);
        double minEval = 100000d;
        for (int move : moves) {
            String tempBoard = Piece.move(move >> 6, move % 64, board);
            //Makes sure of no duplicate Positions
            if (positions.containsValue(tempBoard)) {
                System.out.println("skip " + remainingDepth);
                continue;
            }
            positions.put(move, tempBoard);

            double eval = getMaxEval(tempBoard, remainingDepth - 1, !isWhite);
            if (eval <= minEval) {
                minEval = eval;
            }
        }
        return minEval;
    }

    public double getMaxEval(String board, int remainingDepth, boolean isWhite){
        //Recursion break
        if(remainingDepth <= 0){
            return evaluate.evaluate(Piece.toCharArray(board), Piece.convertMoveFormat(Piece.getAllValidMoves(true, board)),Piece.convertMoveFormat(Piece.getAllValidMoves(false, board)));
        }
        int[] moves = Piece.getAllValidMoves(isWhite, board);
        double maxEval = -100000d;
        for (int move : moves) {
            String tempBoard = Piece.move(move >> 6, move % 64, board);
            //Makes sure of no duplicate Positions
            if (positions.containsValue(tempBoard)) {
                System.out.println("skip " + remainingDepth);
                continue;
            }
            positions.put(move, tempBoard);

            double eval = getMinEval(tempBoard, remainingDepth - 1, !isWhite);
            if (eval >= maxEval) {
                maxEval = eval;
            }
        }
        return maxEval;
    }
}
