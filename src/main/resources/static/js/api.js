const API_URL = "/api";

async function fetchChatRooms() {
    const response = await fetch(`${API_URL}/chat-rooms`);
    return response.json();
}

async function createChatRoom(name) {
    const response = await fetch(`${API_URL}/chat-rooms/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name }),
    });
    return response.json();
}