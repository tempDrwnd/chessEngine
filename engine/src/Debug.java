public class Debug {

    public static void main(String[] args){

        // P = white pawn
        // p = black pawn

        // [f0, r0, fc, rc]

        int[][] moves1 = {{1, 0, 2, 2}, {2, 1, 2, 2}, {5, 0, 4, 2}, {4, 0, 4, 1}};
        int[][] moves2 = {{1, 7, 2, 5}, {2, 6, 2, 5}, {5, 7, 4, 5}, {4, 7, 4, 6}};

        char[][] boardSet = {
                {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}
        };
        char[][] boardEmpty = {
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'}
        };
        char[][] boardTest = {
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', 'P', 'P', 'P', '0', '0'},
                {'0', '0', '0', '0', 'K', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '0', '0', 'k', '0', '0', '0'},
                {'0', '0', '0', 'p', 'p', 'p', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0', '0'}
        };


        Evaluate bot = new Evaluate(
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5
        );
        double eval = bot.evaluate(boardTest, moves1, moves2);
        System.out.println(eval);
    }
}
