public class Evaluate {

    char[][] board = new char[8][8];
    double E = 0.0;
    boolean endgame = false;

    int value_White;
    int value_Black;
    int whiteActivity;
    int blackActivity;
    int whiteArea;
    int blackArea;

    int value_King = 1000;
    int value_Queen = 10;
    int value_Rook = 5;
    int value_minorPiece = 3;
    int value_Pawn = 1;

    double value_passedPawn;
    double value_isolatedPawn;
    double value_stackedPawn;
    double value_centralPawn ;
    double value_advancedPawn;
    double value_centralKnight;
    double value_centralRook;
    double value_seventhRankRook;
    double value_pawnChain;
    double value_activity;
    double value_area;
    double value_kingSafety;
    double value_kingLead;
    double value_kingOpposition;

    public Evaluate(
            double value_passedPawn,
            double value_isolatedPawn,
            double value_stackedPawn,
            double value_centralPawn,
            double value_advancedPawn,
            double value_centralKnight,
            double value_centralRook,
            double value_seventhRankRook,
            double value_pawnChain,
            double value_activity,
            double value_area,
            double value_kingSafety,
            double value_kingLead,
            double value_kingOpposition
    )
    {
        this.value_passedPawn = value_passedPawn;
        this.value_isolatedPawn = value_isolatedPawn;
        this.value_stackedPawn = value_stackedPawn;
        this.value_centralPawn = value_centralPawn;
        this.value_advancedPawn = value_advancedPawn;
        this.value_centralKnight = value_centralKnight;
        this.value_centralRook = value_centralRook;
        this.value_seventhRankRook = value_seventhRankRook;
        this.value_pawnChain = value_pawnChain;
        this.value_activity = value_activity;
        this.value_area = value_area;
        this.value_kingSafety = value_kingSafety;
        this.value_kingLead = value_kingLead;
        this.value_kingOpposition = value_kingOpposition;
    }

    public Evaluate(double[] values){
        this.value_passedPawn = values[0];
        this.value_isolatedPawn = values[1];
        this.value_stackedPawn = values[2];
        this.value_centralPawn = values[3];
        this.value_advancedPawn = values[4];
        this.value_centralKnight = values[5];
        this.value_centralRook = values[6];
        this.value_seventhRankRook = values[7];
        this.value_pawnChain = values[8];
        this.value_activity = values[9];
        this.value_area = values[10];
        this.value_kingSafety = values[11];
        this.value_kingLead = values[12];
        this.value_kingOpposition = values[13];
    }


    public double evaluate(char[][] array, int[][] whiteMoves, int[][] blackMoves){
        this.board = array;

        //Resets the evaluation values, so you can call this method multiple times per instance
        //That's why the evaluation was off the charts
        value_White = 0;
        value_Black = 0;
        whiteActivity = 0;
        blackActivity = 0;
        whiteArea = 0;
        blackArea = 0;
        E = 0;

        for (int y = 0; y<8; y++) {
            for (int x = 0; x<8; x++) {
                if (Character.toLowerCase(board[y][x]) == 'p'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += value_Pawn;
                    } else {
                        value_White += value_Pawn;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'n' || Character.toLowerCase(board[y][x]) == 'b'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += value_minorPiece;
                    } else {
                        value_White += value_minorPiece;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'r'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += value_Rook;
                    } else {
                        value_White += value_Rook;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'q'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += value_Queen;
                    } else {
                        value_White += value_Queen;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'k'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += value_King;
                        if (y != 0 && board[y-1][x] == 'p'){
                            E -= (1/3.0) * value_kingSafety;
                        }
                        if (y != 0 && x != 0 && board[y-1][x-1] == 'p'){
                            E -= (1/3.0) * value_kingSafety;
                        }
                        if (y != 0 && x != 7 && board[y-1][x+1] == 'p'){
                            E -= (1/3.0) * value_kingSafety;
                        }
                    } else {
                        value_White += value_King;
                        if (y != 7 && board[y+1][x] == 'P'){
                            E += (1/3.0) * value_kingSafety;
                        }
                        if (y != 7 && x != 0 && board[y+1][x-1] == 'P'){
                            E += (1/3.0) * value_kingSafety;
                        }
                        if (y != 7 && x != 7 && board[y+1][x+1] == 'P'){
                            E += (1/3.0) * value_kingSafety;
                        }
                    }
                }

            }
        }

        E += value_White;
        E -= value_Black;

        if ((value_White + value_Black)/2.0 <= 1016) {
            endgame = true;
        }

        String wm = new String("");
        String subwm = new String("");
        String bm = new String("");
        String subbm = new String("");

        whiteActivity = whiteMoves.length;
        blackActivity = blackMoves.length;
        for (int w = 0; w<whiteMoves.length; w++){
            subwm = "<" + whiteMoves[w][2] + "" + whiteMoves[w][2] + ">";
            if (!wm.contains(subwm)){
                wm += subwm;
                whiteArea++;
            }
        }
        for (int b = 0; b<blackMoves.length; b++){
            subbm = "<" + blackMoves[b][2] + "" + blackMoves[b][2] + ">";
            if (!bm.contains(subbm)){
                bm += subbm;
                blackArea++;
            }
        }

        E += whiteActivity * value_activity;
        E -= blackActivity * value_activity;
        E += whiteArea * value_area;
        E -= blackArea * value_area;

        for (int r = 0; r<8; r++){
            for (int f = 0; f<8; f++){

                check(board, r, f);

            }
        }
        return E;
    }



    public String[] getSurroundings(int r, int f, boolean squares){

        String[] result = new String[8];

        for (int i = 0; i<8; i++) {
            result[i] = "/";
        }

        if (r != 0 && f != 0){
            if (squares) {
                result[0] = ""+board[r-1][f-1];
            } else {
                result[0] = (r-1) + "" + (f-1);
            }
        }
        if (r != 0) {
            if (squares) {
                result[1] = "" + board[r - 1][f];
            } else {
                result[1] = (r - 1) + "" + f;
            }
        }
        if (r != 0 && f != 7) {
            if (squares) {
                result[2] = "" + board[r - 1][f+1];
            } else {
                result[2] = (r - 1) + "" + (f+1);
            }
        }
        if (f != 0) {
            if (squares) {
                result[3] = "" + board[r][f-1];
            } else {
                result[3] = (r) + "" + (f-1);
            }
        }
        if (f != 7) {
            if (squares) {
                result[4] = "" + board[r][f+1];
            } else {
                result[4] = (r) + "" + (f+1);
            }
        }
        if (r != 7 && f != 0){
            if (squares) {
                result[5] = ""+board[r+1][f-1];
            } else {
                result[5] = (r+1) + "" + (f-1);
            }
        }
        if (r != 7) {
            if (squares) {
                result[6] = "" + board[r + 1][f];
            } else {
                result[6] = (r + 1) + "" + f;
            }
        }
        if (r != 7 && f != 7) {
            if (squares) {
                result[7] = "" + board[r + 1][f+1];
            } else {
                result[7] = (r + 1) + "" + (f+1);
            }
        }

        return result;

    }


    public int checkFile(int file, char character){
        int counter = 0;

        for (int r = 0; r<8; r++){
            if (board[r][file] == character){
                counter++;
            }
        }
        return counter;
    }



    public void pawn(int r, int f) {
        double n = 0;
        char current;
        char opponent;
        int m = 1;

        if (board[r][f] == 'P') {
            current = 'P';
            opponent = 'p';
        } else {
            current = 'p';
            opponent = 'P';
            m = -1;
        }


        // passed
        if (checkFile(f, opponent) == 0) {
            if (f == 0 || checkFile(f - 1, opponent) == 0) {
                if (f == 7 || checkFile(f + 1, opponent) == 0) {
                    if (current == 'P'){
                        E += (value_passedPawn * 18) / (8 - (r + 1));
                    } else {
                        E -= (value_passedPawn * 18) /r;
                    }
                }
            }
        }

        // isolated
        if (f == 0 || checkFile(f - 1, current) == 0) {
            if (f == 7 || checkFile(f + 1, current) == 0) {
                E -= value_isolatedPawn * m;
            }
        }

        // stacked
        n = checkFile(f, current) - 1;
        E -= ((n * value_stackedPawn + ((n*(n-1))/2) * 0.2 * value_stackedPawn)/2) * m;

        // advanced
        if (f>=2 && f<=5){
            if (current == 'p' && r<=5 && r>=3){
                E -= value_centralPawn;
            } else if(current == 'P' && r<=4 && r>=2) {
                E += value_centralPawn;
            }
        }

        // endgame pawnmoves
        if (endgame) {
            if (current == 'P') {
                E += value_advancedPawn * r;
            } else {
                E -= value_advancedPawn * (7-r);
            }
        }

        // chain
        int tmp = 0;

        if (f!=7 && r!=7){
            if (board[r+1][f+1] == current){
                double val = value_pawnChain * m;
                if (current == 'P'){
                    val *= (1/(double)r);
                } else {
                    val *= (double)(1/(double)(6-r));
                }
                E += val;
            }
        }
        if (f!=0 && r!=7){
            if (board[r+1][f-1] == current){
                double val = value_pawnChain * m;
                if (current == 'P'){
                    val *= (1/(double)r);
                } else {
                    val *= (double)(1/(double)(6-r));
                }
                E += val;
            }
        }

    }


    public void knight(int r, int f) {
        int m = 1;

        if (board[r][f] == 'n') {m = -1;}

        if (f >= 2 && f <= 5) {
            if (r <= 5 && r >= 2){
                E += value_centralKnight * m;
            }
        }
    }


    public void rook(int r, int f){

        int m = 1;
        if (board[r][f] == 'r') {m = -1;}

        if (f >= 2 && f <= 5) {
            E += value_centralRook * m;
        }

        if (r == 6 && board[r][f] == 'R') {
            E += value_seventhRankRook;
        } else if (r == 1 && board[r][f] == 'r') {
            E -= value_seventhRankRook;
        }

    }


    public void king(int r, int f) {

        int m = 1;
        int r_ = r;

        if (board[r][f] == 'k') {
            m = -1;
            r = (r - 7) * m;
        }

        if (endgame){

            String[] s = getSurroundings(r_, f, true);

            if (m == 1){
                if (s[0].equals('P'+ "")){
                    E += value_kingLead;
                }
                if (s[1].equals('P'+ "")){
                    E += value_kingLead;
                }
                if (s[2].equals('P'+ "")){
                    E += value_kingLead;
                }
            } else {
                if (s[5].equals('p'+ "")){
                    E -= value_kingLead;
                }
                if (s[6].equals('p'+ "")){
                    E -= value_kingLead;
                }
                if (s[7].equals('p'+ "")){
                    E -= value_kingLead;
                }
            }
        } else {
            if (r == 0 && (f == 0 || f == 1 || f == 6 || f == 7)) {
                E += value_kingSafety * m;
            } else if (r == 1 && (f == 0 || f == 7)) {
                E += value_kingSafety * m * (1 / 6.0);
            } else if (r == 0 && f == 2) {
                E += value_kingSafety * m * (1 / 5.0);

            }
        }
    }



    public void check(char[][] board, int r, int f) {

        if (Character.toLowerCase(board[r][f]) == 'p'){
            pawn(r, f);
        } else if (Character.toLowerCase(board[r][f]) == 'n') {
            knight(r, f);
        } else if (Character.toLowerCase(board[r][f]) == 'r') {
            rook(r, f);
        } else if (Character.toLowerCase(board[r][f]) == 'k') {
            king(r, f);
        }

    }
}