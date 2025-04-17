document.addEventListener("DOMContentLoaded", async () => {
    const queryString = new URLSearchParams(window.location.search);
    const roomId = queryString.get("roomId");
    const messagesContainer = document.getElementById("messages-container");
    const sendButton = document.getElementById("send-button");
    const messageInput = document.getElementById("message-input");

    const socket = new WebSocket(`ws://${window.location.host}/ws/chat`);

    socket.onopen = () => {
        console.log("WebSocket connection established.");
    };

    socket.onmessage = (event) => {
        const message = event.data;
        const div = document.createElement("div");
        div.textContent = message;
        messagesContainer.appendChild(div);
    };

    socket.onerror = (error) => {
        console.error("WebSocket error:", error);
    };

    sendButton.addEventListener("click", () => {
        const content = messageInput.value.trim();
        if (!content) return;

        const message = JSON.stringify({ roomId, content });
        socket.send(message);
        messageInput.value = ""; // Clear input
    });
});