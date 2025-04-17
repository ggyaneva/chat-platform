document.getElementById("create-room").addEventListener("click", async () => {
    const roomName = document.getElementById("new-room-name").value;

    if (!roomName.trim()) {
        alert("Room name cannot be empty.");
        return;
    }

    const room = await createChatRoom(roomName);
    if (room) {
        const ul = document.getElementById("chat-rooms");
        const li = document.createElement("li");
        li.textContent = room.name;
        ul.appendChild(li);
    } else {
        alert("Failed to create chat room. Please try again.");
    }
});