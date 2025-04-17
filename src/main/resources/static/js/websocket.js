const roomName = document.getElementById("room-name");
const messagesDiv = document.getElementById("messages");
const messageInput = document.getElementById("message-input");
const sendMessageButton = document.getElementById("send-message");

const ws = new WebSocket("ws://localhost:8080/ws");

ws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    const div = document.createElement("div");
    div.textContent = `${message.sender}: ${message.content}`;
    messagesDiv.appendChild(div);
};

sendMessageButton.addEventListener("click", () => {
    const content = messageInput.value;
    ws.send(JSON.stringify({ content }));
    messageInput.value = "";
});