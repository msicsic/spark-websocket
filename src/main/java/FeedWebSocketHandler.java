import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class FeedWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println(session.getRemoteAddress() + " connected");
        Main.feedSessions.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println(session.getRemoteAddress() + " disconnected");
        Main.feedSessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        switch (message) {
            case "SLOW": Main.setTimerSpeed(2500); break;
            case "FAST": Main.setTimerSpeed(500);  break;
        }
    }

}
