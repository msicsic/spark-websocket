var webSocket;

document.getElementById("send").addEventListener("click", function() {
    sendMessage(document.getElementById("message").value);
});

document.getElementById("message").addEventListener("keypress", function(e) {
    if(e.keyCode === 13){ sendMessage(e.target.value); }
});

(function() {
    webSocket = new WebSocket("ws://"+location.hostname+":"+location.port+"/chat/");
    webSocket.onopen    = function()    { addMsgToFeed("Connected");    };
    webSocket.onmessage = function(msg) { addMsgToFeed(msg);            };
    webSocket.onclose   = function()    { addMsgToFeed("Disconnected"); };
})();

function sendMessage(message) {
    if(isOpen(webSocket) && message !== "") {
        document.getElementById("message").value = "";
        webSocket.send(message);
    }
}

function isOpen(ws) {
    return typeof ws !== "undefined" && ws.readyState === ws.OPEN;
}

function addMsgToFeed(msg) {
    var message = msg.data ? msg.data : "<article>" + msg + "</article>";
    document.getElementById("feed").insertAdjacentHTML("afterbegin", message);
}
