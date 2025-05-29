import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Harvester {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Kullanım: java Harvester <regex> <dosya_yolu>");
            System.exit(1);
        }

        String regexp = args[0];
        String filePath = args[1];

        try {
            // 1. Dosyanın tüm içeriğini oku
            String input = new String(Files.readAllBytes(Paths.get(filePath)));

            // 2. Derleme: Regex → Pattern (arka planda NFA veya optimizasyonlu motor)
            Pattern pattern = Pattern.compile(regexp);

            // 3. Bağlama: Pattern + input → Matcher (simülatör)
            Matcher matcher = pattern.matcher(input);

            // 4. Bul ve Yazdır
            System.out.println("Eşleşen alt diziler:");
            while (matcher.find()) {
                System.out.println("  • " + matcher.group());
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata: " + e.getMessage());
            System.exit(2);
        }
    }
}
