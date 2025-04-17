const API_URL = "/api";

// Fetch chat rooms with error handling
async function fetchChatRooms() {
    try {
        const response = await fetch(`${API_URL}/chat-rooms`);
        if (!response.ok) throw new Error("Failed to fetch chat rooms");
        return response.json();
    } catch (error) {
        console.error(error);
        return [];
    }
}

// Create a new chat room with error handling
async function createChatRoom(name) {
    try {
        const response = await fetch(`${API_URL}/chat-rooms/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name }),
        });
        if (!response.ok) throw new Error("Failed to create chat room");
        return response.json();
    } catch (error) {
        console.error(error);
        return null;
    }
}