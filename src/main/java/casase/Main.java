package casase;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        UrlCutter urlCutter = new UrlCutter();
        urlCutter.encode("https://anton.azzot.com");
    }

    private static Consumer<String> getStringConsumer(String str) {
        return s -> {
            String s1 = s.toLowerCase().concat(str);
            System.out.println(s1);
        };
    }
}
