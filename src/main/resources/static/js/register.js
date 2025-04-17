document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    const response = await fetch("/api/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password, role }),
    });

    const successMessage = document.getElementById("success-message");
    const errorMessage = document.getElementById("error-message");

    if (response.ok) {
        successMessage.textContent = "Account created successfully. You can now log in.";
        errorMessage.textContent = "";
    } else {
        successMessage.textContent = "";
        errorMessage.textContent = "Failed to create account. Please try again.";
    }
});