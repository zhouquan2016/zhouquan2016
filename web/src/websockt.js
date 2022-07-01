const Stomp = require('stompjs');
const sockjs = require("sockjs-client")
let client;
let headers = {};
let checkConnectInt;

function isConnected(){
    return client && client.connected;
}

function openWebSock(wsUrl: string) {
    if (isConnected()) {
        console.log("websocket已连接!");
        window.clearInterval(checkConnectInt);
        return;
    }
    console.log("websocket开始连接!")
    const sock = new sockjs(wsUrl);
    client = Stomp.over(sock);
    console.log(client)
    client.connect(headers, () => {
        console.log("websocket连接成功!");
        client.subscribe('/topic/greetings', function (greeting) {
            console.log(greeting);
        });

    }, (err) => {
        console.info("websocket连接失败!!!");
        checkConnectInt = setTimeout(() => {
            console.log("websocket掉线重连中...");
            openWebSock(wsUrl);
        }, 1000);
    });
}


function closeWebSock() {
    if (isConnected()) {
        window.clearTimeout(checkConnectInt);
        client.disconnect(() => {
            console.log("已断开websocket");
        });
    }
}
export {
    openWebSock,
    closeWebSock
}