document.getElementById("signin-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("signin-username").value;
    const password = document.getElementById("signin-password").value;

    const errorMessage = document.getElementById("signin-error-message");
    const successMessage = document.getElementById("signin-success-message");

    // Client-side validation
    if (!username || !password) {
        errorMessage.textContent = "Username and password are required.";
        successMessage.textContent = "";
        return;
    }

    const response = await fetch("/api/users/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
        successMessage.textContent = "Signed in successfully.";
        errorMessage.textContent = "";
    } else {
        successMessage.textContent = "";
        errorMessage.textContent = "Invalid username or password.";
    }
});