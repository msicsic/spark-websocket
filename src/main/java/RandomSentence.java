import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomSentence {

    public static String get() {
        return random(name())      + " " +
               random(action())    + " " +
               random(activity())  + " " +
               random(prepish())   + " " +
               random(adjective()) + " " +
               random(noun());
    }

    public static List<String> name()      { return Arrays.asList("Alice", "Bob", "Alex", "Nina", "Jen", "Carl", "Jill", "Jack", "Ben", "Rose", "John", "Max", "Joe", "Kim"); }
    public static List<String> action()    { return Arrays.asList("wants to", "used to", "likes to", "often tries to", "decided not to", "decided to", "doesn't want to", "is learning how to"); }
    public static List<String> activity()  { return Arrays.asList("eat", "dance", "sleep", "go to burger king", "vacuum", "mountain climb", "stalk people", "fly kites", "bark"); }
    public static List<String> prepish()   { return Arrays.asList("with", "without", "on top of", "underneath", "next to", "on", "over", "with the help of", "in spite of", "disguised as", "because of"); }
    public static List<String> adjective() { return Arrays.asList("dry", "wet", "dead", "giant", "tiny", "orange", "cloudy", "rude", "obnoxious", "starving"); }
    public static List<String> noun()      { return Arrays.asList("cranberries", "racecars", "spaceships", "dandelions", "leaves", "windmills", "puppets", "aliens", "tigers", "mountains"); }

    public static String random(List<String> list) {
        Collections.shuffle(list);
        return list.get(0);
    }
}
