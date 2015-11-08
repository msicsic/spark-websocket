import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session currentUser) throws Exception {
        Main.currentUsers.add(currentUser);
        Main.sendToAll(currentUser, "I have arrived!");
    }

    @OnWebSocketClose
    public void onClose(Session currentUser, int statusCode, String reason) {
        Main.currentUsers.remove(currentUser);
        Main.sendToAll(currentUser, "I'm outta here!");
    }

    @OnWebSocketMessage
    public void onMessage(Session currentUser, String message) {
        Main.sendToAll(currentUser, message);
    }

}
