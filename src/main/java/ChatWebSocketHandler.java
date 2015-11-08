import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        Main.currentUsers.add(user);
        Main.sendToAll(user, "I have arrived!");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        Main.currentUsers.remove(user);
        Main.sendToAll(user, "I'm outta here!");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Main.sendToAll(user, message);
    }

}
