importScripts('https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js');
importScripts('https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js');

console.log("🚀 SharedWorker inicializado con SockJS y Stomp");
let connections = [];
let stompClient = null;
onconnect = (event) => {
    const port = event.ports[0];
    connections.push(port);

    console.log("🔗 Nuevo tab conectado al SharedWorker");
    port.onmessage = (e) => {
        if (e.data === "start") {
            console.log("🚀 Cliente pidió iniciar conexión WebSocket");
            connect();
        } else if (stompClient && stompClient.connected) {
            // Mensajes que el cliente quiera mandar al servidor
            stompClient.send("/app/notificar", {}, e.data);
        }
    };

    port.start();
};
function connect() {
    if (stompClient) {
        console.log("⚡ WebSocket ya está conectado, no se abre otro.");
        return;
    }
    let socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log("✅ Conectado al WebSocket via STOMP.");
        stompClient.subscribe('/topic/notifications', (message) => {
            if (message.body) {
                connections.forEach(port => port.postMessage(message.body));
            }
        });
    }, (error) => {
        console.error("❌ Error en WebSocket:", error);
        stompClient = null;
    });
}

function showNotification(notification){
    alert("New Notification: " + notification.message);
    const audio = new Audio('/sounds/notification.mp3');
    audio.play().catch(error => console.error("Error playing sound:", error));
}