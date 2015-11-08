import org.eclipse.jetty.websocket.api.*;
import org.json.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Main {

    static List<Session> currentUsers = new ArrayList<>(); //Directly available to ChatWebSocketHandler

    public static void main(String[] args) {
        port(9999);
        staticFileLocation("public"); // index.html will be served at localhost:4567 (default port)
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
    }

    //Sends a message from one user to all users, along with a list of current users
    public static void sendToAll(Session user, String string) {
        currentUsers.stream().filter(Session::isOpen).forEach(session -> {
            try {
                JSONObject json = new JSONObject();
                json.put("userMessage", createHtmlMessage(user, string)); //The message wrapped in HTML
                json.put("userlist", currentUsers.stream().map(Main::getUsername).collect(Collectors.toList())); //List of usernames
                session.getRemote().sendString(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //Builds a HTML element with timestamp, username and message
    private static String createHtmlMessage(Session user, String string) {
        return article().with(
                b(getUsername(user) + " says:"),
                p(string),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }

    //Creates a username based on the session port
    public static String getUsername(Session user) {
        return "User " + user.getRemoteAddress().getPort();
    }

}
