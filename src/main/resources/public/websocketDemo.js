var ws; //websocket

start();

function stop() {
    ws.close();
}

function start() {
    if(isOpen(ws)) {
        return addMsgToFeed("CLIENT: Connection already open.")
    }
    ws = new WebSocket("ws://localhost:4567/randomGeneratedFeed/");
    ws.onopen = function() {
        addMsgToFeed("CLIENT: Ready. Waiting for messages.")
    };
    ws.onmessage = function(msg) {
        addMsgToFeed(msg);
    };
    ws.onclose = function() {
        addMsgToFeed("CLIENT: Connection closed.");
    };
}

function setSpeed(speed) {
    if(!isOpen(ws)) { return addMsgToFeed("CLIENT: Connection is closed. Can't change speed."); }
    ws.send(speed);
}

function isOpen(ws) {
    return typeof ws !== "undefined" && ws.readyState === ws.OPEN ;
}

//DOM methods

function addMsgToFeed(msg) {
    var feed = document.getElementById("feed");
    var messageDate = new Date().toLocaleTimeString();
    var messageText =  typeof msg.data !== "undefined" ? msg.data : msg;
    var message = document.createElement("div");
    message.innerHTML = messageDate + "<br>" + messageText;
    feed.insertBefore(message, feed.firstChild);
}

function clearFeed() {
    document.getElementById("feed").innerHTML = "";
}