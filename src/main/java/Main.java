import org.eclipse.jetty.websocket.api.*;
import java.io.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static Timer feedTimer;
    static List<Session> wsSessions;

    public static void main(String[] args) {
        feedTimer = new Timer();
        wsSessions = Collections.synchronizedList(new ArrayList<>());
        setTimerSpeed(2500);
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        port(9999);
        webSocket("/randomGeneratedFeed", FeedWebSocketHandler.class);
        init();
    }

    public static void setTimerSpeed(int intervalInMillis) {
        feedTimer.cancel();
        feedTimer = new Timer();
        feedTimer.scheduleAtFixedRate(createTimerTask(), intervalInMillis, intervalInMillis); //delay, interval
    }


    private static TimerTask createTimerTask() {
        return new TimerTask() {
            public void run() {
                sendToAllWsClients(createHtmlMessage(RandomSentence.get()));
            }
        };
    }

    private static void sendToAllWsClients(String string) {
        Main.wsSessions.stream().filter(Session::isOpen).forEach(s -> {
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
                p(text)
        ).render();
    }

}
