public class GREP {
    public static void main(String[] args) {
        // Argüman olarak gelen deseni, tüm satırı kapsayacak şekilde sarıyoruz
        String re = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(re);

        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            if (nfa.recognizes(line))
                StdOut.println(line);
        }
    }
}
