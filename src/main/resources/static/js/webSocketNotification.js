let connections = [];
let stompClient = null;

function connect(){
    if(stompClient) return;

    let socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({},() =>{
        console.log("Connected to WebSocket via STOMP.");
        stompClient.subscribe('/topic/notifications', (message) => {
            if(message.body){
                connections.forEach(port => port.postMessage(message.body));
            }
        });
    }, (error) => {
        console.error("WebSocket connection error:", error);
        stompClient = null;

    })
}
connect();
onconnect = (event) => {
    const port = event.ports[0];
    connections.push(port);
    port.onmessage = (e) => {
        if (stompClient && stompClient.connected) {
            stompClient.send("/app/notificar", {}, e.data);
        }
    };
    port.start();
    connect();
};

function disconnect(){
    if(stompClient){
        stompClient.disconnect();
        stompClient = null;
        console.log("Disconnected from WebSocket.");
    }
}

function showNotification(notification){
    alert("New Notification: " + notification.message);
    const audio = new Audio('/sounds/notification.mp3');
    audio.play().catch(error => console.error("Error playing sound:", error));
}