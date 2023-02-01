package casase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UrlCutter {
    private static final String POSSIBLE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzAbCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String tinyUrlHost = "http://tinyurl.com/";

    private final Map<String, String> inMemLongToShort = new HashMap<>();
    private final Map<String, String> inMemShortToLong = new HashMap<>();

    public String encode(String longUrl) {
        if (inMemLongToShort.containsKey(longUrl)) {
            return inMemLongToShort.get(longUrl);
        }

        String encoded = tinyUrlHost + hash();
        inMemLongToShort.put(longUrl, encoded);
        inMemShortToLong.put(encoded, longUrl);

        return encoded;
    }

    private String hash() {
        StringBuilder hashBuilder = new StringBuilder();
        Random random = new Random();
        while (true) {
            for (int i = 0; i < 7; i++) {
                hashBuilder.append(random.nextInt(POSSIBLE_SYMBOLS.length()));
            }
            if (!inMemShortToLong.containsKey(hashBuilder.toString())) {
                return hashBuilder.toString();
            }
            hashBuilder.setLength(0);
        }
    }

    public String decode(String shortUrl) {
        return inMemShortToLong.get(shortUrl);
    }
}
