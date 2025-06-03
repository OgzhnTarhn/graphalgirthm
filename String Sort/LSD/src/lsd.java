public class lsd {
    public static void main(String[] args) {
        String[] a = {
                "dab", "add", "cab", "fad",
                "fee", "bad", "dad", "bee",
                "fed", "bed", "ebb", "ace"
        };

        lsdSort(a, 3); // LSD radix sort, sabit uzunluk: 3 karakter

        for (String s : a) {
            System.out.println(s);
        }
    }

    public static void lsdSort(String[] a, int W) {
        int N = a.length;
        int R = 256; // ASCII karakter kümesi
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];

            // 1. Frekans sayımı
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            // 2. Kümülatif toplam
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // 3. Yardımcı diziye yerleştir
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // 4. Geri kopyala
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }
}
