import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Main.sendToAllWsClients(getUsername(session) + " connected");
        Main.currentUsers.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        Main.sendToAllWsClients(getUsername(session) + " disconnected");
        Main.currentUsers.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        Main.sendToAllWsClients(getUsername(session) + ": " + message);
    }

    private String getUsername(Session session) {
        return "User_" + session.getRemoteAddress().getPort();
    }

}
