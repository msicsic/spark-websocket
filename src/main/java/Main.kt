import j2html.TagCreator.*
import org.eclipse.jetty.websocket.api.Session
import org.json.JSONObject
import spark.Spark.*
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    staticFileLocation("public") //index.html is served at localhost:4567 (default port)
    webSocket("/chat", WSHandler::class.java)
    init()
}

var userUsernameMap: MutableMap<Session, String> = HashMap()
var nextUserNumber = 1

internal fun broadcastMessage(sender: String?, message: String) {
    userUsernameMap.keys.filter { it.isOpen }.forEach {
        try {
            it.remote.sendString(JSONObject().put("userMessage", createHtmlMessageFromSender(sender, message)).put("userlist", userUsernameMap.values).toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

internal fun createHtmlMessageFromSender(sender: String?, message: String) {
    article().with(
            b("$sender says:"),
            p(message),
            span().withClass("timestamp").withText(SimpleDateFormat("HH:mm:ss").format(Date()))).render()
}
