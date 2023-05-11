import java.util.Random;
import java.time.Duration;
import java.time.Instant;


public class Test {

    public static void main(String[] args){

        char[][] board = {
                {'R', '0', 'B', 'Q', 'K', 'B', 'N', 'R'},
                {'P', 'P', 'P', '0', '0', 'P', 'P', 'P'},
                {'0', '0', '0', 'P', '0', '0', 'q', '0'},
                {'0', '0', '0', '0', 'P', '0', '0', '0'},
                {'0', '0', 'N', 'p', 'p', '0', '0', '0'},
                {'0', '0', '0', '0', '0', 'p', '0', '0'},
                {'p', 'p', 'p', '0', '0', '0', '0', 'p'},
                {'r', 'n', 'b', '0', 'k', 'b', 'n', 'r'}
        };

        int n = 1000000000;
        //char[][][] array = new char[n][8][8];
        //char[] pieces = {'r', 'R', 'n', 'N', 'b', 'B', 'q', 'Q', 'k', 'K', 'p', 'P'};
        //Random randint = new Random();
//
        //for (int i = 0; i<n; i++) {
        //    for (int r = 0; r<8; r++) {
        //        for (int f = 0; f<8; f++) {
        //            if (randint.nextInt(2) == 1){
        //                array[i][r][f] = pieces[randint.nextInt(pieces.length)];
        //            } else {
        //                array[i][r][f] = '0';
        //            }
//
        //        }
        //    }
        //}


        //for (int i = 0; i<n; i++) {
        //    for (int r = 0; r<8; r++) {
        //        for (int f = 0; f<8; f++) {
        //            //System.out.print(array[i][r][f]);
        //        }
        //        //System.out.println("");
        //    }
        //    //System.out.println(bot.evaluate(array[i]));
        //    //System.out.println("--------");
        //}

        Instant start = Instant.now();

        for (int i = 0; i<n; i++) {
            Evaluate bot = new Evaluate(
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
            //System.out.println(bot.evaluate(board));
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(n+" boards");
        System.out.println(timeElapsed.toMillis() + " ms in total");
        System.out.println(timeElapsed.toMillis()/(double)n +" ms per board");

    }

}
