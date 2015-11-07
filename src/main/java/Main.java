import org.eclipse.jetty.websocket.api.*;
import java.io.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static Timer feedTimer = new Timer();
    static List<Session> feedSessions = new ArrayList<>();
    static int numberOfMessagesSent = 0;

    public static void main(String[] args) {
        setTimerSpeed(2500);
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        port(9999);
        webSocket("/randomGeneratedFeed", FeedWebSocketHandler.class);
        init();
    }

    public static void setTimerSpeed(int interval) {
        feedTimer.cancel();
        feedTimer = new Timer();
        feedTimer.scheduleAtFixedRate(new TimerTask() { public void run() {
            sendToAllWsClients(createRandomMessage());
        }}, 0, interval); //0 delay
    }

    private static void sendToAllWsClients(String string) {
        Main.feedSessions.stream().filter(Session::isOpen).forEach(s -> {
            try{
                s.getRemote().sendString(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static String createRandomMessage() {
        return article().with(
                h1("Message #" + numberOfMessagesSent++),
                h2("Sent to " + feedSessions.size() + " clients").withClass("client-count"),
                p(RandomSentence.get())
        ).render();
    }

}
