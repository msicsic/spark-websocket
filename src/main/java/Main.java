import org.eclipse.jetty.websocket.api.*;
import java.io.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static Timer feedTimer;
    static List<Session> feedSessions;

    public static void main(String[] args) {
        feedTimer = new Timer();
        feedSessions = new ArrayList<>();
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
            sendToAllWsClients(createHtmlMessage(RandomSentence.get()));
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

    private static String createHtmlMessage(String text) {
        return div().with(
                h1(new SimpleDateFormat("HH:mm:ss (dd. MMM, yyyy)").format(new Date())),
                h1("Sent to " + feedSessions.size() + " clients").withClass("client-count"),
                p(text)
        ).render();
    }

}
