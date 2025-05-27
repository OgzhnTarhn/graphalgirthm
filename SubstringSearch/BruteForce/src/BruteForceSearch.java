public class BruteForceSearch {

    /**
     * Metin içinde desenin ilk geçtiği indeksi döner.
     * Bulamazsa -1 döner.
     *
     * @param txt Aranacak metin (text)
     * @param pat Aranacak desen (pattern)
     * @return Desenin başlangıç indeksi veya -1
     */
    public static int search(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        for (int i = 0; i <= N - M; i++) {
            int j = 0;
            // Karakter karakter karşılaştır
            for (; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            // Eğer tüm karakterler eşleştiyse
            if (j == M) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // Örnek metin ve desen
        String text = "ABACADABRA";
        String pattern = "ABRA";

        int index = search(text, pattern);
        if (index != -1) {
            System.out.printf("Desen \"%s\" metin içinde %d. indekste bulundu.%n", pattern, index);
        } else {
            System.out.printf("Desen \"%s\" metin içinde bulunamadı.%n", pattern);
        }
    }
}
