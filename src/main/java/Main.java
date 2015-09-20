import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        staticFileLocation("public"); // index.html will be served at localhost:4567/
        webSocket("/randomGeneratedFeed", RandomFeed.class);
        init();
    }

}
