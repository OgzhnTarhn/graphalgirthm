import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String s = "banana";
        int[] sa = buildSuffixArray(s);

        System.out.println("Suffix Array:");
        for (int idx : sa) {
            System.out.println(idx + ": " + s.substring(idx));
        }
    }

    // Suffix Array Builder (O(N log^2 N))
    public static int[] buildSuffixArray(String s) {
        int n = s.length();
        Integer[] sa = new Integer[n];  // indeksleri tutacak
        int[] rank = new int[n];        // her suffix'in rank'i
        int[] temp = new int[n];        // geçici rank'lar

        // Başlangıç: sadece ilk karaktere göre sıralama
        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = s.charAt(i); // ASCII değer
        }

        for (int k = 1; k < n; k *= 2) {
            final int K = k;

            // Sıralama: önce rank[i], sonra rank[i + k]
            Arrays.sort(sa, (i, j) -> {
                if (rank[i] != rank[j])
                    return Integer.compare(rank[i], rank[j]);
                int ri = (i + K < n) ? rank[i + K] : -1;
                int rj = (j + K < n) ? rank[j + K] : -1;
                return Integer.compare(ri, rj);
            });

            // Yeni rank'leri oluştur
            temp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                boolean same =
                        rank[sa[i]] == rank[sa[i - 1]] &&
                                ((sa[i] + K < n && sa[i - 1] + K < n &&
                                        rank[sa[i] + K] == rank[sa[i - 1] + K]) ||
                                        (sa[i] + K >= n && sa[i - 1] + K >= n));
                temp[sa[i]] = temp[sa[i - 1]] + (same ? 0 : 1);
            }

            // temp → rank aktar
            System.arraycopy(temp, 0, rank, 0, n);
        }

        // Integer[] → int[] dönüşümü
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = sa[i];
        }
        return result;
    }
}
