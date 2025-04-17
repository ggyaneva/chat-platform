document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    const errorMessage = document.getElementById("error-message");
    const successMessage = document.getElementById("success-message");

    // Client-side validation
    if (!username || !password || !role) {
        errorMessage.textContent = "All fields are required.";
        successMessage.textContent = "";
        return;
    }

    const response = await fetch("/api/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password, role }),
    });

    if (response.ok) {
        successMessage.textContent = "Account created successfully. You can now log in.";
        errorMessage.textContent = "";
    } else {
        successMessage.textContent = "";
        errorMessage.textContent = "Failed to create account. Please try again.";
    }
});