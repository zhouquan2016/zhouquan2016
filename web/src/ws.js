let sock;
const serviceEvents = {};
function openWebSock(wsUrl: string) {
    if (!("WebSocket" in window)) {
        console.error("当前客户端不支持websocket");
        return;
    }
    if (sock && sock.readyState == 1) {
        console.log("已建立websocket连接无需重新建立!");
        return;
    }
    sock = new WebSocket(wsUrl + "?token=xxx");

    sock.onopen = () => {
        console.log("已建立websocket连接!");

    };
    sock.onclose = () => {
        console.log("已断开websocket连接!")
    };
    sock.onmessage = (msg) => {
        console.log("接收到消息:", msg);
        msg = JSON.parse(msg.data);
        if (msg.serviceName && msg.eventName) {
            let handler = serviceEvents[msg.serviceName][msg.eventName];
            handler && handler(msg.data);
        }else {
            console.error(msg.serviceName, msg.eventName, "未注册");
        }
    };
    sock.onerror = (err) => {
        if (sock.readyState != 1) {
            return;
        }
        console.error("服务端发生错误:", err);
    };

}

function closeWebSock() {
    sock.close();
}

function registerEvent(serviceName:string, eventName:string, callback:function) {
    if (serviceName == null) {
        console.error("serviceName不能为空!");
        return false;
    }
    if (eventName == null) {
        console.error("eventName不能为空!");
        return false;
    }
    if (serviceEvents[serviceName] == null) {
        serviceEvents[serviceName] = {};
    }
    if (eventName in serviceEvents[serviceName]) {
        console.error("已注册，请检查!");
        return false;
    }
    serviceEvents[serviceName][eventName] = callback;
}
registerEvent("ws", "loginSuccess", () => {
    console.log("登录成功");
});

export {
    openWebSock,
    closeWebSock,
    registerEvent
}