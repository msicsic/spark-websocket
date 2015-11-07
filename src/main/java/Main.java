import org.eclipse.jetty.websocket.api.*;
import java.io.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static Timer feedTimer = new Timer();
    static List<Session> wsSessions = new ArrayList<>();

    public static void main(String[] args) {
        setTimerSpeed(2500);
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        port(9999);
        webSocket("/randomGeneratedFeed", FeedListener.class);
        init();
    }

    public static void setTimerSpeed(int intervalInMillis) {
        feedTimer.cancel();
        feedTimer = new Timer();
        feedTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                send(createHtmlMessage(RandomSentence.get()));
            }
        }, intervalInMillis, intervalInMillis); //delay, interval
    }

    private static void send(String string) {
        try {
            for(Session session : Main.wsSessions) {
                if(session.isOpen()) {
                    session.getRemote().sendString(string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createHtmlMessage(String text) {
        return div().with(
                text(new SimpleDateFormat("HH:mm:ss (dd. MMM, yyyy)").format(new Date())),
                br(),
                text(text)
        ).render();
    }

}
