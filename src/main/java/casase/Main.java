package casase;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        FunctionsTransferring<String> transferring = new FunctionsTransferring<>();

        String str = "ANTON";

        String antin = transferring.getConsumer(getStringConsumer("ANTOn"), "ANTIN"
        );

        System.out.println(antin);

    }

    private static Consumer<String> getStringConsumer(String str) {
        return s -> {
            String s1 = s.toLowerCase().concat(str);
            System.out.println(s1);
        };
    }
}
