import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Harvester {
    public static void main(String[] args) throws Exception {
        String regexp = args[0];
        String input  = new String(Files.readAllBytes(Paths.get(args[1])));

        // 1. Derle: Regex → Pattern (arka planda NFA oluşturulur)
        Pattern pattern = Pattern.compile(regexp);

        // 2. Bağla: Pattern + input → Matcher (simülatör)
        Matcher matcher = pattern.matcher(input);

        // 3. Ara: find() ile sıradaki eşleşmeyi bul
        while (matcher.find()) {
            // 4. Dönen alt dizeyi al
            System.out.println(matcher.group());
        }
    }
}
