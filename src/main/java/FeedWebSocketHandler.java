import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class FeedWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Main.sendToAllWsClients("User_"+session.getRemoteAddress().getPort() + " connected");
        Main.feedSessions.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        Main.sendToAllWsClients("User_" + session.getRemoteAddress().getPort() + " disconnected");
        Main.feedSessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        Main.sendToAllWsClients(message);
    }

}
