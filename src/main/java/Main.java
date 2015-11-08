import org.eclipse.jetty.websocket.api.*;
import org.json.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;
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

    public static void sendToAll(Session session, String string) {
        Main.currentUsers.stream().filter(Session::isOpen).forEach(s -> {
            try{
                JSONObject json = new JSONObject();
                json.put("message", createHtmlMessage(session, string));
                json.put("userlist", Main.currentUsers.stream().map(Main::getUsername).collect(Collectors.toList()));
                s.getRemote().sendString(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String createHtmlMessage(Session session, String string) {
        return article().with(
                h1(new SimpleDateFormat("HH:mm:ss (dd. MMM, yyyy)").format(new Date())),
                h2("Sent to " + currentUsers.size() + " clients").withClass("client-count"),
                p().with(b(getUsername(session)), text(" says \"" + string + "\""))
        ).render();
    }

    public static String getUsername(Session session) {
        return "User_" + session.getRemoteAddress().getPort();
    }

}
