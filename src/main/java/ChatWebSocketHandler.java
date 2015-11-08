import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Main.currentUsers.add(session);
        Main.sendToAll(session, "I have arrived!");
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        Main.currentUsers.remove(session);
        Main.sendToAll(session, "I'm outta here!");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        Main.sendToAll(session, message);
    }

}
