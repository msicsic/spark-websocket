import javafx.collections.*;
import java.util.*;
import static spark.Spark.*;

public class Main {

    static Timer feedTimer = new Timer();
    static ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>());

    public static void main(String[] args) {
        setTimerSpeed(2500);
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        port(9999);
        webSocket("/randomGeneratedFeed", FeedListener.class);
        init();
    }

    public static void setTimerSpeed(int intervalInMillis) {
        feedTimer.cancel();
        feedTimer = new Timer();
        feedTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                observableList.add(RandomSentence.get());
            }
        }, intervalInMillis, intervalInMillis); //delay, interval
    }

}
