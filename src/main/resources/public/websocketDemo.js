var webSocket;

start();

function stop() {
    webSocket.close();
}

function start() {
    if (isOpen(webSocket)) { return; }
    webSocket = new WebSocket("ws://"+location.hostname+":"+location.port+"/randomGeneratedFeed/");
    webSocket.onopen    = function()    { addMsgToFeed("CLIENT: Ready. Waiting for messages."); };
    webSocket.onmessage = function(msg) { addMsgToFeed(msg);                                    };
    webSocket.onclose   = function()    { addMsgToFeed("CLIENT: Connection closed.");           };
}

function setSpeed(speed) {
    if (isOpen(webSocket)) {
        webSocket.send(speed);
    }
}

function isOpen(ws) {
    return typeof ws !== "undefined" && ws.readyState === ws.OPEN;
}

function addMsgToFeed(msg) {
    var message = msg.data ? msg.data : "<article>" + msg + "</article>";
    document.getElementById("feed").insertAdjacentHTML("afterbegin", message);
}

function clearFeed() {
    document.getElementById("feed").innerHTML = "";
}
