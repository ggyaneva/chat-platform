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

    logoutButton.addEventListener("click", async () => {
        try {
            const response = await fetch("/api/auth/logout", { method: "POST" });
            if (response.ok) {
                window.location.href = "/signin.html"; // Redirect to login page after logout
            } else {
                throw new Error("Logout failed!");
            }
        } catch (error) {
            alert("Error logging out: " + error.message);
        }
    });
});