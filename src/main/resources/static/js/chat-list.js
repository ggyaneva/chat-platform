document.addEventListener("DOMContentLoaded", async () => {
    const chatList = document.getElementById("chat-list");
    const logoutButton = document.getElementById("logout-button");

    try {
        const response = await fetch("/api/chat-rooms");
        const chatRooms = await response.json();

        chatRooms.forEach((room) => {
            const li = document.createElement("li");
            li.textContent = room.name;
            li.onclick = () => {
                window.location.href = `chat.html?roomId=${room.id}`;
            };
            chatList.appendChild(li);
        });
    } catch (error) {
        chatList.innerHTML = "<li>Error loading chats.</li>";
    }

    logoutButton.addEventListener("click", () => {
        window.location.href = "signin.html";
    });
});