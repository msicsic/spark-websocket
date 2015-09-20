import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class RandomFeed {

    private Session session;
    private Timer feedTimer;
    //final ScheduledExecutorService feedExecutor = Executors.newScheduledThreadPool(1);

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        this.session = session;
        this.feedTimer = new Timer();
        setTimerSpeed(2500);
        sendString("SERVER: Ready. Setting speed to 2500ms.");
    }

    @OnWebSocketClose
    public void onCLose(int statusCode, String reason) {
        this.feedTimer.cancel();
        this.session = null;
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        if("SLOW".equals(message)) {
            setTimerSpeed(2500);
            sendString("SERVER: Setting speed to 2500ms");
        } else if("FAST".equals(message)) {
            setTimerSpeed(500);
            sendString("SERVER: Setting speed to 500ms");
        } else {
            sendString("SERVER: " + message);
        }
    }

    private void setTimerSpeed(int intervalInMillis) {
        feedTimer.cancel();
        feedTimer = new Timer();
        feedTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                sendString(RandomSentence.get());
            }
        }, intervalInMillis, intervalInMillis); //delay, interval
    }

    private void sendString(String string) {
        try {
            session.getRemote().sendString(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}