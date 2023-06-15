import java.util.HashMap;

public class Bot {
    public double[] values;
    public boolean isWhite;
    public Evaluate evaluate;

    public HashMap<Integer, String> positions = new HashMap<>();

    public Bot(double[] values, boolean isWhite){
        this.values = values;
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
                double eval = getMinEval(Piece.move(moves[i] >> 6, moves[i] % 64, board), remainingDepth - 1, false);
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
            double eval = getMaxEval(Piece.move(moves[i] >> 6, moves[i] % 64, board), remainingDepth - 1, true);
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
                System.out.println("skip");
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
                System.out.println("skip");
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
    public double evaluate(char[][] array, int[][] whiteMoves, int[][] blackMoves){
        int material = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                switch (array[i][j]) {
                    case 'P' -> material += 1;
                    case 'p' -> material -= 1;
                    case 'R' -> material += 5;
                    case 'r' -> material -= 5;
                    case 'N', 'B' -> material += 3;
                    case 'n', 'b' -> material -= 3;
                    case 'K' -> material += 1000000000;
                    case 'k' -> material -= 1000000000;
                    case 'Q' -> material += 9;
                    case 'q' -> material -= 9;
                }
            }
        }
        return material;
    }

}
