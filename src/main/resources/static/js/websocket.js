const roomName = document.getElementById("room-name");
const messagesDiv = document.getElementById("messages");
const messageInput = document.getElementById("message-input");
const sendMessageButton = document.getElementById("send-message");

const ws = new WebSocket("ws://localhost:8080/ws");

ws.onmessage = (event) => {
    try {
        const message = JSON.parse(event.data);
        const div = document.createElement("div");
        div.textContent = `${message.sender}: ${message.content}`;
        messagesDiv.appendChild(div);
    } catch (error) {
        console.error("Failed to process message:", error);
    }
};

sendMessageButton.addEventListener("click", () => {
    const content = messageInput.value.trim();
    if (!content) {
        alert("Message cannot be empty.");
        return;
    }

    ws.send(JSON.stringify({ content }));
    messageInput.value = "";
});