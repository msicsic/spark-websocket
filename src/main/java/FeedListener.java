import javafx.collections.*;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;

@WebSocket
public class FeedListener {

    private Session session;
    private ListChangeListener listChangeListener = c -> {
        send(createHtmlMessage(c.toString()));
    };

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println(session.getRemoteAddress().toString() + " connected");
        this.session = session;
        Main.observableList.addListener(listChangeListener);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.session = null;
        Main.observableList.removeListener(listChangeListener);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        switch (message) {
            case "SLOW": Main.setTimerSpeed(2500); break;
            case "FAST": Main.setTimerSpeed(500);  break;
        }
    }

    private String createHtmlMessage(String text) {
        return div().with(
                text(new SimpleDateFormat("HH:mm:ss (dd. MMM, yyyy)").format(new Date())),
                br(),
                text(text)
        ).render();
    }

    private void send(String string) {
        try {
            session.getRemote().sendString(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
