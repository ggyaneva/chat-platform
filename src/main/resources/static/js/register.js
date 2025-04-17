document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error("Registration failed! Try again.");
        }

        alert("Registration successful! Please log in.");
        window.location.href = "signin.html"; // Redirect to login
    } catch (error) {
        alert(error.message);
    }
});