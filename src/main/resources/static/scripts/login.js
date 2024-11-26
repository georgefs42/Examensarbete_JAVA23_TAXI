document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (username === "admin" && password === "123456") {
        // Redirect to drivers.html if login is successful
        window.location.href = "drivers.html";
    } else {
        // Show error message for incorrect credentials
        document.getElementById("errorMessage").textContent = "Invalid username or password!";
    }
});
