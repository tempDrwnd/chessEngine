public class Evaluate {
    static double value_passedPawn = 0.5;
    static double value_isolatedPawn = 0.5;
    static double value_stackedPawn = 0.5;

    static double E = 0.0;

    public static double evaluate(char[][] board){

        for (int r = 0; r<8; r++){
            for (int f = 0; f<8; f++){

                check(board, r, f);

            }
        }
        return E;
    }


    public static int checkFile(char[][] board, int file, char character){
        int counter = 0;

        for (int r = 0; r<8; r++){
            if (board[r][file] == character){
                counter++;
            }
        }
        return counter;
    }


    public static void pawn(char[][] board, int r, int f) {
        double n = 0;
        char current;
        char opponent;
        int m = 1;

        if (board[r][f] == 'P') {
            current = 'P';
            opponent = 'p';
            m = 1;
        } else {
            current = 'p';
            opponent = 'P';
            m = -1;
        }


            // passed
            if (checkFile(board, f, opponent) == 0) {
                if (f == 0 || checkFile(board, f - 1, opponent) == 0) {
                    if (f == 7 || checkFile(board, f + 1, opponent) == 0) {
                        if (current == 'P'){
                            E += (value_passedPawn * 18) / (8 - (r + 1));
                        } else {
                            E -= (value_passedPawn * 18) /r;
                        }
                    }
                }
            }

            // isolated white
            if (f == 0 || checkFile(board, f - 1, current) == 0) {
                if (f == 7 || checkFile(board, f + 1, current) == 0) {
                    E -= value_isolatedPawn * m;
                }
            }

            // stacked white
            n = checkFile(board, f, current) - 1;
            System.out.println(f+1+" "+(r+1)+" | "+n+" | "+board[r][f]);
            E -= ((n * value_stackedPawn + ((n*(n-1))/2) * 0.2 * value_stackedPawn)/2) * m;
            // advanced white
            // chain white
    }


    public static void check(char[][] board, int r, int f) {

        if (Character.toLowerCase(board[r][f]) == 'p'){
            pawn(board, r, f);
        }

    }
}