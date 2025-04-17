document.getElementById("create-room").addEventListener("click", async () => {
    const roomName = document.getElementById("new-room-name").value;
    const room = await createChatRoom(roomName);
    const ul = document.getElementById("chat-rooms");
    const li = document.createElement("li");
    li.textContent = room.name;
    ul.appendChild(li);
});