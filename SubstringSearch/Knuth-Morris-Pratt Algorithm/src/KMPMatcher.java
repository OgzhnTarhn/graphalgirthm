public class KMPMatcher {

    private final int R;      // Alfabet büyüklüğü
    private int[][] dfa;      // dfa[c][j]

    /**
     * Desen için DFA tablosunu oluşturur.
     *
     * @param pat Aranacak desen (pattern)
     */
    public KMPMatcher(String pat) {
        this.R = 256;
        int M = pat.length();
        dfa = new int[R][M];
        // İlk karakter eşleşmesi
        dfa[pat.charAt(0)][0] = 1;
        // yeniden başlama durumu X
        for (int X = 0, j = 1; j < M; j++) {
            // mismatch durumları: önceki duruma dön
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            // match durumunda ilerle
            dfa[pat.charAt(j)][j] = j + 1;
            // X = pat[j] karakterindeki yeniden başlama durumu
            X = dfa[pat.charAt(j)][X];
        }
    }

    /**
     * Metin içinde desenin ilk geçtiği indeksi döner.
     * Bulamazsa -1 döner.
     *
     * @param txt Aranacak metin (text)
     * @return Desenin başlangıç indeksi veya -1
     */
    public int search(String txt) {
        int N = txt.length();
        int M = dfa[0].length;
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) {
            return i - M;  // Desen başladı index
        }
        return -1;         // Bulunamadı
    }

    public static void main(String[] args) {
        // Örnek metin ve desen
        String text = "ABABABACABABAC";
        String pattern = "ABABAC";

        // KMP eşleştirici oluştur
        KMPMatcher matcher = new KMPMatcher(pattern);

        // Arama
        int index = matcher.search(text);
        if (index != -1) {
            System.out.printf("Desen \"%s\" metin içinde %d. indekste bulundu.%n", pattern, index);
        } else {
            System.out.printf("Desen \"%s\" metin içinde bulunamadı.%n", pattern);
        }
    }
}
