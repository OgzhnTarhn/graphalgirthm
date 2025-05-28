import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {

    private final long Q;       // Mod (büyük rastgele asal)
    private final int R = 256;  // Alfabet tabanı (ASCII)
    private final int M;        // Desen uzunluğu
    private final long RM;      // R^(M-1) % Q
    private final long patHash; // Desenin hash'i

    /**
     * Rastgele büyük bir asal sayı üretir (hash modulus için).
     */
    private static long longRandomPrime() {
        return BigInteger.probablePrime(31, new Random()).longValue();
    }

    /**
     * Desen için ön işlemleri yapar: Q, RM, patHash
     */
    public RabinKarp(String pat) {
        this.M = pat.length();
        this.Q = longRandomPrime();

        // RM = R^(M-1) % Q
        long rm = 1;
        for (int i = 1; i <= M - 1; i++) {
            rm = (R * rm) % Q;
        }
        this.RM = rm;

        // Desenin hash'ini hesapla
        this.patHash = hash(pat, M);
    }

    /**
     * key[0..M-1] alt-dizesinin hash'ini hesaplar.
     */
    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (R * h + key.charAt(j)) % Q;
        }
        return h;
    }

    /**
     * Metin içinde desenin ilk geçtiği indeksi döner; bulunamazsa -1.
     */
    public int search(String txt, String pat) {
        int N = txt.length();
        if (N < M) return -1;

        // Metindeki ilk pencerenin hash'i
        long txtHash = hash(txt, M);
        // İlk pencereyi kontrol et
        if (txtHash == patHash && txt.substring(0, M).equals(pat)) {
            return 0;
        }

        // Kaydırma döngüsü
        for (int i = M; i < N; i++) {
            // Önce öndeki karakteri çıkar:
            txtHash = (txtHash + Q - (RM * txt.charAt(i - M)) % Q) % Q;
            // Yeni karakteri ekle:
            txtHash = (txtHash * R + txt.charAt(i)) % Q;

            // Hash eşleşirse, çakışma kontrolü yap
            int offset = i - M + 1;
            if (txtHash == patHash
                    && txt.substring(offset, offset + M).equals(pat)) {
                return offset;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        String text    = "ABACADABRA";
        String pattern = "ABRA";

        RabinKarp rk = new RabinKarp(pattern);
        int idx = rk.search(text, pattern);

        if (idx != -1) {
            System.out.printf("Desen \"%s\" metin içinde %d. indekste bulundu.%n",
                    pattern, idx);
        } else {
            System.out.printf("Desen \"%s\" metin içinde bulunamadı.%n", pattern);
        }
    }
}
