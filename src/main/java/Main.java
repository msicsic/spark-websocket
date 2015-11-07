import org.eclipse.jetty.websocket.api.*;
import java.io.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static List<Session> currentUsers = new ArrayList<>();

    public static void main(String[] args) {
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        port(4567);
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
    }

    public static void sendToAllWsClients(String string) {
        Main.currentUsers.stream().filter(Session::isOpen).forEach(s -> {
            try{
                s.getRemote().sendString(createHtmlMessage(string));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static String createHtmlMessage(String string) {
        return article().with(
                h1(new SimpleDateFormat("HH:mm:ss (dd. MMM, yyyy)").format(new Date())),
                h2("Sent to " + currentUsers.size() + " clients").withClass("client-count"),
                pre().with(text(string))
        ).render();
    }

}
