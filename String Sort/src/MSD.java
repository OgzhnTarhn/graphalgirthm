public class MSD {

    public static void main(String[] args) {
        String[] a = {
                "she", "sells", "sea", "shells",
                "by", "the", "sea", "shore"
        };

        MSD.sort(a);  // MSD radix sort ile sıralama

        for (String s : a) {
            System.out.println(s);
        }
    }


    // MSD radix sort sınıfı
    private static final int R = 256;     // ASCII karakter kümesi
    private static final int CUTOFF = 15; // küçük alt dizilerde insertion sort kullan

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, 0, a.length - 1, 0, aux);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + CUTOFF) {
            insertionSort(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2]; // +2: -1 karakter için yer

        // Frekans sayımı (d. karaktere göre)
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }

        // Kümülatif toplam
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // Yardımcı diziye yerleştir
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }

        // Geri kopyala
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // Alt grupları sırala (recursive)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1; // kısa string varsa öncelikli
    }

    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
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