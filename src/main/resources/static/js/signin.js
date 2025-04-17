document.addEventListener("DOMContentLoaded", async () => {
    try {
        // Optional: Check if the user is already authenticated
        const response = await fetch("/api/auth/status");
        if (response.ok) {
            // Redirect authenticated users to the chat list
            window.location.href = "/chat-list.html";
        }
    } catch (error) {
        console.error("Error checking authentication status:", error);
    }
});

document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error("Invalid credentials!");
        }

        alert("Login successful!");
        window.location.href = "chat-list.html"; // Redirect to chat list
    } catch (error) {
        alert(error.message);
    }
});