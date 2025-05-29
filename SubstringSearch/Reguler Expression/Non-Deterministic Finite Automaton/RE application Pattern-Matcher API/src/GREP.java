import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GREP {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Kullanım:");
            System.err.println("  java GREP <regex>                  (STDIN’dan okur)");
            System.err.println("  java GREP <regex> <dosya_yolu>     (Dosyadan okur)");
            System.exit(1);
        }

        // Deseni satır içinde aramak için başına/sonuna .* ekliyoruz
        Pattern pattern = Pattern.compile(".*" + args[0] + ".*");

        // İki argüman varsa: dosyadan okuma
        if (args.length >= 2) {
            String filePath = args[1];
            try {
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                for (String line : lines) {
                    if (pattern.matcher(line).matches()) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Dosya okunurken hata: " + e.getMessage());
                System.exit(2);
            }
        }
        // Tek argüman: STDIN’den okuma
        else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pattern.matcher(line).matches()) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Girdi okunurken hata: " + e.getMessage());
                System.exit(2);
            }
        }
    }
}
