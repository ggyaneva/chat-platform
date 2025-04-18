document.addEventListener("DOMContentLoaded", async () => {
    // Check if the user is already authenticated
    try {
        const response = await fetch("/api/auth/status");
        if (response.ok) {
            const authStatus = await response.json();
            if (authStatus.isAuthenticated) {
                // Redirect authenticated users to the chat list
                window.location.href = "/chat-list.html";
            }
        }
    } catch (error) {
        console.error("Error checking authentication status:", error);
    }

    // Attach event listener to the login form
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const csrfToken = getCsrfToken();
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-XSRF-TOKEN": csrfToken
                    },
                    body: JSON.stringify({ username, password }),
                });

                if (!response.ok) {
                    if (response.status === 403) {
                        throw new Error("Access forbidden. Please check your credentials.");
                    } else {
                        throw new Error("An unexpected error occurred.");
                    }
                }

                alert("Login successful!");
                window.location.href = "/chat-list.html"; // Redirect to chat list
            } catch (error) {
                const errorMessage = document.getElementById("error-message");
                errorMessage.textContent = error.message;
                errorMessage.style.display = "block";
            }
        });
    }

    // Attach event listener to the registration form
    const registerForm = document.getElementById("register-form");
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const csrfToken = getCsrfToken();
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("/api/auth/register", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-XSRF-TOKEN": csrfToken
                    },
                    body: JSON.stringify({ username, password }),
                });

                if (!response.ok) {
                    if (response.status === 403) {
                        throw new Error("Access forbidden. Registration is not allowed.");
                    } else {
                        throw new Error("An unexpected error occurred during registration.");
                    }
                }

                alert("Registration successful! You can now log in.");
                window.location.href = "/signin.html"; // Redirect to sign-in page
            } catch (error) {
                const errorMessage = document.getElementById("error-message");
                errorMessage.textContent = error.message;
                errorMessage.style.display = "block";
            }
        });
    }
});

// Utility to get CSRF token from cookies
function getCsrfToken() {
    return document.cookie
        .split("; ")
        .find(row => row.startsWith("XSRF-TOKEN="))
        ?.split("=")[1];
}