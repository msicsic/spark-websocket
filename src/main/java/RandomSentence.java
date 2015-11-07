import java.util.*;

public class RandomSentence {

    private static String[] name      = "Alice_Bob|Alex|Nina|Jen|Carl|Jill|Jack|Ben|Rose|John|Max|Joe|Kim".split("\\|");
    private static String[] action    = "wants to|used to|likes to|often tries to|decided not to|decided to|doesn't want to|is learning how to".split("\\|");
    private static String[] activity  = "eat|dance|sleep|go to burger king|vacuum|mountain climb|stalk people|fly kites|bark".split("\\|");
    private static String[] prepish   = "with|without|on top of|underneath|next to|on|over|with the help of|in spite of|disguised as|because of".split("\\|");
    private static String[] adjective = "dry|wet|dead|giant|tiny|orange|cloudy|rude|obnoxious|starving".split("\\|");
    private static String[] noun      = "cranberries|racecars|spaceships|dandelions|leaves|windmills|puppets|aliens|tigers|mountains".split("\\|");

    public static String get() {
        return String.join(" ", get(name), get(action), get(activity), get(prepish), get(adjective), get(noun));
    }

    public static String get(String[] words) {
        return words[new Random().nextInt(words.length)];
    }
}
