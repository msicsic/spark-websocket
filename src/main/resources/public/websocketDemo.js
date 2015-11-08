var webSocket = new WebSocket("ws://"+location.hostname+":"+location.port+"/chat/");
webSocket.onmessage = function(msg) { updateChat(msg); };
webSocket.onclose = function(){alert("WebSocket connection closed")};

id("send").addEventListener("click", function() {
    sendMessage(id("message").value);
});

id("message").addEventListener("keypress", function(e) {
    if(e.keyCode === 13){ sendMessage(e.target.value); }
});

function sendMessage(message) {
    if(message !== "") {
        id("message").value = "";
        webSocket.send(message);
    }
}

function updateChat(msg) {
    var data = JSON.parse(msg.data);
    insert("feed", data.message);
    id("userlist").innerHTML = "";
    data.userlist.forEach(function (user) {
        insert("userlist", "<li>" + user + "</li>");
    });
}

function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

function id(id) {
    return document.getElementById(id);
}
