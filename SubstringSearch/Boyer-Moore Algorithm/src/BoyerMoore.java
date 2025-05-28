public class BoyerMoore {

    private final int R;       // Alfabet büyüklüğü (örn. ASCII)
    private int[] right;       // right[c] = desendeki en sağdaki c karakterinin indeksi

    /**
     * Desen için 'right' tablosunu (bad-character heuristic) hazırlar.
     *
     * @param pat Aranacak desen
     */
    public BoyerMoore(String pat) {
        this.R = 256;
        int M = pat.length();
        right = new int[R];
        // Başlangıçta tüm karakterleri -1 (desende hiç yok) olarak işaretle
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        // Desendeki her karakter için en sağdaki konumu kaydet
        for (int j = 0; j < M; j++) {
            right[ pat.charAt(j) ] = j;
        }
    }

    /**
     * Metin içinde desenin ilk geçtiği indeksi döner. Bulamazsa -1 döner.
     *
     * @param txt Aranacak metin
     * @param pat Aranacak desen
     * @return Desenin başlangıç indeksi veya -1
     */
    public int search(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int skip;

        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            // Deseni sağdan sola tara
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    // Uyuşmazlık: kaydırma miktarını hesapla
                    int c = txt.charAt(i + j);
                    skip = j - right[c];
                    if (skip < 1) skip = 1;
                    break;
                }
            }
            // Eğer skip==0 ise tüm j’ler eşleşmiş demektir
            if (skip == 0) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String text    = "ABACADABRA";
        String pattern = "ABRA";

        BoyerMoore bm = new BoyerMoore(pattern);
        int index = bm.search(text, pattern);

        if (index != -1) {
            System.out.printf("Desen \"%s\" metin içinde %d. indekste bulundu.%n",
                    pattern, index);
        } else {
            System.out.printf("Desen \"%s\" metin içinde bulunamadı.%n", pattern);
        }
    }
}
