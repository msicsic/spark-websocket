import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket

@WebSocket
class WSHandler {

    @OnWebSocketConnect
    fun onConnect(user: Session) {
        val username = "User ${nextUserNumber++}"
        userUsernameMap.put(user, username)
        broadcastMessage("Server", "$username joined the chat")
    }

    @OnWebSocketClose
    fun onClose(user: Session, statusCode: Int, reason: String) {
        val username = userUsernameMap[user]
        userUsernameMap.remove(user)
        broadcastMessage("Server", "$username left the chat")
    }

    @OnWebSocketMessage
    fun onMessage(user: Session, message: String) {
        broadcastMessage(userUsernameMap[user], message)
    }
}