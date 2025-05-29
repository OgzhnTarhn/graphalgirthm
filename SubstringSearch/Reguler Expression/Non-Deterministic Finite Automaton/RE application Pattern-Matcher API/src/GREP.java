import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class GREP {
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: java GREP <regex>");
            System.exit(1);
        }

        // Sunumdaki gibi: satırda args[0] içeren her şeyi yakalamak için
        String re = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(re);

        // STDIN'den gelen her satırı test et
        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            if (nfa.recognizes(line)) {
                StdOut.println(line);
            }
        }
    }
}
