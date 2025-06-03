public class MSD {
    private static final int R = 256; // ASCII
    private static final int CUTOFF = 15;

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, 0, a.length - 1, 0, aux);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d); // küçük parçada insertion sort
            return;
        }

        int[] count = new int[R + 2]; // +2 çünkü -1 olabilir (short string)

        // Frequency count
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d); // d. karakter
            count[c + 2]++;          // kaydırma
        }

        // Compute cumulates
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // Move to aux
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }

        // Copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // Recursively sort for each character value
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1; // shorter strings come first
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
