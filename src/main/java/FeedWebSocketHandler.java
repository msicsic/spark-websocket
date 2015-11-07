import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class FeedWebSocketHandler {

    private Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println(session.getRemoteAddress().toString() + " connected");
        this.session = session;
        Main.feedSessions.add(this.session);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println(session.getRemoteAddress().toString() + " disconnected");
        Main.feedSessions.remove(this.session);
        this.session = null;
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        switch (message) {
            case "SLOW": Main.setTimerSpeed(2500); break;
            case "FAST": Main.setTimerSpeed(500);  break;
        }
    }

}
