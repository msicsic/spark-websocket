var ws; //websocket

start();

function stop() {
    ws.close();
}

function start() {
    if(isOpen(ws)) {
        return addMsgToFeed("CLIENT: Connection already open.")
    }
    var hostAndPort = location.hostname +":"+ location.port;
    ws = new WebSocket("ws://"+hostAndPort+"/randomGeneratedFeed/");
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
    var message = msg.data ? msg.data : "<div>"+msg+"</div>";
    id("feed").insertAdjacentHTML("afterbegin", message);
}

function clearFeed() {
    id("feed").innerHTML = "";
}

function id(id) {
    return document.getElementById(id);
}
