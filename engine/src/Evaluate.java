public class Evaluate {

    char[][] board = new char[8][8];
    double E = 0.0;
    boolean endgame = false;

    int value_White;
    int value_Black;

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

    public Evaluate(
            double value_passedPawn,
            double value_isolatedPawn,
            double value_stackedPawn,
            double value_centralPawn,
            double value_advancedPawn,
            double value_centralKnight,
            double value_centralRook,
            double value_seventhRankRook,
            double value_pawnChain
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
    }


    public double evaluate(char[][] array){
        this.board = array;

        for (int y = 0; y<8; y++) {
            for (int x = 0; x<8; x++) {
                if (Character.toLowerCase(board[y][x]) == 'p'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += 1;
                    } else {
                        value_White += 1;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'n' || Character.toLowerCase(board[y][x]) == 'b'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += 3;
                    } else {
                        value_White += 3;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'r'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += 5;
                    } else {
                        value_White += 5;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'q'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += 10;
                    } else {
                        value_White += 10;
                    }
                }
                if (Character.toLowerCase(board[y][x]) == 'k'){
                    if (Character.toLowerCase(board[y][x]) == board[y][x]){
                        value_Black += 1000;
                    } else {
                        value_White += 1000;
                    }
                }

            }
        }

        E += value_White;
        E -= value_Black;

        if ((value_White + value_Black)/2.0 <= 1016) {
            endgame = true;
        }

        for (int r = 0; r<8; r++){
            for (int f = 0; f<8; f++){

                check(board, r, f);

            }
        }
        return E;
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


    public void rook(char[][] board, int r, int f){

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


    public void check(char[][] board, int r, int f) {

        if (Character.toLowerCase(board[r][f]) == 'p'){
            pawn(r, f);
        } else if (Character.toLowerCase(board[r][f]) == 'n') {
            knight(r, f);
        } else if (Character.toLowerCase(board[r][f]) == 'r') {
            rook(board, r, f);
        }

    }
}