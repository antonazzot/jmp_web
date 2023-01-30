package casase;

import java.util.List;
import java.util.function.Consumer;

public class FunctionsTransferring<S> {

    private final List<String> strings = List.of("A", "B", "C");

    public S getConsumer(Consumer<String> function, String str) {
        for (String s : strings) {
            function.accept(s);
        }
        function.accept(str);
        return (S) str;
    }
}
