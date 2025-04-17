document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
        const result = await response.json();
        const role = result.role; // Parse role from backend response

        // Redirect based on role
        if (role === "ADMIN") {
            window.location.href = "/admin-dashboard";
        } else {
            window.location.href = "/chat-rooms";
        }
    } else {
        const errorMessage = document.getElementById("error-message");
        errorMessage.textContent = "Invalid username or password.";
    }
});